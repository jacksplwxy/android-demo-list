package com.jacksplwxy.start;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service{

    @Override
    /*
     * Parameters
        intent  The Intent that was used to bind to this service,
        as given to Context.bindService. Note that any extras
        that were included with the Intent at that point will
         not be seen here.
       Returns
       Return an IBinder through which clients can call on to the service.
     */
    public void onCreate() {
        System.out.println("BindService.onCreate()");
        super.onCreate();
    };


    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        System.out.println("BindService.onBind");
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        System.out.println("onStartCommand()方法。。。。");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("myServie.ondestroy()....");
        super.onDestroy();
    }
}