package com.jacksplwxy.start;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//文档：https://www.jianshu.com/p/bb57bc65e4ce
public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btnGetSync;
    Button btnGetAsync;
    Button postKeyVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.result);

        btnGetSync = findViewById(R.id.getSync);
        btnGetSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSync();
            }
        });
        btnGetAsync = findViewById(R.id.getAsync);
        btnGetAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAsync();
            }
        });
        postKeyVal = findViewById(R.id.postKeyVal);
        postKeyVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postKeyVal();
            }
        });
    }

    //同步GET请求
    private void getSync() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder()
                .url("http://www.sosoapi.com/pass/mock/12003/test/gettest")
                .get()
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.同步调用会阻塞主线程,这边在子线程进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步调用,返回Response,会抛出IO异常
                    Response response = call.execute();
                    textView.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //异步get请求
    private void getAsync() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder()
                .url("http://www.sosoapi.com/pass/mock/12003/test/gettest")
                .get()
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                String err = e.getMessage().toString();
            }

            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String rtn = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(rtn);
                    }
                });
            }
        });
    }

    //Post 键值对
    private void postKeyVal() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder mBuild = new FormBody.Builder();
        mBuild.add("aa", "bb")
                .add("cc", "1");
        RequestBody requestBodyPost = mBuild.build();

        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder()
                .url("http://www.sosoapi.com/pass/mock/12003/test/posttest")
                .post(requestBodyPost)
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                String err = e.getMessage().toString();
            }

            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String rtn = response.body().string();
                //获取返回码
                final String code = String.valueOf(response.code());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(rtn);
                    }
                });
            }
        });
    }

    //Post 字符串
    private void postString() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //RequestBody中的MediaType指定为纯文本，编码方式是utf-8
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                "{username:admin;password:admin}");
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder()
                .url("http://www.sosoapi.com/pass/mock/12003/test/posttest")
                .post(requestBody)
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                String err = e.getMessage().toString();
            }

            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String rtn = response.body().string();
                //获取返回码
                final String code = String.valueOf(response.code());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(rtn);
                    }
                });
            }
        });
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //Post JSON
    private void postJson() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //RequestBody中的MediaType指定为纯文本，编码方式是utf-8
        RequestBody requestBody = RequestBody.create(JSON, "{name:'jacksplwxy'}");
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder()
                .url("http://www.sosoapi.com/pass/mock/12003/test/posttest")
                .post(requestBody)
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                String err = e.getMessage().toString();
            }

            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String rtn = response.body().string();
                //获取返回码
                final String code = String.valueOf(response.code());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(rtn);
                    }
                });
            }
        });
    }

    //Post 提交表单
    private void postForm() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //RequestBody中的MediaType指定为纯文本，编码方式是utf-8
        MultipartBody.Builder mBuild = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "xxx")
                .addFormDataPart("password", "xxx");
        RequestBody requestBody = mBuild.build();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder()
                .url("http://www.sosoapi.com/pass/mock/12003/test/posttest")
                .post(requestBody)
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                String err = e.getMessage().toString();
            }

            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String rtn = response.body().string();
                //获取返回码
                final String code = String.valueOf(response.code());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(rtn);
                    }
                });
            }
        });
    }

    //更多方式：https://www.jianshu.com/p/bb57bc65e4ce

//    //Post 提提交多文件
//    private void postFile() {
//        OkHttpClient http = new OkHttpClient();
//        MultipartBody.Builder mBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        int i = 0;
//        for (String filePath : filelist) {
//            File file = new File(filePath);
//            if (!file.exists()) {
//                Toast.makeText(MainActivity.this, "上传" + filePath + "文件不存在！", Toast.LENGTH_SHORT).show();
//                continue;
//            }
//            String fileMimeType = getMimeType(file);
//            //这里获取文件类型，方法自己定义
//            MediaType mediaType = MediaType.parse(fileMimeType);
//            RequestBody fileBody = RequestBody.create(mediaType, file);
//            mBody.addFormDataPart("file" + i, file.getName(), fileBody);
//            i++;
//        }
//        RequestBody requestBody = mBody.build();
//        Request requestPostFile = new Request.Builder()
//                .url("http://www.jianshu.com/")
//                .post(requestBody)
//                .build();
//         ...
//
//        //同时上传文件加上键值对参数的方法：
//        RequestBody multipartBody = new MultipartBody.Builder()
//                .setType(MultipartBody.ALTERNATIVE)
//                .addFormDataPart("aa", "bb")
//                .addFormDataPart("cc", 1)
//                .addFormDataPart("file", filename, fileBody)
//                .build();
//    }
//
//    //android获取文件getMimeType的方法
//    private static String getSuffix(File file) {
//        if (file == null || !file.exists() || file.isDirectory()) {
//            return null;
//        }
//        String fileName = file.getName();
//        if (fileName.equals("") || fileName.endsWith(".")) {
//            return null;
//        }
//        int index = fileName.lastIndexOf(".");
//        if (index != -1) {
//            return fileName.substring(index + 1).toLowerCase(Locale.US);
//        } else {
//            return null;
//        }
//    }
//
//    public static String getMimeType(File file) {
//        String suffix = getSuffix(file);
//        if (suffix == null) {
//            return "file/*";
//        }
//        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
//        if (type != null || !type.isEmpty()) {
//            return type;
//        }
//        return "file/*";
//    }
//
//    private void postDownloadImg() {
//        OkHttpClient client = new OkHttpClient();
//        final Request request = new Request
//                .Builder()
//                .get()
//                .url("http://avatar.csdn.net/B/0/1/1_new_one_object.jpg")
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                ToastUtil.showToast(DownImageActivity.this, "下载图片失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                InputStream inputStream = response.body().byteStream();
//                //将图片显示到ImageView中
//                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        iv_result.setImageBitmap(bitmap);
//                    }
//                });
//                //将图片保存到本地存储卡中
//                File file = new File(Environment.getExternalStorageDirectory(), "image.png");
//                FileOutputStream fileOutputStream = new FileOutputStream(file);
//                byte[] temp = new byte[128];
//                int length;
//                while ((length = inputStream.read(temp)) != -1) {
//                    fileOutputStream.write(temp, 0, length);
//                }
//                fileOutputStream.flush();
//                fileOutputStream.close();
//                inputStream.close();
//            }
//        });
//    }


}