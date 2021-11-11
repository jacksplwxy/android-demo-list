package com.feng.testcontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    private static final String CONTENT = "content://";
    public static final String AUTHORIY = "com.test.demo";
    public static final String TABLE_USER_INFO = "userInfo";

    private String uri = CONTENT + AUTHORIY + "/" + TABLE_USER_INFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.result);
    }

    public void click(View view) {

        Cursor cursor = getContentResolver().query(Uri.parse(uri), null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            textView.setText("查询到内容提供者的数据为："+cursor.getString(0)+"、"+cursor.getString(1)+"、"+cursor.getString(2));
        }
    }

    private void query() {

    }
}
