https://github.com/orhanobut/logger

* 功能
线程信息：log在哪个线程
类信息：log在哪个类
方法信息：log在哪个方法的哪一行
漂亮地打印json
漂亮的打印XML
漂亮的换行分割
整洁的输出
跳转到源代码

* 优点
默认实现是对于android.util.Log的封装
弥补了“android的logcat的message有字符长度的限制,超过将直接截断”的缺陷
支持参数添加占位符来格式化字符串，Logger.d(“hello %s”, “world”);
支持直接打印List，Set，Map，数组类型等引用类型
指定任意TAG
配置初始化选项
支持自定义CustomLogAdapter实现LogAdapter，替换android.util.Log、

* 打印方式
Logger.v(String message); // VERBOSE级别，可添加占位符
Logger.d(Object object); // DEBUG级别，打印对象
Logger.d(String message); // DEBUG级别，可添加占位符
Logger.i(String message); // INFO级别，可添加占位符
Logger.w(String message); // WARN级别，可添加占位符
Logger.e(String message); // ERROR级别，可添加占位符
Logger.e(Throwable throwable, String message); // ERROR级别，可添加占位符
Logger.wtf(String message); // ASSERT级别，可添加占位符
Logger.xml(String xml); Logger.json(String json);