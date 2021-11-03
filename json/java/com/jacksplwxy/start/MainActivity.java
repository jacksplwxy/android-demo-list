package com.jacksplwxy.start;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSON4JavaBean();
        JSON4ArrayList();
        JSON4Map();
    }

    //json和javabean相互转换
    private void JSON4JavaBean() {
        Scenery scenery = new Scenery(1, "天坛公园", "北京");
        // Javabean 转换成 json 字符串
        // 创建谷歌的 gson 提供的工具类
        Gson gson = new Gson();
        // 使用 toJson() 方法转换
        String json = gson.toJson(scenery);
        System.out.println(json);
        //结果 : {"id":1,"name":"天坛公园","address":"北京"}
        // 使用 formJson 方法将 json 字符串转换为Javabean 对象
        Scenery sceneryBean = gson.fromJson(json, Scenery.class);
        System.out.println(sceneryBean);
        //结果 : Scenery{id=1, name='天坛公园', address='北京'}
    }

    //json和ArrayList相互转换
    private void JSON4ArrayList() {
        List<Scenery> list = new ArrayList<>();
        list.add(new Scenery(1, "野人谷风景区", "湖北"));
        list.add(new Scenery(2, "绿野山庄", "浙江"));
        list.add(new Scenery(3, "天坛公园", "北京"));

        // 创建 gson 工具类
        Gson gson = new Gson();
        // 使用 toJson() 方法 把list集合转换为json字符串
        String json = gson.toJson(list);
        System.out.println(json);
        //结果 : [{"id":1,"name":"野人谷风景区","address":"湖北"},{"id":2,"name":"绿野山庄","address":"浙江"},{"id":3,"name":"天坛公园","address":"北京"}]


        // fromJson 把json字符串转换回list集合
        // toJson() 是把对象转换为json字符串
        // fromJson是把json字符串转换回java对象
        // 如果是转回一个JavaBean.则第二个参数是 转换的javaBean的具体类型
        // 如果是转回一个集合.则第二个参数是type类型
        // 使用fromJson() 方法将json字符串转换为list集合
        //使用匿名内部类
        List<Scenery> list1 = gson.fromJson(json, new TypeToken<List<Scenery>>() {
        }.getType());
        System.out.println(list1);
        //结果 : [Scenery{id=1, name='野人谷风景区', address='湖北'}, Scenery{id=2, name='绿野山庄', address='浙江'}, Scenery{id=3, name='天坛公园', address='北京'}]
        // 在转成javaBean对象
        Scenery scenery = list1.get(0);
        System.out.println(scenery);
        //结果 : Scenery{id=1, name='野人谷风景区', address='湖北'} 作者：IT编程干货资源 https://www.bilibili.com/read/cv5802457/ 出处：bilibili
    }

    //json和map相互转换
    private void JSON4Map() {
        Map<String, Scenery> sceneryMap = new HashMap<>();
        sceneryMap.put("CN10121010103A", new Scenery(1, "杭州极地海洋公园", "杭州"));
        sceneryMap.put("CN10121010104A", new Scenery(2, "雷峰塔", "杭州"));
        sceneryMap.put("CN10109060801A", new Scenery(3, "八达岭长城", "北京"));
        Gson gson = new Gson();
        // 所有的java对象转成json字符串都统一使用 toJson() 方法
        String json = gson.toJson(sceneryMap);
        System.out.println(json);
        //结果 : {"CN10121010103A":{"id":1,"name":"杭州极地海洋公园","address":"杭州"},"CN10121010104A":{"id":2,"name":"雷峰塔","address":"杭州"},"CN10109060801A":{"id":3,"name":"八达岭长城","address":"北京"}}


        // 吧json字符串转换回 map集合
        // 使用匿名内部类
        Map<String, Scenery> map = gson.fromJson(json, new TypeToken<Map<String, Scenery>>() {
        }.getType());
        System.out.println(map);
        //结果 : {CN10121010103A=Scenery{id=1, name='杭州极地海洋公园', address='杭州'}, CN10121010104A=Scenery{id=2, name='雷峰塔', address='杭州'}, CN10109060801A=Scenery{id=3, name='八达岭长城', address='北京'}}
        Scenery cn10109060801A = map.get("CN10109060801A");
        System.out.println(cn10109060801A);
        // 结果 : Scenery{id=3, name='八达岭长城', address='北京'} 作者：IT编程干货资源 https://www.bilibili.com/read/cv5802457/ 出处：bilibili
    }

}