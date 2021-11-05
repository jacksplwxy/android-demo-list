package com.jacksplwxy.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //    为菜单定义一个标识
    private static final int MENU1 = 0x111;
    private static final int MENU2 = 0x112;
    private static final int MENU3 = 0x113;
    private TextView text;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text1);
        //文本框注册上下文菜单
        registerForContextMenu(text);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.showContextMenu();
            }
        });
    }

    //    创建上下文菜单时触发该方法
    //    触发条件有两种 第一：调用方法 view.showContextMenu()， 第二种：长按（可以和长按事件setOnLongClickListener同时触发）
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU1, 0, "红色");
        menu.add(0, MENU2, 0, "绿色");
        menu.add(0, MENU3, 0, "蓝色");
        //        将三个菜单项设为单选菜单项
        menu.setGroupCheckable(0, true, true);
        //        设置上下文菜单的标题、图标
        menu.setHeaderIcon(R.drawable.ic_launcher_foreground);
        menu.setHeaderTitle("选择背景色");
    }

    //    上下文菜单的菜单项被单击时触发该方法
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU1:
                text.setBackgroundColor(Color.RED);
                break;
            case MENU2:
                text.setBackgroundColor(Color.GREEN);
                break;
            case MENU3:
                text.setBackgroundColor(Color.BLUE);
                break;
        }
        return true;
    }
}