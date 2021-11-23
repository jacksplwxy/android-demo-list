package universe.android.mobile.ndkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import universe.mobile.android.myapplication.R;
//  NDK访问Java字段
public class MainActivity extends Activity
{
    //  Java_packagename_classname_functionname
    //  jstring   jint  int
    //  1. 建立一个调用NDK函数的Java类，并且在Java类中定义与NDK函数名称相同
    //  的native方法
    //  2.  准备头文件（.h)，使用下面的命令自动生成头文件
    //  javah -jni universe.android.mobile.ndkdemo.MyNDK
    //  3.  创建jni目录，并将头文件移动到这个目录
    //  4.  在jni目录下创建cpp文件，实现.h文件中的函数
    //  5.  设置local.properties文件，指定Android NDK的根目录
    //  6.  设置build.gradle文件，编译NDK程序，生成libJniDemo.so
    //  7.  调用NDK函数
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("21 * 21 = " + String.valueOf(MyNDK.square(21)));

        MyNDK myNDK = new MyNDK();
        textView.append("\n10! = " + myNDK.jc());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
