package com.jacksplwxy.start;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

//测试时注意打开手机的选择屏幕设置

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();

        Fragment01 fragment01 = new Fragment01();
        Fragment02 fragment02 = new Fragment02();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (width < height) {
            //竖屏
            ft.replace(android.R.id.content,fragment01);
        }else {
            //横屏
            ft.replace(android.R.id.content, fragment02);
        }
        ft.commit();
    }

}