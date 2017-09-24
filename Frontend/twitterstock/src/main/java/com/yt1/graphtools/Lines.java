package com.yt1.graphtools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

/**
 * A two-dimensional square for use as a drawn object in OpenGL ES 2.0.
 */
public class Lines {

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -1.0f, 0.7f, 0.0f,
            1.0f, 0.7f, 0.0f,

            -1.0f, 0.3f, 0.0f,
            1.0f, 0.3f, 0.0f,

            -1.0f, -0.1f, 0.0f,
            1.0f, -0.1f, 0.0f,

            -1.0f, -0.5f, 0.0f,
            1.0f, -0.5f, 0.0f,

            -1.0f, -0.8999f, 0.0f,
            1.0f, -0.8999f, 0.0f,

            -0.8999f, 1.0f, 0.0f,
            -0.8999f, -1.0f, 0.0f,

            -0.5f, 1.0f, 0.0f,
            -0.5f, -1.0f, 0.0f,

            -0.1f, 1.0f, 0.0f,
            -0.1f, -1.0f, 0.0f,

            0.3f, 1.0f, 0.0f,
            0.3f, -1.0f, 0.0f,

            0.7f, 1.0f, 0.0f,
            0.7f, -1.0f, 0.0f,


    }; // top right

    private final short drawOrder[] = { 0, 1 , 2, 3, 4, 5, 6, 7 , 8, 9, 10, 11, 12, 13, 14, 15 , 16, 17, 18, 19}; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    float color[] = { 0.0f, 0.0f, 0.0f, 1.0f };

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Lines() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix, ShaderTools mShader) {
        int mProgram = mShader.setupGraphShader();
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);
        GLES20.glLineWidth(3.0f);

        // get handle to vertex shader's vPosition member
        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        int mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_LINES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}