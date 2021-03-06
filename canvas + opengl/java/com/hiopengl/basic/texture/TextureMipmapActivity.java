package com.hiopengl.basic.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.hiopengl.R;
import com.hiopengl.base.ActionBarActivity;
import com.hiopengl.utils.GlUtil;
import com.hiopengl.utils.LogUtil;
import com.hiopengl.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TextureMipmapActivity extends ActionBarActivity {
    private GLSurfaceView mGLSurfaceView;
    private Texture3DRenderer mGLRenderer;
    private SeekBar mSeekBar;
    private int mDZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_mipmap);

        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDZ = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mDZ = mSeekBar.getProgress();
        mGLSurfaceView = findViewById(R.id.gl_surface_view);
        mGLSurfaceView.setEGLContextClientVersion(3);
        mGLRenderer = new Texture3DRenderer(this);
        mGLSurfaceView.setRenderer(mGLRenderer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    private class Texture3DRenderer implements GLSurfaceView.Renderer {
        private static final int BYTES_PER_FLOAT = 4;
        private static final int BYTES_PER_INT = 4;

        private Context mContext;

        // ????????????
        private int mProgram = -1;

        // ?????????????????????
        private FloatBuffer vertexBuffer;

        // VBO
        private int vboBufferId;

        // VAO
        private int vaoBufferId;

        // vertex??????(??????+??????+?????????)
        private float vertex[] ={ // X, Y, Z, S, T
                -0.5f, -0.5f, -0.5f, 1.0f, 1.0f, // F
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, // B
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f, // C
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, // B
                -0.5f, -0.5f, -0.5f, 1.0f, 1.0f, // F
                -0.5f, 0.5f, -0.5f, 1.0f, 0.0f, // H

                -0.5f, -0.5f, 0.5f, 0.0f, 1.0f, // E
                0.5f, -0.5f, 0.5f, 1.0f, 1.0f, // D
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, // A
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, // A
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, // G
                -0.5f, -0.5f, 0.5f, 0.0f, 1.0f, // E

                -0.5f, 0.5f, 0.5f, 1.0f, 0.0f, // G
                -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, // H
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, // F
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, // F
                -0.5f, -0.5f, 0.5f, 1.0f, 1.0f, // E
                -0.5f, 0.5f, 0.5f, 1.0f, 0.0f, // G

                0.5f, 0.5f, -0.5f, 1.0f, 1.0f, // B
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, // A
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f, // C
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f, // C
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, // A
                0.5f, -0.5f, 0.5f, 0.0f, 0.0f, // D

                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, // F
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f, // C
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f, // D
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f, // D
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, // E
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, // F

                -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, // H
                0.5f, 0.5f, 0.5f, 1.0f, 1.0f, // A
                0.5f, 0.5f, -0.5f, 1.0f, 0.0f, // B
                -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, // G
                0.5f, 0.5f, 0.5f, 1.0f, 1.0f, // A
                -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, // H
        };

        // ??????
        private int textureId;

        //????????????
        private final float[] mModelMatrix = new float[16];
        //????????????
        private final float[] mViewMatrix = new float[16];
        //ViewModel??????
        private final float[] mViewModelMatrix = new float[16];
        //????????????
        private final float[] mProjectMatrix = new float[16];
        //??????????????????
        private final float[] mMVPMatrix = new float[16];

        public Texture3DRenderer(Context context) {
            mContext = context;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertex.length * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertexBuffer = byteBuffer.asFloatBuffer();
            vertexBuffer.put(vertex);
            vertexBuffer.position(0);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(0.0f,0.0f,0.0f,1.0f);

            //????????????????????????
            String vertexShaderStr = ShaderUtil.loadAssets(mContext, "vertex_texture_3d.glsl");
            int vertexShaderId = ShaderUtil.compileVertexShader(vertexShaderStr);
            //????????????????????????
            String fragmentShaderStr = ShaderUtil.loadAssets(mContext, "fragment_texture_3d.glsl");
            int fragmentShaderId = ShaderUtil.compileFragmentShader(fragmentShaderStr);
            //????????????
            mProgram = ShaderUtil.linkProgram(vertexShaderId, fragmentShaderId);
            //???OpenGLES?????????????????????
            GLES30.glUseProgram(mProgram);

            textureId = loadTexture(mContext, R.drawable.texture);

            // ?????????VBO
            int[] buffers = new int[1];
            GLES30.glGenBuffers(buffers.length, buffers, 0);
            if (buffers[0] == 0) {
                throw new RuntimeException();
            }

            vboBufferId = buffers[0];

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboBufferId);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexBuffer.capacity() * BYTES_PER_FLOAT, vertexBuffer, GLES30.GL_STATIC_DRAW);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);

            // ?????????VAO
            buffers[0] = 0;
            GLES30.glGenVertexArrays(buffers.length, buffers, 0);
            if (buffers[0] == 0) {
                throw new RuntimeException();
            }

            vaoBufferId = buffers[0];

            GLES30.glBindVertexArray(vaoBufferId);

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboBufferId);
            int aPositionLocation = GLES30.glGetAttribLocation(mProgram,"vPosition");
            GLES30.glEnableVertexAttribArray(aPositionLocation);
            GLES30.glVertexAttribPointer(aPositionLocation,3, GLES30.GL_FLOAT,false,5 * BYTES_PER_FLOAT, 0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboBufferId);
            int aTexCoordLocation = GLES30.glGetAttribLocation(mProgram,"aTexCoord");
            GLES30.glEnableVertexAttribArray(aTexCoordLocation);
            GLES30.glVertexAttribPointer(aTexCoordLocation,2, GLES30.GL_FLOAT,false,5 * BYTES_PER_FLOAT, 3 * BYTES_PER_FLOAT);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);

            GLES30.glBindVertexArray(0);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);

            float ratio = (float) width / height;
            // ??????????????????
            Matrix.frustumM(mProjectMatrix,0, -ratio, ratio,-1f,1f,1f,333f);
            Matrix.setIdentityM(mModelMatrix, 0);
            Matrix.setIdentityM(mViewModelMatrix, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
            GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT
                    | GL10.GL_DEPTH_BUFFER_BIT);

            Matrix.rotateM(mModelMatrix, 0, 0.5f, 0.5f, 0.5f, 0.0f);

            // ??????????????????
            Matrix.setLookAtM(mViewMatrix,0,
                    0f,0f, mDZ,// ???????????????
                    0f,0f,0f,// ????????????????????????
                    0f,0.1f,0.0f);// ????????????
            // ???????????????????????????????????????????????????????????????????????????up????????????????????????????????????????????????????????????????????????
            // ????????????up?????????y???????????????upx = 0,upy = 1,upz = 0????????????????????????????????????
            // ??????????????????

            Matrix.multiplyMM(mViewModelMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
            Matrix.multiplyMM(mMVPMatrix,0, mProjectMatrix,0, mViewModelMatrix,0);

            int uMatrixLocation = GLES30.glGetUniformLocation(mProgram,"vMatrix");
            GLES30.glUniformMatrix4fv(uMatrixLocation,1,false, mMVPMatrix,0);

            // ????????????????????????????????????????????????0
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
            // ?????????ID???????????????????????????????????????
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
            // ???????????????????????????????????????u_TextureUnit
            int uTextureLocation = GLES30.glGetUniformLocation(mProgram,"uTexture");
            GLES30.glUniform1i(uTextureLocation, 0);

            GLES30.glEnable(GL10.GL_CULL_FACE);
            GLES30.glCullFace(GLES30.GL_BACK);
            GLES30.glFrontFace(GLES30.GL_CCW);

            GLES30.glBindVertexArray(vaoBufferId);
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 36 * 3);
            GLES30.glBindVertexArray(0);
        }

        public int loadTexture(Context context, int resourceId) {
            final int[] textureObjectIds = new int[1];
            // 1. ??????????????????
            GLES30.glGenTextures(1, textureObjectIds, 0);

            if (textureObjectIds[0] == 0) {
                LogUtil.e("Could not generate a new OpenGL texture object.");
                return -1;
            }

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            final Bitmap bitmap = BitmapFactory.decodeResource(
                    context.getResources(), resourceId, options);

            if (bitmap == null) {
                LogUtil.e("Resource ID " + resourceId + " could not be decoded.");
                // ??????Bitmap???????????????????????????Id
                GLES30.glDeleteTextures(1, textureObjectIds, 0);
                return -1;
            }
            // 2. ??????????????????OpenGL?????????
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureObjectIds[0]);

            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_MIRRORED_REPEAT);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,
                    GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_MIRRORED_REPEAT);

            // 3. ????????????????????????:???????????????????????????????????????????????????????????????????????????????????????
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            // 4. ??????OpenGL????????????Bitmap???????????????????????????????????????????????????????????????Bitmap??????
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);

            // Note: Following code may cause an error to be reported in the
            // ADB log as follows: E/IMGSRV(20095): :0: HardwareMipGen:
            // Failed to generate texture mipmap levels (error=3)
            // No OpenGL error will be encountered (glGetError() will return
            // 0). If this happens, just squash the source image to be
            // square. It will look the same because of texture coordinates,
            // and mipmap generation will work.
            // 5. ??????Mip??????
            GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);

            // 6. ??????Bitmap??????
            bitmap.recycle();

            // 7. ????????????OpenGL???????????????
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

            // ????????????????????????OpenGL??????????????????????????????????????????????????????Bitmap???????????????OpenGL?????????
            return textureObjectIds[0];
        }
    }
}