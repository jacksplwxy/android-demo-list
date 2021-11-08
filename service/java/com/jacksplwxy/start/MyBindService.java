package com.jacksplwxy.start;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

public class MyBindService extends Service{
    public static int SIZE = 3;
    @Override
    public void onCreate() {
        System.out.println("onCreate()方法。。。");
        super.onCreate();
    }
    /**
     * bind方式开启service，必须写一个类继承Binder,
     * 然后再IBinder onBind(Intent arg0)方法中返回所需要返回的值
     * @author wyl
     *
     */
    public class MyBinder extends Binder{
        public MyBindService getService(){
            System.out.println("MyBinder extends Binder的MyBindService getService()方法。。。");
            return MyBindService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        System.out.println("public IBinder onBind(Intent arg0) 方法。。。");
        /*
         * onBind(Intent arg0)，想回传数据，
         * 必须写上面的public class MyBinder extends Binder
         */
        return new MyBinder();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("onUnbind(Intent intent)方法。。。");
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        System.out.println("unbindService(ServiceConnection conn)方法。。。");
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy()方法。。。");
        super.onDestroy();
    }

    public void Play(){
        System.out.println("MyBindService.Play()方法，播放音乐");
    }
    public void Pause(){
        System.out.println("MyBindService.Pause()方法，暂停");
    }

}