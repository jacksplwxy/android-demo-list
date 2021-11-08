package com.jacksplwxy.start;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    Intent intent ;
    Intent intent2;
    MyBindService service;//定义一个类型为继承了Service类的MyBindService类的成员变量，
    /*
     * 使用bindService(intent2, conn, Service.BIND_AUTO_CREATE);方式开启一个
     * Service服务必须实例化一个ServiceConnection用来接收extends Service的MyBindService里
     * 回传的数据
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 当启动源跟Service的连接意外丢失的时候会调用这个方法
         * 比如当Service崩溃了或者被强行kill了。
         */
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // TODO Auto-generated method stub
            int s = service.SIZE;
//          Toast.makeText(this, "onServiceConnected()方法所在线程为："+Thread.currentThread().getName(), 100).show();
            System.out.println("SIZE:"+s+",onServiceDisconnected()方法所在线程为："+Thread.currentThread().getName());
        }

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            //接收会传来的数据,根据这个service我们可以获取一些数据
            service = ((MyBindService.MyBinder)binder).getService();
            int s = service.SIZE;
//          Toast.makeText(this, "onServiceConnected()方法所在线程为："+Thread.currentThread().getName(), 100).show();
            System.out.println("SIZE:"+s+",onServiceConnected()方法所在线程为："+Thread.currentThread().getName());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                intent = new Intent(MainActivity.this,MyService.class);
                System.out.println("onClick.startService()");
                Toast.makeText(this, "开启线程startService",Toast.LENGTH_SHORT).show();
                startService(intent);
                break;
            case R.id.stop:
                System.out.println("onClick.stopService()");
                Toast.makeText(this, "关闭线程stopService", Toast.LENGTH_SHORT).show();
                stopService(intent);
                break;

            case R.id.bind://绑定
                intent2 = new Intent(MainActivity.this,MyBindService.class);
                //第三个参数是自动开启服务的作用,第二个参数不能够为空，且为ServiceConnection conn类型，
                bindService(intent2, conn, Service.BIND_AUTO_CREATE);
                System.out.println("onClick.bindService()");
                Toast.makeText(this, "开启绑定", Toast.LENGTH_SHORT).show();
                break;

            case R.id.unbind://解除绑定
                stopService(intent2);
                unbindService(conn);//解除绑定，这个参数一个不能够为空，unbindService(ServiceConnection conn);
                System.out.println("onClick.unbindService()");
                Toast.makeText(this, "解除绑定", Toast.LENGTH_SHORT).show();
                break;

            case R.id.music://播放音乐
                service.Play();
                Toast.makeText(this, "播放音乐", Toast.LENGTH_SHORT).show();
                break;
            case R.id.stopmusic://暂停音乐
                service.Pause();
                Toast.makeText(this, "暂停音乐", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}