package com.yt1.graphtools;

import android.opengl.GLES20;

/**
 * Created by BradBoswell on 2/1/17.
 */

public class ShaderTools {

    private String vGraphShader =
                // This matrix member variable provides a hook to manipulate
                // the coordinates of the objects that use this vertex shader
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "void main() {" +
                        // The matrix must be included as a modifier of gl_Position.
                        // Note that the uMVPMatrix factor *must be first* in order
                        // for the matrix multiplication product to be correct.
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}";

    private String fGraphShader =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";

    private String vTexShader =
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "attribute vec2 a_TexCoordinate;" +
                "varying vec2 v_TexCoordinate;" +
                "void main() {" +
                // The matrix must be included as a modifier of gl_Position.
                // Note that the uMVPMatrix factor *must be first* in order
                // for the matrix multiplication product to be correct.
                "  v_TexCoordinate = a_TexCoordinate;" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}";

    private String fTexShader =
        "precision mediump float;" +
                "uniform sampler2D u_Texture;" +
                "varying vec2 v_TexCoordinate;" +
                "void main() {" +
                "  gl_FragColor = texture2D(u_Texture, v_TexCoordinate);" +
                "}";

    public int setupGraphShader(){
        int gProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vGraphShader);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fGraphShader);
        GLES20.glAttachShader(gProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(gProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(gProgram);                  // create OpenGL program executables
        return gProgram;
    }

    public int setupTexShader(){
        int tProgram = GLES20.glCreateProgram();
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vTexShader);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fTexShader);
        GLES20.glAttachShader(tProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(tProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(tProgram);
        return tProgram;
    }
}
