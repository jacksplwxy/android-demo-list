package com.jacksplwxy.start;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button btn;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.myButton1);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                intent.putExtra("str", "com from first activity");
                //startActivityForResult主要用来从FirstActivity跳转到SecondActivity然后返回FirstActivity并且获取从SecondActivity传回来的参数
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == SecondActivity.RESULT_CODE) {
                Bundle bundle = data.getExtras();
                String str = bundle.getString("back");
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }
        }
    }
}