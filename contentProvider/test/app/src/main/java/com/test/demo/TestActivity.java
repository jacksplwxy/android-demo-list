package com.test.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

public class TestActivity extends AppCompatActivity {

    private String aaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        aaa = getIntent().getStringExtra("bytes");

        byte[] bb = Base64.decode(aaa, Base64.DEFAULT);
        Bitmap b = BitmapFactory.decodeByteArray(bb, 0, bb.length);
        ((ImageView)findViewById(R.id.iv)).setImageBitmap(b);
    }
}
