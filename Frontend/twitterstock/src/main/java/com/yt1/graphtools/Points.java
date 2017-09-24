package com.yt1.graphtools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES20;
import android.util.Log;

/**
 * A two-dimensional triangle for use as a drawn object in OpenGL ES 2.0.
 */
public class Points {
    private final FloatBuffer vertexBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    /*static float tc[] = {
            // in counterclockwise order;
            -1.26f, -0.17723f, 0.0f,   // bottom left
            -1.16f, 0.57723f, 0.0f,   // bottom left
            -1.06f, -0.67723f, 0.0f,   // bottom left
            -0.96f, 0.67723f, 0.0f,   // bottom left
            -0.9f, -0.311004243f, 0.0f,   // bottom left
            -0.8f, 0.611004243f, 0.0f,   // bottom left
            -0.7f, -0.211004243f, 0.0f,   // bottom left
            -0.6f, 0.711004243f, 0.0f,   // bottom left
            -0.5f, -0.311004243f, 0.0f,   // bottom left
            -0.4f, 0.811004243f, 0.0f,   // bottom left
            -0.3f, -0.311004243f, 0.0f,   // bottom left
            -0.2f, 0.211004243f, 0.0f,   // bottom left
            -0.1f, -0.111004243f, 0.0f,   // bottom left
            0.0f,  0.622008459f, 0.0f,   // top
            0.1f, -0.411004243f, 0.0f,   // bottom left
            0.2f, 0.811004243f, 0.0f,   // bottom left
            0.3f,  0.643f, 0.0f,   // top
            0.4f, -0.711004243f, 0.0f,   // bottom left
            0.5f, 0.411004243f, 0.0f,    // bottom right
            0.6f, -0.811004243f, 0.0f,   // bottom left
            0.7f, 0.311004243f, 0.0f,   // bottom left
            0.8f, -0.111004243f, 0.0f,   // bottom left
            0.9f, -0.6f, 0.0f,   // bottom left
            1.0f, -0.8f, 0.0f,   // bottom left
            1.1f, -0.7f, 0.0f,   // bottom left
            1.26f, -0.9f, 0.0f    // bottom right
    };*/

    private final int vertexCount;
    private final int vertexStride; // 4 bytes per vertex

    float color[] = { 0.0f, 0.0f, 0.0f, 1.0f };

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Points(List<Float> triangleCoords) {
        Log.d("tri", triangleCoords.toString());
        vertexCount = triangleCoords.size() / COORDS_PER_VERTEX;
        vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.size() * 4);
        // (number of coordinate values * 4 bytes per float)
        //triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        for(Float f : triangleCoords) { vertexBuffer.put(f); }
        //vertexBuffer.put(triangleCoords.addAll());
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix, ShaderTools mShader) {
        // Add program to OpenGL environment
        int mProgram = mShader.setupGraphShader();
        GLES20.glUseProgram(mProgram);
        GLES20.glLineWidth(5.0f);


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

        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}