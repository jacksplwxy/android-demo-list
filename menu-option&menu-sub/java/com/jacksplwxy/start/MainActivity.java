package com.jacksplwxy.start;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        mTextView = (TextView) findViewById(R.id.optionMenuText);
    }
    //重载onCreateOptionsMenu(Menu menu)方法，
    //并在此方法中添加菜单项，最后返回true，如果false，菜单则不会显示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //1.布局中添加
        getMenuInflater().inflate(R.menu.optionmenu_item,menu);
        /**
         * add()方法的四个参数，依次是：
         1、组别，如果不分组的话就写Menu.NONE,
         2、Id，这个很重要，Android根据这个Id来确定不同的菜单
         3、顺序，那个菜单现在在前面由这个参数的大小决定
         4、文本，菜单的显示文本*/
        //2.Java代码中添加
        menu.add(Menu.NONE,1,Menu.NONE,"java代码中添加OptionMenu1");
        menu.add(Menu.NONE,2,Menu.NONE,"java代码中添加Optionmenu2");
        return true;
    }
    //OptionMenu菜单监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                String str1 = "您点击了java代码中添加OptionMenu1";
                Toast.makeText(MainActivity.this,str1, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                String str2 = "您点击了java代码中添加OptionMenu1";
                Toast.makeText(MainActivity.this,str2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.option_color:
                mTextView.setTextColor(Color.RED);
                break;
            case R.id.font_san:
                mTextView.setTextSize(mTextView.getTextSize()+1);
                break;
            case R.id.font_wu:
                mTextView.setTextSize(mTextView.getTextSize()+2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}