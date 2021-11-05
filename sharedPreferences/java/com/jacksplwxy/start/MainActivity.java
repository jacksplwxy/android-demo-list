package com.jacksplwxy.start;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
/**
 * SharedPreferences用法 测试Activity
 * @author jacksplwxy
 * 参考文档：https://www.jianshu.com/p/59b266c644f3
 * 作用：类似web的localstorage
 */
public class MainActivity extends Activity {
    private static final String TAG = "SharedPreferencesTest";
    /**
     * 保存数据SharedPreferences文件的名字
     */
    private final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveUserInfo();
        getUserInfo();
        removeUserInfo();
        getUserInfo();
        clearUserInfo();
        getUserInfo();
    }

    /**
     * 保存用户信息
     */
    private void saveUserInfo(){
        SharedPreferences userInfo = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {

            @Override
            public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                //preferences被 编辑的SharedPreferences实例
                //该SharedPreferences中被编辑的条目所对应的key
            }
        };
        //userInfo注册监听事件
        userInfo.registerOnSharedPreferenceChangeListener(changeListener);
        SharedPreferences.Editor editor = userInfo.edit();//获取Editor
        //得到Editor后，写入需要保存的数据
        editor.putString("username", "一只猫的涵养");
        editor.putInt("age", 20);
        editor.commit();//提交修改
        Log.i(TAG, "保存用户信息成功");
    }

    /**
     * 读取用户信息
     */
    private void getUserInfo(){
        SharedPreferences userInfo = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = userInfo.getString("username", null);//读取username
        int age = userInfo.getInt("age", 0);//读取age
        Log.i(TAG, "读取用户信息");
        Log.i(TAG, "username:" + username + "， age:" + age);
    }

    /**
     * 移除年龄信数据
     */
    private void removeUserInfo(){
        SharedPreferences userInfo = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();//获取Editor
        editor.remove("age");
        editor.commit();
        Log.i(TAG, "移除年龄数据");
    }

    /**
     * 清空数据
     */
    private void clearUserInfo(){
        SharedPreferences userInfo = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();//获取Editor
        editor.clear();
        editor.commit();
        Log.i(TAG, "清空数据");
    }
}