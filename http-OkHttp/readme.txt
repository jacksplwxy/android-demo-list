简介
android网络请求框架之OkHttp，一个处理网络请求的开源项目,是安卓端最火热的轻量级框架,用于替代HttpUrlConnection和Apache HttpClient(android API23 6.0里已移除HttpClient，但仍可引入Jar包使用)。
OkHttp是一个高效的HTTP客户端

特性：
允许连接到同一个主机地址的所有请求,提高请求效率
共享Socket,减少对服务器的请求次数
通过连接池,减少了请求延迟
缓存响应数据来减少重复的网络请求
减少了对数据流量的消耗
自动处理GZip压缩

功能：
get,post请求
文件的上传和下载
加载图片
支持请求回调，直接返回对象、对象集合
支持session的保持

· demo文档：https://www.jianshu.com/p/bb57bc65e4ce