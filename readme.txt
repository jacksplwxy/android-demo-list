* SDK：
  · 概念：Software Development Kit，即软件开发工具包
  · 分类：
    ~ Java SDK：Java Software Development Kit，简称JDK，java软件开发工具包
    ~ Android SDK：Android Software Development Kit，Android软件开发工具包
  · Android SDK：相当于安卓机器的操作系统，类似与windows操作系统，其也有版本号，我现在用的版本号是23 ，没有android sdk开发包的支持，就无法进行android开发
  · JDK：包含了Java的开发环境和运行环境，主要用于开发JAVA程序，面向Java程序的开发者。具体包括jre、java开发包（java编译器和java解释执行器）和java类库共3大类
  · JRE：JRE(JAVA Runtime Environment，JAVA运行环境)提供了Java的运行环境，主要用于执行JAVA程序，面向Java程序的使用者。


* 作用及版本：
  · Android SDK Version：
    ~ 作用类似于浏览器/操作系统
    ~ 版本列表：https://www.jianshu.com/p/cd9844619210
  · Android SDK Build-tools Version：
    ~ 作用类似于vue-cli；
    ~ 您应保持更新 Build Tools 组件，可以使用 Android SDK 管理器下载该组件的最新版本。如果您使用的是 Android Plugin for Gradle 3.0.0 或更高版本，那么您的项目会自动使用该插件指定的默认版本的 Build Tools。
  · JDK Version：
    ~ 作用类似于js
    ~ JDK Version与Android SDK Version的编译关系：https://www.zhihu.com/question/273898628/answer/370891328
    ~ JDK Version与Android SDK Version的版本对应关系：https://www.cnblogs.com/dawugui5460/articles/7808530.html
  · Gradle Version：
    ~ 作用类似于webpack
    ~ Gradle Version与Android SDK Version的版本对应关系：
  · Android Gradle Plugin Version：
    ~ 作用类似于webpack中loader，将java编译为apk文件
    ~ Android Gradle Plugin Version与Gradle Version的版本对应关系：https://developer.android.google.cn/studio/releases/gradle-plugin
  · Android Project和app中两个build.gradle配置的区别
    ~ 一般创建一个android项目后回出现两个gradle:一个build.gradle(app)，一个build.gradle(Project)，顾名思义就是一个是用来配置整个工程的的一个是用来配置app的
    ~ 在Project中的gradle的dependencies 指添加依赖是使用classpath的，classpath一般是添加buildscript本身需要运行的东西，那么buildscript是用来什么呢？buildScript是用来加载gradle脚本自身需要使用的资源，可以声明的资源包括依赖项、第三方插件、maven仓库地址等
　　~ 在app中的gradle中dependencies 中添加的使应用程序所需要的依赖包，也就是项目运行所需要的东西。
    ~ Project的build.gradle文件
      buildscript {//这里是gradle脚本执行所需依赖，分别是对应的maven库和插件
          repositories {
              google()//从Android Studio3.0后新增了google()配置，可以引用google上的开源项目
              jcenter()//是一个类似于github的代码托管仓库，声明了jcenter()配置，可以轻松引用 jcenter上的开源项目
          }
          dependencies {
              classpath 'com.android.tools.build:gradle:3.0.0'此处是android的插件gradle，gradle是一个强大的项目构建工具
              // NOTE: Do not place your application dependencies here; they belong
              // in the individual module build.gradle files
          }
      }
      allprojects {//这里是项目本身需要的依赖，比如项目所需的maven库
          repositories {
              google()
              jcenter()
          }
      }
      // 运行gradle clean时，执行此处定义的task任务。
      // 该任务继承自Delete，删除根目录中的build目录。
      // 相当于执行Delete.delete(rootProject.buildDir)。
      // gradle使用groovy语言，调用method时可以不用加（）。
      task clean(type: Delete) {
          delete rootProject.buildDir
      }
    ~ Module的build.gradle文件：
      // 声明是Android程序，
      //com.android.application 表示这是一个应用程序模块
      //com.android.library 标识这是一个库模块
      //而这区别：前者可以直接运行，后着是依附别的应用程序运行
      apply plugin: 'com.android.application'
      android {
          signingConfigs {// 自动化打包配置
              release {// 线上环境
                  keyAlias 'test'
                  keyPassword '123456'
                  storeFile file('test.jks')
                  storePassword '123456'
              }
              debug {// 开发环境
                  keyAlias 'test'
                  keyPassword '123456'
                  storeFile file('test.jks')
                  storePassword '123456'
              }
          }
          compileSdkVersion 27//设置编译时用的Android版本
          defaultConfig {
              applicationId "com.billy.myapplication"//项目的包名
              minSdkVersion 16//项目最低兼容的版本
              targetSdkVersion 27//项目的目标版本
              versionCode 1//版本号
              versionName "1.0"//版本名称
              flavorDimensions "versionCode"
              testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"//表明要使用AndroidJUnitRunner进行单元测试
          }
          buildTypes {// 生产/测试环境配置
              release {// 生产环境
                  buildConfigField("boolean", "LOG_DEBUG", "false")//配置Log日志
                  buildConfigField("String", "URL_PERFIX", "\"https://release.cn/\"")// 配置URL前缀
                  minifyEnabled false//是否对代码进行混淆
                  proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'//指定混淆的规则文件
                  signingConfig signingConfigs.release//设置签名信息
                  pseudoLocalesEnabled false//是否在APK中生成伪语言环境，帮助国际化的东西，一般使用的不多
                  zipAlignEnabled true//是否对APK包执行ZIP对齐优化，减小zip体积，增加运行效率
                  applicationIdSuffix 'test'//在applicationId 中添加了一个后缀，一般使用的不多
                  versionNameSuffix 'test'//在applicationId 中添加了一个后缀，一般使用的不多
              }
              debug {// 测试环境
                  buildConfigField("boolean", "LOG_DEBUG", "true")//配置Log日志
                  buildConfigField("String", "URL_PERFIX", "\"https://test.com/\"")// 配置URL前缀
                  minifyEnabled false//是否对代码进行混淆
                  proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'//指定混淆的规则文件
                  signingConfig signingConfigs.debug//设置签名信息
                  debuggable false//是否支持断点调试
                  jniDebuggable false//是否可以调试NDK代码
                  renderscriptDebuggable false//是否开启渲染脚本就是一些c写的渲染方法
                  zipAlignEnabled true//是否对APK包执行ZIP对齐优化，减小zip体积，增加运行效率
                  pseudoLocalesEnabled false//是否在APK中生成伪语言环境，帮助国际化的东西，一般使用的不多
                  applicationIdSuffix 'test'//在applicationId 中添加了一个后缀，一般使用的不多
                  versionNameSuffix 'test'//在applicationId 中添加了一个后缀，一般使用的不多
              }
          }
          sourceSets {//目录指向配置
              main {
                  jniLibs.srcDirs = ['libs']//指定lib库目录
              }
          }
          packagingOptions{//打包时的相关配置
              //pickFirsts做用是 当有重复文件时 打包会报错 这样配置会使用第一个匹配的文件打包进入apk
              // 表示当apk中有重复的META-INF目录下有重复的LICENSE文件时  只用第一个 这样打包就不会报错
              pickFirsts = ['META-INF/LICENSE']
      
              //merges何必 当出现重复文件时 合并重复的文件 然后打包入apk
              //这个是有默认值得 merges = [] 这样会把默默认值去掉  所以我们用下面这种方式 在默认值后添加
              merge 'META-INF/LICENSE'
      
              //这个是在同时使用butterknife、dagger2做的一个处理。同理，遇到类似的问题，只要根据gradle的提示，做类似处理即可。
              exclude 'META-INF/services/javax.annotation.processing.Processor'
          }
          productFlavors {
              wandoujia {}
              xiaomi {}
              _360 {}
          }
          productFlavors.all {
                  //批量修改，类似一个循序遍历
              flavor -> flavor.manifestPlaceholders = [IFLYTEK_CHANNEL: name]
          }
          //程序在编译的时候会检查lint，有任何错误提示会停止build，我们可以关闭这个开关
          lintOptions {
              abortOnError false
              //即使报错也不会停止打包
              checkReleaseBuilds false
              //打包release版本的时候进行检测
          }
      }
      dependencies {
          //项目的依赖关系
          implementation fileTree(include: ['*.jar'], dir: 'libs')
          //本地jar包依赖
          implementation 'com.android.support:appcompat-v7:27.1.1'
          //远程依赖
          implementation 'com.android.support.constraint:constraint-layout:1.1.2'
          testImplementation 'junit:junit:4.12'
          //声明测试用例库
          androidTestImplementation 'com.android.support.test:runner:1.0.2'
          androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
      }
    ~ 《史上最全Android build.gradle配置详解》：https://blog.csdn.net/u012587637/article/details/88825607


* 编译：
  · Android App Bundle：
    ~ 什么是Android App Bundle：
      ~ Google Play 推出的新应用服务模式名叫“Dynamic Delivery”，新的服务支持上传的文件就是Android App Bundle文件（.aab文件），之后用户在下载应用时，Google Play应用服务会针对下载用户的设备配置生成并提供经过优化的 APK，因此他们只需下载运行应用所需的代码和资源，不需要下载原先apk中的所有资源文件，从而让用户获取到最轻量的apk。
      ~ Android App Bundle 是一种全新的上传格式，是用来上传到 Google Play 以支持 Dynamic Delivery应用服务 的一种文件类型（文件扩展名为 .aab）
      ~ Android App Bundle其中包含应用的所有编译好的代码和资源，但 APK 生成及签名工作则交给 Google Play 来处理
    ~ App Bundles 将一个 apk 拆分成多个 apk，我们的 apk 一般会被拆分为如下几个部分：
      ~ Base Apk：首次安装的apk，公共代码和资源，所以其他的模块都基于Base Apk；
      ~ Configuration APKs：native libraries 和适配当前手机屏幕分辨率的资源；
      ~ Dynamic feature APKs：不需要在首次安装就加载的模块。
    ~ .aab文件：
      和.apk一样也是一个zip的文件， 不一样的是apk可以直接安装到手机，而.aab文件不能直接安装，需要通过Google Play 或者 bundletool 工具 生成优化后的apk才能安装到手机。
    ~ 编译 Android App Bundle 文件：
      ~ 编译Android App Bundle 需支持以下条件： Android Studio 3.2 或者更高版本
      ~ Android Studio中可视化界面操作：Build -> Build Bundle(s)/APK(s) -> Build Bundle(s)
      ~ 编译结束后.aab文件目录位置为： app -> build -> outputs -> bundle -> debug/release
    ~ 文档：https://www.jianshu.com/p/c3aca7d3c076
  · 编译和运行命令：
    ~ Make Module：编译自上次编译以来已修改的所选模块中的所有源文件，以及所选模块以递归方式依赖的所有模块。编译包括相关源文件和所有关联的构建任务。我们可以通过在 Project 窗口中选择模块名称或模块的某个文件来选择要构建的模块。此命令不会生成APK。
    ~ Make Project：生成所有模块。
    ~ Clean Project：删除所有中间/缓存的构建文件。
    ~ Rebuild Project：针对所选编译变体运行Clean Project并生成APK。
    ~ Build Bundle(s)/APK(s) > Build APK(s)：为所选的变体构建当前项目中所有模块的APK。构建完成后，系统将显示确认通知，提供指向APK文件的链接以及用于在APK分析器中对其进行分析的链接。未签名的apk不能安装，只能调试
    ~ Build Bundle(s)/APK(s) > Build Bundle(s)：为所选的变体构建当前项目中所有模块的Android App Bundle。构建完成后，系统将显示确认通知，提供指向 app bundle 的链接以及用于在 APK 分析器中对其进行分析的链接。
    ~ Generate Signed Bundle/APK：使用向导打开一个对话框以设置新的签名配置，并构建已签名的app bundle 或APK。我们需要先使用发布密钥为我们的应用签名，然后才能将其上传到Play管理中心。
  · R类：
    ~ 在android开发中会用到各种资源，例如图片、values等。除了assets和res/raw资源被原装不动地打包进APK之外，其它的资源都会被编译为二进制的resources.arsc文件，并被赋予一个资源ID。android自动生成一个R类，专门用于映射管理二进制资源。
    ~ 在java文件中引用资源时，直接对R.java文件中的内容进行调用，例如：R.id.button_1，表示调用R这个类下面的id这个内部类中的button_1属性。在xml文件中@符号表示调用R.java文件中的资源，举个例子：@style/AppTheme 中的@符号可以理解为R这个类，而style表示R类中的style内部类，AppTheme表示，style内部类中的AppTheme属性。站在设计前台的角度，所有资源都是有具体路径，都是可视的。
    ~ 生成activity时，引入布局文件动态生成窗体控件，所有的R.id都是临时生成的，在内存中的代码就是public static final int tv=0x7f0800d1，通过R.id.tv读取0x7f0800d1然后定位控件，也就是说R.id只不过是一个内存编码，这跟文件资源是完全不同的，在不同布局文件中名字相同是没有关系的，但同一布局文件中不能有相同的名字，否则无法定位控件。R文件只是一个管理资源入口，包含许多不同的资源管理类，不同类的资源管理基本上没有关系。
    ~ R文件标红错误解决：每次修改项目设置后，系统都会自动重新生成app.iml，自动生成的app.iml会丢失R文件都引用路径，在app.iml文件中添加R文件路径<sourceFolder url="file://$MODULE_DIR$/build/generated/not_namespaced_r_class_sources/debug/processDebugResources/r" isTestSource="false" generated="true" />即可



* Android Stadio的依赖：
  · 添加依赖的2种方式
    ~ Add Library Dependency：是通过访问网址的形式将依赖库进行添加依赖包（可能需要科学上网）
    ~ Add Jar/AAR Dependency：将依赖库的.jar文件已经下载下来了，通过.jar/.aar文件的形式添加依赖
  · 依赖的配置：
    ~ implementation：
    ~ releaseImplementation：仅仅针对release 模式的编译和最终的release apk打包
    ~ debugImplementation：只在debug模式的编译和最终的debug apk打包时有效
    ~ testImplementation
    ~ testReleaseImplementation
    ~ testDebugImplementation
    ~ androidTestImplementation：只在单元测试代码的编译以及最终打包测试apk时有效


* Android SO文件：
  SO文件是Linux下共享库文件，它的文件格式被称为ELF文件格式。由于Android操作系统的底层基于Linux系统，所以SO文件可以运行在Android平台上。Android系统也同样开放了C/C++接口供开发者开发Native程序。由于基于虚拟机的编程语言JAVA更容易被人反编译，因此越来越多的应用将其中的核心代码以C/C++为编程语言，并且以SO文件的形式供上层JAVA代码调用，以保证安全性


* AndroidManifest.xml：
  · 应用清单:
    ~ action：向 Intent 过滤器添加操作
    ~ activity：声明实现应用部分可视化界面的 Activity
    ~ activity-alias：Activity 的别名
    ~ application：应用的声明
    ~ category：向 Intent 过滤器添加类别名称
    ~ compatible-screens：指定应用与之兼容的各个屏幕配置
    ~ data：向 Intent 过滤器添加数据规范
    ~ grant-uri-permission：指定父内容提供程序有权访问的应用数据的子集
    ~ instrumentation：声明用于监控应用与系统交互的 Instrumentation 类
    ~ intent-filter：指定 Activity、服务或广播接收器可以响应的 Intent 类型
    ~ manifest：AndroidManifest.xml 文件的根元素
    ~ meta-data：可以向父组件提供的其他任意数据项的名称值对
    ~ path-permission：定义内容提供程序中特定数据子集的路径和所需权限
    ~ permission：声明可用于限制对此应用或其他应用的特定组件或功能的访问权限的安全权限
    ~ permission-group：声明相关权限的逻辑分组的名称
    ~ permission-tree：声明权限树的基名。应用拥有树中所有名称的所有权
    ~ provider：声明内容提供程序组件
    ~ receiver：将广播接收器（BroadcastReceiver 子类）声明为应用的组件之一
    ~ service：将服务（Service 子类）声明为应用的一个组件
    ~ supports-gl-texture：声明应用支持的一种 GL 纹理压缩格式
    ~ supports-screens：使您能够指定应用支持的屏幕尺寸，并为比应用支持的最大屏幕还大的屏幕启用屏幕兼容模式
    ~ uses-configuration：指示应用所需的硬件和软件功能
    ~ uses-feature：Android Market会根据uses-feature过滤所有你设备不支持的应用。通过使用<uses-feature>元素，一个应用可以指定它所支持的硬件型号，举个例子，有些设备不支持多点触控或者OpenGL ES 2.0，那么过滤器就会过滤需要这些硬件支持（多点触控或者OpenGL ES 2.0）的应用，用户就不会在android market上看到这些应用。
    ~ uses-library：指定应用必须与之关联的共享库。此元素告知系统将库的代码添加到软件包的类加载器中
    ~ uses-permission：指定用户必须授予的系统权限，以便应用正常运行。当（在运行 Android 5.1 和更低版本的设备上）安装应用或（在运行 Android 6.0 和更高版本的设备上）运行应用时，用户会授予权限
    ~ uses-permission-sdk-23：指明应用需要特定权限，但仅当应用在 Android 6.0（API 级别 23）或更高版本的设备上安装时才需要。如果设备运行的是 API 级别 22 或更低版本，则应用没有指定的权限
    ~ uses-sdk：您可以通过整数形式的 API 级别表示应用与一个或多个版本的 Android 平台的兼容性。应用表示的 API 级别将与给定 Android 系统的 API 级别进行比较，其结果在不同 Android 设备上可能有所差异。
  · 设置安卓程序入口程序：
    通过指定android:name来确定应用的入口程序，默认为android.app.Application类，例如下面设置了入口为UniteApp这个类，该类会继承Application，这样当应用程序退出后其生命周期也跟着结束
    <application android:name=".UniteApp"></application>
    这个类的作用是为了放一些全局的和一些上下文都要用到的变量和方法
  · 设置安卓程序入口页面：
    把下面这段代码放到Activity中，该Activity即成为入口
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
  · 文档：https://developer.android.google.cn/guide/topics/manifest/manifest-intro?hl=zh-cn%22


* xml：
  · 解析xml文件的3种方式：
    ~ SAX解析：SAX（Simple API for XML）是一个解析速度快并且占用内存少的XML解析器，非常适合用于Android等移动设备。SAX解析XML文件采用的是事件驱动，也就是说，它并不需要解析完整个文档，在按内容顺序解析文档的过程中，SAX会判断当前读到的字符是否合法XML语法中的某部分，如果符合就会触发事件。所谓事件，其实就是一些回调（callback）方法，这些方法(事件)定义在ContentHandler接口。
    ~ Pull解析：Pull解析是一个while循环，随时可以跳出。Pull解析器的工作方式为允许你的应用程序代码主动从解析器中获取事件，因此可以在满足了需要的条件后不再获取事件，结束解析工作。
    ~ DOM解析：DOM解析XML文件时，会将XML的所有内容读取到内存中，然后允许您使用DOM API遍历XML树、检索所需的数据。因为DOM需要将所有内容读取到内存中，所以内存的消耗比较大，不建议使用DOM解析XML文件，若文件较小可行。
  · 常用单位：
    ~ 在布局中，除了字体大小单位用sp，其他都用dp
    ~ 文档：https://blog.csdn.net/huangjianfeng21/article/details/119616651
  · android盒模型：与html一致
  · 尺寸：
    ~ wrap_content：表示和自身内容一样的长度
    ~ match_parent：和父组件一样的长度
    ~ layout_weight：线性分割原本应有长度的权重
  · 常见属性：
    ~ 文档：https://www.jianshu.com/p/910685a8ea91
    ~ xmlns:xmlns是xml namespace的缩写，意思是xml命名空间
    ~ xmlns:android="http://schemas.android.com/apk/res/android：
      ~ :android是系统默认的命名空间，能够给予系统已有属性提示
      ~ schemas的意思是xml文件的约束（也就是xml的书写规范，类似于模板），还有一种xml约束是DTD，但schemas相对于DTD来说克服了DTD的局限性，扩展性强
      ~ 有了他，Android Studio就会在我们编写布局文件的时候给出提示，提示我们可以输入什么，不可以输入什么。也可以理解为语法文件，或者语法判断器
    ~ xmlns:app="http://schemas.android.com/apk/res-auto：
      ~ 在项目需求中，我们往往使用系统自带的属性以及控件是不够的，我们可能需要导入自定义控件的一些属性，或者support支持包之类的
      ~ :app是自定义的，改成其它名字也可以，常用于自定义控件的自定义属性，res-auto会自动查找项目下的自定义属性，也可以在后面跟上包名
      ~ 为了引入自定义的属性，我们可以xmlns:前缀=http://schemas.android.com/apk/res/你的应用程序包路径，将其导入
      ~ 现在的普遍做法是使用xmlns:app="http://schemas.android.com/apk/res-auto"，因为res-auto可以引用所有的自定义包名
    ~ xmlns:tools="http://schemas.android.com/tools：
      ~ tools可以告诉Android Studio，哪些属性在运行的时候是被忽略的，只在设计布局的时候有效
      ~ tools可以覆盖android的所有标准属性，将android:换成tools:即可；同时在运行的时候就连tools:本身都是被忽略的，不会被带进apk中
      ~ tools相关的属性主要是提示给编辑器的，也就是用来辅助编辑器展示效果，在真机上这些属性是没有作用的。例如tools:context就是将这个layout文件和后面的Activity进行关联，这样编辑器在展示布局效果的时候，就能针对Activity的一些属性进行有针对性的处理。
  · 布局：
    ~ 约束布局
      ~ ConstraintLayout 
      ~ 文档：https://developer.android.google.cn/training/constraint-layout?hl=zh-cn%22
    ~ MotionLayout 
      ~ MotionLayout
      ~ MotionLayout 是一种布局类型，可帮助您管理应用中的运动和微件动画。MotionLayout 是 ConstraintLayout 的子类，在其丰富的布局功能基础之上构建而成
    ~ 线性布局 
      ~ LinearLayout
      ~ 把孩子都摆在同一条横线上
      ~ 可以通过设置android:orientation="vertical"将孩子摆在同一条竖线上
      ~ 给孩子平均宽高度时，可以给权重：android:layout_width="0dp" android:layout_weight="1"
    ~ 相对布局：
      ~ RelativeLayout
      ~ 相对于父控件
      ~ 设置居中：android:layout_centerInParent="true"
      ~ 设置相对于其他组件布局：
        为参考组件添加id：android:id="@+/center_button"
        为目标组件设置参考组件id：android:layout_toLeftOf="@id/center_button"
    ~ 绝对布局：
      ~ AbsoluteLayout
      ~ 已废弃
      ~ 靠x/y布局
    ~ 表格布局：
      ~ TableLayout
      ~ 类似html中的table
  · 事件：
    ~ 添加事件的2种方式：
      ~ onClick：在xml文件中对控件进行绑定事件（用得比较少）
      ~ setOnClickListener：在java文件中通过id声明的方式找控件，然后对这个控件设置点击时间


* android屏幕适配和资源命名规则：
  · 文档：
    ~ 《android res 资源匹配目录 、 drawable与 mipmap的区别（在res目录下，有各种各样的资源文件目录，这些目录的名字是有规则的，为了更好的适配多种设备，我们需要了解这些目录的命名规则，及各中限定符的意义，熟悉系统匹配这些资源的规则）》：https://www.jianshu.com/p/6743f4303321?t=123
    ~ 《Carson带你学Android：屏幕适配-最全面的解决方案》：https://www.jianshu.com/p/ec5a1a30694b
    ~ 《Android适配全面总结（一）----屏幕适配》：https://www.jianshu.com/p/7aa34434ad4d
    ~ 《Android适配不同的屏幕》：https://jingyan.baidu.com/article/624e745948a78a34e8ba5abf.html
    ~ 《Android平板上开发应用的一点心得——精确适配不同的dpi和屏幕尺寸》：https://www.cnblogs.com/zealotrouge/archive/2012/11/23/2784774.html
    ~ 《Android hdpi ldpi mdpi xhdpi xxhdpi适配详解》：https://blog.csdn.net/u010413574/article/details/52790253
  · 概念：
    ~ 分辨率。分辨率就是手机屏幕的像素点数，一般描述成屏幕的“宽×高”，安卓手机屏幕常见的分辨率有480×800、720×1280、1080×1920等。720×1280表示此屏幕在宽度方向有720个像素，在高度方向有1280个像素。
    ~ 屏幕大小。屏幕大小是手机对角线的物理尺寸，以英寸（inch）为单位。比如某某手机为“5寸大屏手机”，就是指对角线的尺寸，5寸×2.54厘米/寸=12.7厘米。
    ~ 密度（dpi，dots per inch；或PPI，pixels per inch）。从英文顾名思义，就是每英寸的像素点数，数值越高当然显示越细腻。假如我们知道一部手机的分辨率是1080×1920，屏幕大小是5英寸，你 能否算出此屏幕的密度呢？哈哈，中学的勾股定理派上用场啦！通过宽1080和高1920，根据勾股定理，我们得出对角线的像素数大约是2203，那么用 2203除以5就是此屏幕的密度了，计算结果是440。440dpi的屏幕已经相当细腻了。
  · 密度限定符
    ~ ldpi：	适用于低密度 (ldpi) 屏幕 (0~ 120dpi) 的资源。
    ~ mdpi：	适用于中密度 (mdpi) 屏幕 (120~ 160dpi) 的资源（这是基准密度）
    ~ hdpi：	适用于高密度 (hdpi) 屏幕 (160~ 240dpi) 的资源。
    ~ xhdpi：	适用于加高 (xhdpi) 密度屏幕 (240~ 320dpi) 的资源。
    ~ xxhdpi：	适用于超超高密度 (xxhdpi) 屏幕 (320~ 480dpi) 的资源。
    ~ xxxhdpi：适用于超超超高密度 (xxxhdpi) 屏幕 (480~ 640dpi) 的资源。
  · android常见的屏幕适配方法：
    ~ 让你的布局能充分的自适应屏幕
    ~ 根据屏幕的配置来加载合适的UI布局（类似web中的响应式布局）
       ~  方式1：res文件路径下新建适配不同分辨率的手机的layout文件夹,与layout文件夹同级，例如：layout-1280*720、layout-1920*1080
       ~  方式2：在values后面添加相应的尺寸
    ~ 确保正确的布局应用在正确的设备屏幕上
    ~ 提供可以根据屏幕大小自动伸缩的图片
  · 项目res/目录中支持的资源目录：https://developer.android.google.cn/guide/topics/resources/providing-resources?hl=zh-cn%22
    ~ animator/：用于定义属性动画的 XML 文件。
    ~ anim/：用于定义渐变动画的 XML 文件。（属性动画也可保存在此目录中，但为了区分这两种类型，属性动画首选 animator/ 目录。）
    ~ color/：用于定义颜色状态列表的 XML 文件。请参阅颜色状态列表资源
    ~ drawable/：位图文件（.png、.9.png、.jpg、.gif）或编译为以下可绘制对象资源子类型的 XML 文件：
    ~ mipmap/：适用于不同启动器图标密度的可绘制对象文件。如需了解有关使用 mipmap/ 文件夹管理启动器图标的详细信息，请参阅管理项目概览。
    ~ layout/：用于定义用户界面布局的XML文件
    ~ menu/：用于定义应用菜单（如选项菜单、上下文菜单或子菜单）的 XML 文件。请参阅菜单资源。
    ~ raw/：需以原始形式保存的任意文件。如要使用原始InputStream打开这些资源，请使用资源ID（即 R.raw.filename）调用Resources.openRawResource()。
    ~ values/：包含字符串、整型数和颜色等简单值的 XML 文件。对于可在此目录中创建的资源，下面给出了相应的文件名约定：
       ~ arrays.xml：资源数组（类型数组）。
       ~ colors.xml：颜色值。
       ~ dimens.xml：尺寸值。
       ~ strings.xml：字符串值。
       ~ styles.xml：样式。
    ~ xml/：可在运行时通过调用 Resources.getXML() 读取的任意 XML 文件。各种 XML 配置文件（如可搜索配置）都必须保存在此处。
    ~ font/：带有扩展名的字体文件（如 .ttf、.otf 或 .ttc），或包含 <font-family> 元素的 XML 文件。如需详细了解作为资源的字体，请参阅 XML 中的字体。


* Android四大组件：
  · Activity
  · Service
  · BroadcastReceiver
  · ContentProvider


* android各类之间的继承关系：
  · View继承关系：
    View
        → AnalogClock
        → ProcessBar
          → AbsSeeBar
            → SeekBar
              → AppCompatSeekBar
            → RatingBar
              → AppCompatRatingBar
          → ContentLoadingProgressBar
        → ImageView
          → AppCompatImageView
          → CircleImageView
          → QuickContactBadge
          → ImageButton
            → ZoomButton
        → SurfaceView
          → VideoView
          → GLSurfaceView
        → ViewGroup
          → ActivityChooserView
          → CoordinatorLayout
          → PagerTitleStrip
          → MetroLayout
          → b
          → Toolbar
          → 
          → ViewPager：
            ~ 这个类可以让用户左右切换当前的View
            ~ ViewPager直接继承自ViewGroup类，它是一个容器类，可以在其中添加其他的View
            ~ ViewPager类需要一个PagerAdapter适配器，来为他提供数据。
            ~ ViewPager经常和Fragment一起使用，并且提供了专门的FragmentPagerAdapter和FragmentStatePagerAdapter类
          → MapView
          → SlidlingDrawer
          → AdapaterView
            → AdapaterViewAnimator
            → AbsSpinner
              → Spinner
                ~ 下拉列表
                ~ https://www.cnblogs.com/linjiqin/archive/2011/02/24/1963998.html
              → Gallery
            → HorizontalListView
            → AbsListView
              → GridView
              → ListView
                ~ 滚动列表
              → ExpandableListView
          → DrawerLayout
          → AbsActionBarView
          → TvView
          → SwipeRefreshLayout
          → ActionBarOverlayLayout
          → GridLayout
          → LinearLayoutCompat
          → AutoLinefeedLayout
          → FramgementBreadCrumbs
          → CenterLayout
          → RecyclerView
          → Toolbar
          → FlowLayout
            → TagFlowLayout
          → SlidingPaneLayout
          → RelativeLayout
            → DialerFilter
            → TwoLineListitem
          → LinearLayout
            → AppBarLayout
            → AutoLinerLayout
            → ZoomControls
            → DatePicker
            → BGARefreshLayout
            → RadioGroup
            → TextInputLayout
            → AdapterSupportedLinearLayout
            → StickListViewHeader
            → Sliding TabStrip in TabLayout
            → TabView in TabLayout
            → TextViewScrolll
            → ListMenuItemView
            → XListViewHeader
            → ButtonBarLayout
            → XListViewFooter
            → BGAStickyNavlayout
            → SearchView
            → TableLayout
            → i in TskGuide
            → StickLsitViewFooter
            → FitWindowsLinearLayout
            → SnackbarLayout in Snackbar
            → MyLinearLayout
            → ActionMenuView
            → TableRow
            → TavWidget
            → ShapeHintView
            → NumberPicker 
          → FrameLayout
            → CalendarView
            → AutoFrameLayout
            → FitWindowsFrameLayout
            → CommentComponentView
            → c
            → AttentionComponentView
            → MediaController
            → GestureOverlayView
            → DatePicker
            → HorizontalScrollView
            → AppWidgetHostView
            → NestedScrollView
            → CollapsingToolbarLayout
            → SwipeListLayout
            → CollapsibleActionViewWrapper in MenuItemWrapperICS
            → TabHost
            → ScrimInsetsFrameLayout
            → ActionBarContainer
            → TagView
            → ScrollView
            → ViewAnimator
            → TimePicker
            → ContentFrameLayout
            → SectionFixedScrollView
            → NoSaveStateFrameLayout
            → Ticker
            → ScaleLayout
          → AbsoluteLayout
            → WebView
        → TextView
          → AppCompatTextView
            → ActionMenuItemView
          → Button
            → AppCompatButton
            → CompoundButton
              → ToggleButton
              → RadioButton
              → CheckBox
          → CheckedTextView
            → AppCompatCheckedTextView
          → Chronometer
          → DigitalTitle
          → DigitalClock
          → EditText
            → AutoCompleteTextView
              → MultiAutiCompleteTextView
              → AppCompatAutoCompleteTextView
            → AutoCompleteEditView
              → TextInoutEditText
            → ExtractEditText
          → TextClock
        → ViewStub
        → ViewStubCompat
        → TouchInterceptor in OverlayViewGroup in ViewOverlayApi4
        → KeyboardView
        → MediaRouteButton
        → MediaRouteButton
        → Space
        → TabItem
        → TextureView

  · 文档：
    ~ 《Android中主要类的继承关系梳理汇总》：https://zhuanlan.zhihu.com/p/32368063
    ~ 《android 中组件继承关系图，一目了然》：https://www.cnblogs.com/xuan52rock/p/5391252.html
* Context：上下文环境
  · 继承关系：
    Context（interface）
    ~ ContextIml
    ~ ContextWrapper
      ~ ContextThemeWrapper
        ~ Activity
      ~ Service
      ~ Application
  · 继承关系相关类：
    public class Application extends ContextWrapper implements ComponentCallbacks2  {}
    public class Activity extends ContextThemeWrapper implements Factory2, Callback, android.view.KeyEvent.Callback, OnCreateContextMenuListener, ComponentCallbacks2 {}
    public abstract class Service extends ContextWrapper implements ComponentCallbacks2 {}
    public class ContextThemeWrapper extends ContextWrapper {}
    public class ContextWrapper extends Context {}
    public abstract class Context {}
  · 区分：
    ~ getApplicationContext：
      ~ 返回应用的上下文，生命周期是整个应用，应用摧毁它才摧毁
      ~ 在当前app的任意位置使用这个函数得到的是同一个Context
      ~ 使用getApplicationContext取得的是当前app所使用的application，这在AndroidManifest中唯一指定
    ~ getContext：
      ~ getContext获取的是当前对象所在的Context
    ~ getApplication：
      ~ andorid开发中共享全局数据
      ~ 与getApplicationContext的区别：用起来没区别，都是返回应用的Application对象；但是从来源上，getApplication是Activity、Service里的方法，而getApplicationContext则是Context里的抽象方法，所以能调用到的它们的地方不一样
    ~ this：
      ~ 当前类是context的子类，一般是activity、application等
      ~ this:代表当前,在Activity当中就是代表当前的Activity，换句话说就是Activity.this在Activity当中可以缩写为this。
      ~ Activity.this：返回当前activity的上下文，生命周期只是它所在的Activity，activity 摧毁他就摧毁
    ~ getBaseContext()：
      ~ 返回由构造函数指定或setBaseContext()设置的上下文
  · Application、Activity、Service都是继承自Context，是应用运行时的环境，我们可以把Application看做是应用，Activity看做是一个界面，至于getApplicationContext和getApplication，他们返回的对象有可能不一样（虽然大部分时间是一样的，都是整个应用的上下文），如果想要拿到在manifest里面声明的那个Application，务必用getApplication
    this.getApplicationContext()获取的是这个应用程序的Context（associated with the Applicaiton），Activity.this获取的是这个Activity的Context，这两者的生命周期是不同的，前者的生命周期是整个应用的创建到销毁，后者的生命周期只是它所在的Activity(创建到销毁)


* Activity：
  · 定义：显示或者与用户进行交互的界面
  · 特点
    ~ 一个Activity通常是一个界面，是四大组件唯一能被用户感知的
    ~ 每个活动被实现为一个独立的类， 都是从活动基类继承过来
    ~ Activity之间通过Intent进行通信
  · 声明周期：
    ~ onCreate：在首次创建 Activity 时调用。系统向此方法传递一个 Bundle 对象，其中包含 Activity 的上一状态，不过前提是捕获了该状态，而后会调用onStart方法。（可以在此方法中执行所有正常的静态设置 ，比如：创建视图、将数据绑定到列表等等。）
    ~ onStart：在 Activity 即将对用户可见之前调用。而后如果Activity转入了前台就会调用onResume方法。 如果此时直接屏幕熄灭或者用户按下home键则会直接调用onStop方法，当然这种情况比较极端。
    ~ onResume：在 Activity 即将开始与用户进行交互之前调用。 此时，Activity 处于 Activity 堆栈的顶层，并具有用户输入焦点。当跳转另一个Activity，或者退出当前Activity后会调用onPause方法。
    ~ onPause：在系统即将开始继续另一个 Activity 时调用。 此方法通常用于确认对持久性数据的未保存更改、停止动画以及其他可能消耗 CPU 的内容，诸如此类。 它应该非常迅速地执行所需操作，因为它返回后，下一个 Activity 才能继续执行,所以不能执行耗时操作。而后正常情况下会调用onStop方法。但是有一种极端情况，就是如果这个时候快速让 当前Activity 返回前台，则会调用onResume方法。
    ~ onStop：在 Activity 对用户不再可见时调用。如果 Activity 被销毁，或另一个 Activity（一个现有 Activity 或新 Activity）继续执行并将其覆盖，就会调用此方法。而后如果 Activity 恢复与用户的交互，则会调用 onRestart 方法，如果 Activity 被销毁，则会调用onDestroy方法。
    ~ onRestart：在Activity被停止后再次启动时调用（即屏幕熄灭后再次回到app，按下home键后再次回到app），而后会调用onStart方法。
    ~ onDestroy：在 Activity 被销毁前调用，这是 Activity 收到的最后调用。 当 Activity 结束（对 Activity 调用了 finish 方法），或系统为节省空间而暂时销毁该 Activity 实例时，可能会调用它。 你可以通过 isFinishing 方法区分这两种情形。
  · 任务栈：
    任务栈Task，是一种用来放置Activity实例的容器，他是以栈的形式进行盛放，也就是所谓的先进后出，主要有2个基本操作：压栈和出栈，其所存放的Activity是不支持重新排序的，只能根据压栈和出栈操作更改Activity的顺序。启动一个Application的时候，系统会为它默认创建一个对应的Task，用来放置根Activity。默认启动Activity会放在同一个Task中，新启动的Activity会被压入启动它的那个Activity的栈中，并且显示它。当用户按下回退键时，这个Activity就会被弹出栈，按下Home键回到桌面，再启动另一个应用，这时候之前那个Task就被移到后台，成为后台任务栈，而刚启动的那个Task就被调到前台，成为前台任务栈，手机页面显示的就是前台任务栈中的栈顶元素。
  · 4种启动模式：
    ~ 标准模式（standard）：
      ~ 在清单文件中声明Activity时，如果不设置Activity的启动模式，系统会默认将其设置为standard。每次启动一个标准模式的Activity都会重新创建一个新的实例，不管这个Activity之前是否已经存在实例，一个任务栈中可以有多个实例，每个实例也可以属于不同的任务栈，谁启动了这个Activity，那么这个Activity实例就运行在启动它的那个Activity所在的栈中
      ~ 使用场景：正常的去打开一个新的页面，这种启动模式使用最多，最普通
    ~ 栈顶复用模式（singleTop）：
      ~ 在这种模式下，如果新启动的Activity已经位于任务栈的栈顶，那么此Activity不会被重新创建，只会重新调用onNewIntent方法，这个Activity的onCreate、onStart都不会被系统调用。如果新Activity实例已经存在但不在栈顶，那么重新创建Activity并放入栈顶
      ~ 使用场景：假设你在当前的Activity中又要启动同类型的Activity，此时建议将此类型Activity的启动模式指定为SingleTop，能够降低Activity的创建，节省内存。例如，假如一个新闻客户端，在通知栏收到了3条推送，点击每一条推送会打开新闻的详情页，如果为默认的启动模式的话，点击一次打开一个页面，会打开三个详情页，这肯定是不合理的。如果启动模式设置为singleTop，当点击第一条推送后，新闻详情页已经处于栈顶，当我们第二条和第三条推送的时候，只需要通过Intent传入相应的内容即可，并不会重新打开新的页面，这样就可以避免重复打开页面了。
    ~ 栈内复用模式（singleTask）：
      ~ 这是一种单实例模式，一个栈中同一个Activity只存在唯一一个实例，无论是否在栈顶，只要存在实例，都不会重新创建，和 singleTop 一样会重新调用 onNewIntent 方法。需要注意的是：如果一个Activity被设置为singleTask模式，那么当栈内已经存在该Activity实例时，再启动该Activity，会让该Activity实例之上的Activity被出栈。举个例子：有四个Activity 分别是 A、B、C和D,A是singleTask模式，当先执行A->B->C->D时，A在栈内已存在实例，此时再调用D->A启动A时，会让A实例之上的B、C、D都出栈。一般项目的MainActivity都设置为此模式，方便放回首页和清空中间Activity。
      ~ 使用场景：最常见的应用场景就是保持我们应用开启后仅仅有一个Activity的实例。最典型的样例就是应用中展示的主页（Home页）。假设用户在主页跳转到其他页面，运行多次操作后想返回到主页，假设不使用SingleTask模式，在点击返回的过程中会多次看到主页，这明显就是设计不合理了。
    ~ 单例模式（singleInstance）：
      ~ 这是一种加强的singleTask模式，它除了具有singleTask模式的所有特性外，还加强了一点，那就是此种模式的Activity只能单独地位于一个任务栈中，不同的应用去打开这个activity 共享公用的同一个activity。他会运行在自己单独，独立的任务栈里面，并且任务栈里面只有他一个实例存在。应用场景：呼叫来电界面。这种模式的使用情况比较罕见，在Launcher中可能使用。或者你确定你需要使Activity只有一个实例。
      ~ 使用场景：很常见的是，电话拨号盘页面，通过自己的应用或者其他应用打开拨打电话页面 ，只要系统的栈中存在该实例，那么就会直接调用。
  · 启动模式的2种设置方式：
    ~ 在AndroidMainifest设置
      ~ 栗子：
        <activity 
            android:name="com.demo.Main4Activity"
            //通过android:launchMode属性设置
            android:launchMode="singleTask"/>
    ~ 通过Intent设置标志位
      ~ 栗子：
        Intent inten = new Intent (ActivityA.this,ActivityB.class);
        //通过Intent的Flag设置
        intent,addFlags(Intent,FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent); 
      ~ 标记位属性及含义：
        ~ FLAG_ACTIVITY_SINGLE_TOP：指定启动模式为栈顶复用模式（SingleTop）
        ~ FLAG_ACTIVITY_NEW_TASK：指定启动模式为栈内复用模式（SingleTask）
        ~ FLAG_ACTIVITY_CLEAR_TOP：所有位于其上层的Activity都要移除，SingleTask模式默认具有此标记效果
        ~ FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS：具有该标记的Activity不会出现在历史Activity的列表中，即无法通过历史列表回到该Activity上
  · 通用的页面跳转方法有两种：
    ~ Intent
      ~ Intent适合Activity与Activity之间的跳转，按返回键可以直接返回前一页面
      ~ 缺点：需要到Manifest注册Activity
    ~ setContentView
      ~ 适合同一Activity里的不同View之间跳转
      ~ 缺点：按返回键不会返回到前一页面，需要自己添加按键监听代码来实现
  · savedInstanceState：
    ~ 简介：onCreate方法的参数是一个Bundle类型的参数。Bundle类型的数据与Map类型的数据相似，都是以key-value的形式存储数据的
    ~ onsaveInstanceState：从字面上看savedInstanceState，是保存实例状态的。实际上，savedInstanceState也就是保存Activity的状态的。那么，savedInstanceState中的状态数据是从何处而来的呢？就是通过onsaveInstanceState。
      onsaveInstanceState方法是用来保存Activity的状态的。当一个Activity在生命周期结束前，会调用该方法保存状态。这个方法有一个参数名称与onCreate方法参数名称相同。如下所示：
        public void onSaveInstanceState(Bundle savedInstanceState){
          super.onSaveInstanceState(savedInsanceState);
        }
      在实际应用中，当一个Activity结束前，如果需要保存状态，就在onsaveInstanceState中，将状态数据以key-value的形式放入到savedInstanceState中。这样，当一个Activity被创建时，就能从onCreate的参数savedInsanceState中获得状态数据。
    ~ 使用场景：一般的程序开发中，很少用到savedInstanceStat参数。当程序快被杀死之前，也就是生命周期onpuse、onstop的时候当前界面处于不可见状态会执行public void onSaveInstanceState(Bundle savedInstanceState) 这个方法进行当前数据保存，以防止程序因为内存不足被杀死丢失数据。若当程序因为内存不足被被系统gc时再次启动这个程序 则会走onRestoreInstanceState、onCreate这两个方法读取之前被保存的数据，这个应用场景一般是玩游戏或者读书，到某个进度的时候来个电话或者操作其他应用使这个程序切换到后台内存不足gc掉了可以保存当前进度
    ~ 文档：
      ~ 《Android——Bundle savedInstanceState的作用》：https://www.cnblogs.com/Chenshuai7/p/5290918.html
      ~ 《android savedInstanceState 方法简单讲解》：https://blog.csdn.net/bxllove/article/details/89469070
  · Activity常用方法：
    ~ LayoutInflater()：
      ~ 简介：它的作用类似于findViewById()。不同点是LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化；而findViewById()是找xml布局文件下的具体widget控件(如Button、TextView等)。
        对于一个没有被载入或者想要动态载入的界面，都需要使用LayoutInflater.inflate()来载入；
        对于一个已经载入的界面，就可以使用Activity.findViewById()方法来获得其中的界面元素
      ~ 获得LayoutInflater实例的3种方式：
        1. LayoutInflater inflater = getLayoutInflater();//调用Activity的getLayoutInflater() 
        2. LayoutInflater inflater = LayoutInflater.from(context);  
        3. LayoutInflater inflater =  (LayoutInflater)context.getSystemService
    ~ findViewById()：根据id找到xml中的对象，类似js根据id查找html中的dom
    ~ finish()用于结束一个Activity的生命周期
  · 不同activity之间传递数据（内存级别，非io级别）:
    ~ 通过Intent传递数据
      Activity1:
      Intent intent=new intent(Activity1.this,Activity2.clas);
      Bundle data=new data();
      data.putString("name",name);
      intent.putExtras(data);
      StartActivity(intent);
      Activity2:
      Intent intent=getIntent();
      Bundle data=intent.getExtras();
      String name=data.getString("name);
    ~ 通过静态变量（static）传递数据：
      将需要共享的变量声明为public static类型
    ~ 通过剪贴板（Clipboard）传递数据
    ~ 通过全局对象传递数据
      新建Application的子类,在该类中声明共享的变量
      class MyApp extends Application {         
          private String myState;         
          public String getState() {         
          return myState;         
          }         
          public void setState(String s) {         
          myState = s;         
          }         
      }     
  · 文档：
    ~ 《Carson带你学Android：关于Activity的知识都在这里了》：https://www.jianshu.com/p/32938446e4e0




* Fragment：
  · 定义：Activity界面中的一部分，可理解为模块化的Activity
  · 特点：
    ~ Fragment不能独立存在，必须嵌入到Activity中
    ~ Fragment具有自己的生命周期，接收它自己的事件，并可以在Activity运行时被添加或删除
    ~ Fragment的生命周期直接受所在的Activity的影响。如：当Activity暂停时，它拥有的所有Fragment们都暂停
  · 作用：
    支持动态、灵活的界面设计
  · 注意事项：
    ~ Fragment从 Android 3.0后引入
    ~ 在低版本Android 3.0前使用 Fragment，需要采用android-support-v4.jar兼容包
  · Fragment生命周期解析：
    ~ 当一个fragment被创建的时候：
      onAttach() → onCreate() → onCreateView() → onActivityCreated()
    ~ 当这个fragment对用户可见的时候（可以理解为从创建到显示或切换），它会经历以下状态。
      onStart() → onResume()
    ~ 当这个fragment进入“后台模式”的时候，它会经历以下状态。
      onPause() → onStop()
    ~ 当这个fragment被销毁了（或者持有它的activity被销毁了）：
      onPause() → onStop() → onDestroyView() → onDestroy() → onDetach()
    ~ 就像Activity一样，在以下的状态中，可以使用Bundle对象保存一个fragment的对象。
      onCreate() → onCreateView() → onActivityCreated()
    ~ 屏幕灭掉
      onPause() → onSaveInstanceState() → onStop()
    ~ 屏幕解锁
      onStart() → onResume()
    ~ 切换到其他Fragment
      onPause() → onStop() → onDestroyView()
    ~ 切换回本身的Fragment
      onCreateView() → onActivityCreated() → onStart() → onResume()
    ~ 回到桌面
      onPause() → onSaveInstanceState() → onStop()
    ~ 回到应用
      onStart() → onResume()
    ~ 退出应用
      onPause() → onStop() → onDestroyView() → onDestroy() → onDetach()
  · 将Fragment添加到Activity中一般有2种方法:
    ~ 在Activity的layout.xml布局文件中静态添加
    ~ 在Activity的.java文件中动态添加
  · 文档：
    ~ 《Carson带你学Android：Fragment最全面介绍 & 使用方法解析》：https://www.jianshu.com/p/2bf21cefb763


* BroadcastReceiver：
  · 广播是一个全局的监听器，属于Android四大组件之一
  · Android广播分为两个角色：广播发送者和广播接收者
  · 作用
    监听/接收应用App发出的广播消息，并做出响应
  · 应用场景
    ~ Android不同组件间的通信（含：应用内/不同应用之间）
    ~ 多线程通信
    ~ 与Android系统在特定情况下的通信，如：电话呼入时、网络可用时
  · 实现原理：
    Android中的广播使用了设计模式中的观察者模式：基于消息的发布/订阅事件模型。因此，Android将广播的发送者和接收者解耦，使得系统方便集成，更易扩展
  · 使用流程：
    ~ 自定义广播接收者BroadcastReceiver
      ~ 例子：
        // mBroadcastReceiver.java
        // 继承BroadcastReceivre基类
        public class mBroadcastReceiver extends BroadcastReceiver {
          // 复写onReceive()方法
          // 接收到广播后，则自动调用该方法
          @Override
          public void onReceive(Context context, Intent intent) {
            //写入接收广播后的操作
          }
        }
    ~ 广播接收者注册
      ~ 注册的方式分为两种：静态注册和动态注册
      ~ 静态注册：
        ~ 特点：当此 App首次启动时，系统会自动实例化mBroadcastReceiver类，并注册到系统中
        ~ 注册方式：在AndroidManifest.xml里通过<receive>标签声明
        ~ 属性说明：
          <receiver 
              android:enabled=["true" | "false"]
              //此broadcastReceiver能否接收其他App的发出的广播
              //默认值是由receiver中有无intent-filter决定的：如果有intent-filter，默认值为true，否则为false
              android:exported=["true" | "false"]
              android:icon="drawable resource"
              android:label="string resource"
              //继承BroadcastReceiver子类的类名
              android:name=".mBroadcastReceiver"
              //具有相应权限的广播发送者发送的广播才能被此BroadcastReceiver所接收；
              android:permission="string"
              //BroadcastReceiver运行所处的进程
              //默认为app的进程，可以指定独立的进程
              //注：Android四大基本组件都可以通过此属性指定自己的独立进程
              android:process="string" >
                //用于指定此广播接收器将接收的广播类型
                //本示例中给出的是用于接收网络状态改变时发出的广播
                <intent-filter>
                  <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
                </intent-filter>
          </receiver>
        ~ 注册示例
          <receiver 
              //此广播接收者类是mBroadcastReceiver
              android:name=".mBroadcastReceiver" >
              //用于接收网络状态改变时发出的广播
              <intent-filter>
                  <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
              </intent-filter>
          </receiver>
      ~ 动态注册:
        ~ 注册方式：在代码中调用Context.registerReceiver()方法
        ~ 具体代码如下：
          // 选择在Activity生命周期方法中的onResume()中注册
          @Override
          protected void onResume(){
              super.onResume();
              // 1. 实例化BroadcastReceiver子类 &  IntentFilter
              mBroadcastReceiver mBroadcastReceiver = new mBroadcastReceiver();
              IntentFilter intentFilter = new IntentFilter();
              // 2. 设置接收广播的类型
              intentFilter.addAction(android.net.conn.CONNECTIVITY_CHANGE);
              // 3. 动态注册：调用Context的registerReceiver()方法
              registerReceiver(mBroadcastReceiver, intentFilter);
          }
          // 注册广播后，要在相应位置记得销毁广播
          // 即在onPause() 中unregisterReceiver(mBroadcastReceiver)
          // 当此Activity实例化时，会动态将MyBroadcastReceiver注册到系统中
          // 当此Activity销毁时，动态注册的MyBroadcastReceiver将不再接收到相应的广播。
          @Override
          protected void onPause() {
              super.onPause();
                //销毁在onResume()方法中的广播
              unregisterReceiver(mBroadcastReceiver);
          }
        ~ 注意：
          ~ 动态广播最好在Activity 的 onResume()注册、onPause()注销。原因：对于动态广播，有注册就必然得有注销，否则会导致内存泄露
      ~ 两种注册方式区别：
        ~ 静态注册：常驻内存，不受任何组件的生命周期影响；缺点是耗电、占内存；常用于需要时刻监听广播
        ~ 动态注册：非常驻内存，跟随组件生命周期变化，非常灵活；常用于特定时刻监听广播
    ~ 广播发送者向AMS发送广播
      ~ 广播的类型主要分为5类：
        ~ 普通广播（Normal Broadcast）
          ~ 开发者自身定义 intent的广播（最常用）。发送广播使用如下：
            Intent intent = new Intent();
            //对应BroadcastReceiver中intentFilter的action
            intent.setAction(BROADCAST_ACTION);
            //发送广播
            sendBroadcast(intent);
          ~ 若被注册了的广播接收者中注册时intentFilter的action与上述匹配，则会接收此广播（即进行回调onReceive()）。如下mBroadcastReceiver则会接收上述广播
            <receiver 
                //此广播接收者类是mBroadcastReceiver
                android:name=".mBroadcastReceiver" >
                //用于接收网络状态改变时发出的广播
                <intent-filter>
                    <action android:name="BROADCAST_ACTION" />
                </intent-filter>
            </receiver>
          ~ 若发送广播有相应权限，那么广播接收者也需要相应权限
        ~ 系统广播（System Broadcast）
          ~ Android中内置了多个系统广播：只要涉及到手机的基本操作（如开机、网络状态变化、拍照等等），都会发出相应的广播
          ~ 每个广播都有特定的Intent - Filter（包括具体的action），Android系统广播action如下：
            ~ 系统操作：action
            ~ 监听网络变化：android.net.conn.CONNECTIVITY_CHANGE
            ~ 关闭或打开飞行模式：Intent.ACTION_AIRPLANE_MODE_CHANGED
            ~ 充电时或电量发生变化：Intent.ACTION_BATTERY_CHANGED
            ~ 电池电量低：Intent.ACTION_BATTERY_LOW
            ~ 电池电量充足（即从电量低变化到饱满时会发出广播：Intent.ACTION_BATTERY_OKAY
            ~ 系统启动完成后(仅广播一次)：Intent.ACTION_BOOT_COMPLETED
            ~ 按下照相时的拍照按键(硬件按键)时：Intent.ACTION_CAMERA_BUTTON
            ~ 屏幕锁屏：Intent.ACTION_CLOSE_SYSTEM_DIALOGS
            ~ 设备当前设置被改变时(界面语言、设备方向等)：Intent.ACTION_CONFIGURATION_CHANGED
            ~ 插入耳机时：Intent.ACTION_HEADSET_PLUG
            ~ 未正确移除SD卡但已取出来时(正确移除方法:设置--SD卡和设备内存--卸载SD卡)：Intent.ACTION_MEDIA_BAD_REMOVAL
            ~ 插入外部储存装置（如SD卡）：Intent.ACTION_MEDIA_CHECKING
            ~ 成功安装APK：Intent.ACTION_PACKAGE_ADDED
            ~ 成功删除APK：Intent.ACTION_PACKAGE_REMOVED
            ~ 重启设备：Intent.ACTION_REBOOT
            ~ 屏幕被关闭：Intent.ACTION_SCREEN_OFF
            ~ 屏幕被打开：Intent.ACTION_SCREEN_ON
            ~ 关闭系统时：Intent.ACTION_SHUTDOWN
            ~ 重启设备：Intent.ACTION_REBOOT
          ~ 注：当使用系统广播时，只需要在注册广播接收者时定义相关的action即可，并不需要手动发送广播，当系统有相关操作时会自动进行系统广播
        ~ 有序广播（Ordered Broadcast）
          ~ 定义
            发送出去的广播被广播接收者按照先后顺序接收，有序是针对广播接收者而言的
          ~ 广播接受者接收广播的顺序规则（同时面向静态和动态注册的广播接受者）
            ~ 按照Priority属性值从大-小排序；
            ~ Priority属性相同者，动态注册的广播优先；
          ~ 特点
            ~ 接收广播按顺序接收
            ~ 先接收的广播接收者可以对广播进行截断，即后接收的广播接收者不再接收到此广播；
            ~ 先接收的广播接收者可以对广播进行修改，那么后接收的广播接收者将接收到被修改后的广播
          ~ 具体使用
            有序广播的使用过程与普通广播非常类似，差异仅在于广播的发送方式：
            sendOrderedBroadcast(intent);
        ~ 粘性广播（Sticky Broadcast）
          由于在Android5.0 & API 21中已经失效，所以不建议使用，在这里也不作过多的总结。
        ~ App应用内广播（Local Broadcast）
          ~ 背景：Android中的广播可以跨App直接通信（exported对于有intent-filter情况下默认值为true）
          ~ 冲突可能出现的问题：
            ~ 其他App针对性发出与当前App intent-filter相匹配的广播，由此导致当前App不断接收广播并处理；
            ~ 其他App注册与当前App一致的intent-filter用于接收广播，获取广播具体信息；即会出现安全性 & 效率性的问题。
          ~ 解决方案
            ~ 使用App应用内广播（Local Broadcast）
              ~ App应用内广播可理解为一种局部广播，广播的发送者和接收者都同属于一个App。
              ~ 相比于全局广播（普通广播），App应用内广播优势体现在：安全性高 & 效率高
          ~ 具体使用1 - 将全局广播设置成局部广播
            1、注册广播时将exported属性设置为false，使得非本App内部发出的此广播不被接收；
            2、在广播发送和接收时，增设相应权限permission，用于权限验证；
            3、发送广播时指定该广播接收器所在的包名，此广播将只会发送到此包中的App内与之相匹配的有效广播接收器中。通过intent.setPackage(packageName)指定报名
          ~ 具体使用2 - 使用封装好的LocalBroadcastManager类
            使用方式上与全局广播几乎相同，只是注册/取消注册广播接收器和发送广播时将参数的context变成了LocalBroadcastManager的单一实例。注：对于LocalBroadcastManager方式发送的应用内广播，只能通过LocalBroadcastManager动态注册，不能静态注册
            //注册应用内广播接收器
            //步骤1：实例化BroadcastReceiver子类 & IntentFilter mBroadcastReceiver 
            mBroadcastReceiver = new mBroadcastReceiver(); 
            IntentFilter intentFilter = new IntentFilter(); 
            //步骤2：实例化LocalBroadcastManager的实例
            localBroadcastManager = LocalBroadcastManager.getInstance(this);
            //步骤3：设置接收广播的类型 
            intentFilter.addAction(android.net.conn.CONNECTIVITY_CHANGE);
            //步骤4：调用LocalBroadcastManager单一实例的registerReceiver()方法进行动态注册 
            localBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
            //取消注册应用内广播接收器
            localBroadcastManager.unregisterReceiver(mBroadcastReceiver);
            //发送应用内广播
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            localBroadcastManager.sendBroadcast(intent);
  · 注意事项：对于不同注册方式的广播接收器回调OnReceive（Context context，Intent intent）中的context返回值是不一样的：
    ~ 对于静态注册（全局+应用内广播），回调onReceive(context, intent)中的context返回值是：ReceiverRestrictedContext；
    ~ 对于全局广播的动态注册，回调onReceive(context, intent)中的context返回值是：Activity Context；
    ~ 对于应用内广播的动态注册（LocalBroadcastManager方式），回调onReceive(context, intent)中的context返回值是：Application Context。
    ~ 对于应用内广播的动态注册（非LocalBroadcastManager方式），回调onReceive(context, intent)中的context返回值是：Activity Context；


* Service：
  · 简介
    ~ 定义：服务，是Android四大组件之一， 属于计算型组件
    ~ 作用：提供需在后台长期运行的服务，如：复杂计算、音乐播放、下载等
    ~ 特点：无用户界面、在后台运行、生命周期长
  · 生命周期
    ~ 在Service的生命周期里，常用的有：
      ~ 4个手动调用的方法
        ~ startService()：启动服务
        ~ stopService()：关闭服务
        ~ bindService()：绑定服务
        ~ unbindService()：解绑服务
      ~ 5个自动调用的方法
        ~ onCreat()：创建服务
        ~ onStartCommand()：开始服务
        ~ onDestroy()：销毁服务
        ~ onBind()：绑定服务
        ~ onUnbind()：解绑服务
    ~ 《Carson带你学Android：Service生命周期完全解析》：https://www.jianshu.com/p/8d0cde35eb10
  · Service类型：
    ~ Android中的服务和windows中的服务是类似的东西，服务一般没有用户操作界面，它运行于系统中不容易被用户发觉，可以使用它开发如监控之类的程序。服务不能自己运行，需要通过调用Context.startService()或Context.bindService()方法启动服务。这两个方法都可以启动Service，但是它们的使用场合有所不同。使用startService()方法启用服务，调用者与服务之间没有关连，即使调用者退出了，服务仍然运行。使用bindService()方法启用服务，调用者与服务绑定在了一起，调用者一旦退出，服务也就终止，大有“不求同时生，必须同时死”的特点。
    ~ 运行地点：
      ~ 本地服务
        ~ 特点：
          ~ 运行在主线程
          ~ 主进程被终止后，服务也会被终止
        ~ 优点：
          ~ 节约资源
          ~ 通信方便：由于在同一进程，所以不需要IPC或者AIDL
        ~ 缺点：
          限制性大（主进程被终止后，服务也会被终止）
        ~ 使用场景：
          需依附某个进程的服务，例如音乐播放
      ~ 远程服务
        ~ 特点：
          ~ 运行在独立线程
          ~ 服务常驻在后台，不受其他Acticity影响
        ~ 优点：
          ~ 灵活（服务常驻在后台，不受其他Acticity影响）
        ~ 缺点：
          ~ 消耗资源：单独进程
          ~ 使用AIDL进行IPC复杂
        ~ 使用场景：
          系统级别服务
    ~ 运行类型
      ~ 前台服务
        ~ 特点：在通知栏显示通知（用户可看到）
        ~ 场景：服务使用时需要让用户知道，并且进行相关操作，如音乐播放服务（服务被终止时，通知栏的通知也会消失）
      ~ 后台服务
        ~ 特点：处于后台的服务（用户可看到）
        ~ 场景：服务使用时不需要让用户知道，如天气更新、日期同步（服务被终止时，用户无法知道）
    ~ 功能类型
      ~ 可通信服务：
        ~ 用bindService()启动的可通信服务：
          ~ 特点：调用者退出后，随着调用者销毁
          ~ 场景：服务需与Activity或Service通信，需控制服务开始时刻
        ~ 特点：
          ~ 调用者退出后，随着调用者销毁
          ~ 调用者退出了，Srevice就会调用onUnbind-->onDestroyed，所谓绑定在一起就共存亡了。
        ~ 场景：服务需与Activity或Service通信，不需控制服务开始时刻（服务一开始便运行）
      ~ 不可通信服务
        ~ 特点：
          ~ 用startService()启动
          ~ 调用者退出后Service仍然存在
          ~ 如果是调用者直接退出而没有调用stopService的话，Service会一直在后台运行。下次调用者再起来仍然可以stopService。
        ~ 场景：服务不需要与Activity或Service通信
  · Service与Thread的区别：
    ~ Service与Thread无任何关系
    ~ 之所以有不少人会把它们联系起来，主要因为Service的后台概念
      后台：后台任务运行完全不依赖UI，即使Activity被销毁 / 程序被关闭，只要进程还在，后台任务就可继续运行
    ~ 2者异同：
      ~ 相同点：都是执行异步操作
      ~ 不同点：
        ~ Service：
          ~ 运行在主线程（不能处理耗时操作，否则会出现ANR）
          ~ 运行范围：进程
        ~ Thread
          ~ 运行在工作线程
            ~ 完全不依赖UI/Activity，主要进程还在，Service就可以继续运行
            ~ 所有Activity都可与Service关联，获得Binder实例或操作其中的方法
            ~ 若要处理耗时操作，则在Service里创建Thread子线程执行
          ~ 运行范围：Activity
            ~ 即依赖于某个Activity
            ~ 在一个Acitvity中创建的子线程，另外一个Activity无法对其进行操作
            ~ Acivity很难控制Thread
            ~ 当Activity被销毁后，就无法再获得之前创建的子线程实例
  · Service和IntentService的区别：https://www.jianshu.com/p/8a3c44a9173a


* ContentProvider：
  · 定义：即内容提供者，是Android四大组件之一
  · 背景：当应用继承ContentProvider类，并重写该类用于提供数据和存储数据的方法，就可以向其他应用共享其数据。ContentProvider为存储和获取数据提供了统一的接口。虽然使用其他方法也可以对外共享数据，但数据访问方式会因数据存储的方式而不同，如采用文件方式对外共享数据，需要进行文件操作读写数据；采用sharedpreferences共享数据，需要使用sharedpreferences API读写数据。而使用ContentProvider共享数据的好处是统一了数据访问方式。
  · 作用：进程间进行数据交互或者共享，即跨进程通信
  · 原理：
    ~ ContentProvider的底层原理 = Android中的Binder机制
    ~ https://www.jianshu.com/p/4ee3fd07da14
  · 具体使用：
    ~ 统一资源标识符（URI）
      ~ 定义：Uniform Resource Identifier，即统一资源标识符
      ~ 作用：唯一标识ContentProvider或其中的数据
              外界进程通过 URI 找到对应的ContentProvider & 其中的数据，再进行数据操作
      ~ 具体使用：URI分为系统预置和自定义，分别对应系统内置的数据（如通讯录、日程表等等）和自定义数据库
      ~ 系统预置URI：
        ~ ContactsContract.Contacts.CONTENT_URI 管理联系人的Uri
        ~ ContactsContract.CommonDataKinds.Phone.CONTENT_URI 管理联系人的电话的Uri
        ~ ContactsContract.CommonDataKinds.Email.CONTENT_URI 管理联系人的Email的Uri
        ~ ...
      ~ 自定义URI：
        ~ URI的组成：
          自定义URI = content://com.carson.provider/User/1
          ~ 主题(Schema)：Content Provider的URI前缀（Android规定），指的上面的content://
          ~ 授权信息(Authority)：Content Provider的唯一标识符，指的上面的com.carson.provider
          ~ 表面（Path）：Content Provider指向数据库中的某个表面，指的上面的User
          ~ 记录（ID）：表中的某个记录（若无指定，则返回全部记录），指的上面的1
        ~ 设置URI
          Uri uri = Uri.parse("content://com.carson.provider/User/1") 
          // 上述URI指向的资源是：名为 `com.carson.provider`的`ContentProvider` 中表名 为`User` 中的 `id`为1的数据
          // 特别注意：URI模式存在匹配通配符* & ＃
          // *：匹配任意长度的任何有效字符的字符串
          // 以下的URI 表示 匹配provider的任何内容
          content://com.example.app.provider/* 
          // ＃：匹配任意长度的数字字符的字符串
          // 以下的URI 表示 匹配provider中的table表的所有行
          content://com.example.app.provider/table/# 
    ~ MIME数据类型
      ~ 作用：指定某个扩展名的文件用某种应用程序来打开。如指定.html文件采用text应用程序打开、指定.pdf文件采用flash应用程序打开
      ~ 具体使用：
        ~ ContentProvider根据 URI 返回MIME类型
          ContentProvider.geType(uri) ；
        ~ MIME类型组成
          每种MIME类型 由2部分组成 = 类型 + 子类型
          text / html
          text/css
          text/xml
          application/pdf
        ~ MIME类型的2种形式
          ~ 形式1：单条记录  
            vnd.android.cursor.item/自定义
          ~ 形式2：多条记录（集合）
            vnd.android.cursor.dir/自定义 
            // 注：
            // 1. vnd：表示父类型和子类型具有非标准的、特定的形式。
            // 2. 父类型已固定好（即不能更改），只能区别是单条还是多条记录
            // 3. 子类型可自定义
        ~ 实例说明
          <-- 单条记录 -->
          // 单个记录的MIME类型
          vnd.android.cursor.item/vnd.yourcompanyname.contenttype 
          // 若一个Uri如下
          content://com.example.transportationprovider/trains/122   
          // 则ContentProvider会通过ContentProvider.geType(url)返回以下MIME类型
          vnd.android.cursor.item/vnd.example.rail
          <-- 多条记录 -->
          // 多个记录的MIME类型
          vnd.android.cursor.dir/vnd.yourcompanyname.contenttype 
          // 若一个Uri如下
          content://com.example.transportationprovider/trains 
          // 则ContentProvider会通过ContentProvider.geType(url)返回以下MIME类型
          vnd.android.cursor.dir/vnd.example.rail
    ~ ContentProvider类
    ~ ContentResolver类
    ~ ContentUris类
    ~ UriMatcher类
    ~ ContentObserver类
  · 文档：
    ~ 《Carson带你学Android：关于ContentProvider的知识都在这里了！》：https://www.jianshu.com/p/ea8bc4aaf057


* Intent：
  · 定义：描述的是应用的动作，其对应的数据
  · 作用：
    ~ 指定当前组件要完成的动作
    ~ 在Android不同组件间传递数据：Activity、Service、BroadcastReceiver之间的通信载体 = Intent
  · 指定当前组件要完成的动作：
    ~ 显式意图
      ~ 特点：明确指定需启动的组件名，即显式Intent不需解析Intent则可直接启动目标组件
      ~ 具体使用：明确指定组件名的方式，调用Intent的构造方法、Intent.setComponent()、Intent.setClass()
      ~ 栗子：
        // 使FirstActivity启动SecondActivity（通过按钮）
        mybutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              // 1. 实例化显式Intent & 通过构造函数接收2个参数
              // 参数1 = Context：启动活动的上下文，一般为当前Activity
              // 参数2 = Class：是指定要启动的目标活动
              Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
              // 2. 通过Activity类的startActivity()执行该意图操作（接收一个Intent对象）
              // 将构建好的Intent对象传入该方法就可启动目标Activity
              startActivity (intent);
            }
        });
    ~ 隐式意图
      ~ 特点：无明确指定需启动的组件名，但指定了需启动组件需满足的条件，即没有明确指定组件名的Intent为隐式意图。Android系统会根据隐式意图中设置的动作(action)、类别(category)、数据（URI和数据类型）找到最合适的组件来处理这个意图。
      ~ 具体使用：通过AndroidManifest.xml文件下的<Activity>标签下的<intent -filter>声明需匹配的条件。一个<Activity>标签下可以有多组<intent -filter>，只需匹配其中1组即可
      ~ 总体匹配规则：
        ~ 只有当动作(action)、类别(category)、数据（URI和数据类型）同事匹配时，才能成功启动Activity
        ~ 1个Activity可以有多个intent-filter，一个Intent只要能匹配任何一组intent-filter即可成功启动对应Acticity
      ~ 匹配过程：
        1、加载所有的Intent Filter列表
        2、去掉action匹配失败的Intent Filter
        3、去掉url匹配失败的Intent Filter
        4、去掉category匹配失败的Intent Filter
        5、判断剩下的Intent Filter数目是否为0；若为0，则查找失败，并返回异常；若>0，就按优先级排序，返回最高优先级的Intent Filter
      ~ 栗子：
        // 使FirstActivity启动SecondActivity（通过按钮）
        mybutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              // 1. 实例化1个隐式Intent对象，并指定action参数
              Intent intent = new Intent("android.intent.action.ALL_APPS");
              // 2. 调用Intent中的addCategory()来添加一个category
              // 注：每个Intent中只能指定1个action，但却能指定多个category
              intent.addCategory("com.example.intent_test.MY_CATEGORY");
              startActivity (intent);
            }
        });
        // 为使SecondActivity能继续响应该Intent
        // 我们需在AndroidManifest.xml文件下的<Activity>标签下配置<intent -filter>的内容
        <intent-filter>
            <action android:name="android.intent.action.ALL_APPS"/>
            <category android:name="android.intent.category.DEFAULT"/>        
            <category android:name="com.example.intent_test.MY_CATEGORY"/>
        </intent-filter>
  · 不同组件间传递数据：
    ~ 使用方法：通过putExtra()和Bundle方式传递
    ~ 可传递的数据类型：
      ~ 8种基本数据类型（boolean byte char short int long float double）、String
      ~ Intent、Bundle
      ~ Serializable对象、Parcelable及其对应数组、CharSequence 类型
      ~ ArrayList，泛型参数类型为：<Integer>、<? Extends Parcelable>、<Charsequence>、<String>
    ~ 具体使用：在当前Activity把要传递的数据暂存在Intent中、在新启动的Activity中取出Intent中的数据
      ~ 方法1：putExtra()
        // 目的：将FristActivity中的一个字符串传递到SecondActivity中，并在SecondActivity中将Intent对象中的数据（FristActivity传递过来的数据）取出
        // 1. 数据传递
        // a. 创建Intent对象（显示Intent）
        Intent intent = new Intent(FirstActivity.this,SecondActivity.class);     
        // b. 通过putExtra()方法传递一个字符串到SecondActivity；
        // putExtra()方法接收两个参数：第一个是键，第二个是值（代表真正要传递的数据）
        intent.putExtra("data","I come from FirstActivity");
        // c. 启动Activity
        startActivity(intent);
        // 2. 数据取出（在被启动的Activity中）
        // a. 获取用于启动SecondActivit的Intent
        Intent intent = getIntent();
        // b. 调用getStringExtra()，传入相应的键名，就可得到传来的数据
        // 注意数据类型 与 传入时保持一致
        String data = intent.getStringExtra("data");
      ~ 方法2：Bundle
        // 1. 数据传递
        // a. 创建Intent对象（显示Intent）
        Intent intent = new Intent(FirstActivity.this,SecondActivity.class);     
        // b. 创建bundle对象
        Bundle bundle = new Bundle();
        // c. 放入数据到Bundle
        bundle.putString("name", "carson");
        bundle.putInt("age", 28);
        // d. 将Bundle放入到Intent中
        intent.putExtras(bundle);
        // e. 启动Activity
        startActivity(intent);
        // 2. 数据取出（在被启动的Activity中）
        // a. 获取用于启动SecondActivit的Intent
        Intent intent = getIntent();
        // b. 通过Intent获取bundle
        Bundle bundle = intent.getExtras();
        // c. 通过bundle获取数据传入相应的键名，就可得到传来的数据
        // 注意数据类型 与 传入时保持一致
        String nameString = bundle.getString("name");
        int age = bundle.getInt("age");
  · Bundle和putExtra()两种方式的区别:
    ~ Bundle 意为捆绑的意思，更多适用于：
      ~ 连续传递数据:
        若需实现连续传递：Activity A -> B -> C；若使用putExtra()，则需写两次intent = A->B先写一遍 + 在B中取出来 & 再把值重新写到Intent中再跳到C；若使用 Bundle，则只需取出 & 传入 Bundle对象即可
      ~ 可传递的值：
        Bundle可传递对象,putExtra()无法传递对象，而Bundle则可通过putSerializable传递对象，但传递的对象要实现Serializable接口
        // 如传递User类的对象
        public class User implements Serializable { ... }
        // 传递时
        User user = new User();
        Intent intent = new Intent(MyActivity.this,OthereActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
    ~ 而putExtra()更多使用于单次传递、传递简单数据类型的应用场景
  · Bundle：
    ~ Bundle经常与Intent一起用，在两个Activity间传递数据。个人目前的理解就是，如果Intent传递的数据只有一个，那么就直接用Intent的putExtra()方法直接放进参数即可。那如果Intent需要传递的是好几个参数，或者是一个类，那么这时候就需要用到Bundle
    ~ 传入demo：
      Bundle bundle = new Bundle();
      Intent intent=new Intent(MainActivity.this,Main2Activity.class);
      //设置数据
      String name="zhangSan";
      String num="88888";
      //把数据保存到Bundle里  
      bundle.putString("name", name);
      bundle.putString("num",num);
      //把bundle放入intent里  
      intent.putExtra("Message",bundle);
      startActivity(intent);
    ~ 解析demo：
      Intent intent=getIntent();
      Bundle bundle=intent.getExtras();
      String name=bundle.getString("name");
      String num=bundle.getString("num");
  · 常用场景示例：
    ~ 从google搜索内容 
      Intent intent = new Intent(); 
      intent.setAction(Intent.ACTION_WEB_SEARCH); 
      intent.putExtra(SearchManager.QUERY,"searchString") 
      startActivity(intent); 
    ~ 浏览网页 
      Uri uri = Uri.parse("http://www.google.com"); 
      Intent it  = new Intent(Intent.ACTION_VIEW,uri); 
      startActivity(it); 
    ~ 显示地图 
      Uri uri = Uri.parse("geo:38.899533,-77.036476"); 
      Intent it = new Intent(Intent.Action_VIEW,uri); 
      startActivity(it); 
    ~ 路径规划 
      Uri uri = Uri.parse("http://maps.google.com/maps?f=dsaddr=startLat%20startLng&daddr=endLat%20endLng&hl=en"); 
      Intent it = new Intent(Intent.ACTION_VIEW,URI); 
      startActivity(it); 
    ~ 拨打电话 
      Uri uri = Uri.parse("tel:xxxxxx"); 
      Intent it = new Intent(Intent.ACTION_DIAL, uri);   
      startActivity(it); 
    ~ 调用发短信的程序 
      Intent it = new Intent(Intent.ACTION_VIEW);    
      it.putExtra("sms_body", "The SMS text");    
      it.setType("vnd.android-dir/mms-sms");    
      startActivity(it); 
    ~ 发送短信 
      Uri uri = Uri.parse("smsto:0800000123");    
      Intent it = new Intent(Intent.ACTION_SENDTO, uri);    
      it.putExtra("sms_body", "The SMS text");    
      startActivity(it); 
      String body="this is sms demo"; 
      Intent mmsintent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", number, null)); 
      mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_BODY, body); 
      mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_COMPOSE_MODE, true); 
      mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_EXIT_ON_SENT, true); 
      startActivity(mmsintent); 
    ~ 发送彩信 
      Uri uri = Uri.parse("content://media/external/images/media/23");    
      Intent it = new Intent(Intent.ACTION_SEND);    
      it.putExtra("sms_body", "some text");    
      it.putExtra(Intent.EXTRA_STREAM, uri);    
      it.setType("image/png");    
      startActivity(it); 
      StringBuilder sb = new StringBuilder(); 
      sb.append("file://"); 
      sb.append(fd.getAbsoluteFile()); 
      Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mmsto", number, null)); 
      // Below extra datas are all optional. 
      intent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_SUBJECT, subject); 
      intent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_BODY, body); 
      intent.putExtra(Messaging.KEY_ACTION_SENDTO_CONTENT_URI, sb.toString()); 
      intent.putExtra(Messaging.KEY_ACTION_SENDTO_COMPOSE_MODE, composeMode); 
      intent.putExtra(Messaging.KEY_ACTION_SENDTO_EXIT_ON_SENT, exitOnSent); 
      startActivity(intent); 
    ~ 发送Email 
      Uri uri = Uri.parse("mailto:xxx@abc.com"); 
      Intent it = new Intent(Intent.ACTION_SENDTO, uri); 
      startActivity(it); 
      Intent it = new Intent(Intent.ACTION_SEND);    
      it.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");    
      it.putExtra(Intent.EXTRA_TEXT, "The email body text");    
      it.setType("text/plain");    
      startActivity(Intent.createChooser(it, "Choose Email Client")); 
      Intent it=new Intent(Intent.ACTION_SEND);      
      String[] tos={"me@abc.com"};      
      String[] ccs={"you@abc.com"};      
      it.putExtra(Intent.EXTRA_EMAIL, tos);      
      it.putExtra(Intent.EXTRA_CC, ccs);      
      it.putExtra(Intent.EXTRA_TEXT, "The email body text");      
      it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");      
      it.setType("message/rfc822");      
      startActivity(Intent.createChooser(it, "Choose Email Client"));    
      Intent it = new Intent(Intent.ACTION_SEND);    
      it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");    
      it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");    
      sendIntent.setType("audio/mp3");    
      startActivity(Intent.createChooser(it, "Choose Email Client")); 
    ~ 播放多媒体   
      Intent it = new Intent(Intent.ACTION_VIEW); 
      Uri uri = Uri.parse("file:///sdcard/song.mp3"); 
      it.setDataAndType(uri, "audio/mp3"); 
      startActivity(it); 
      Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");    
      Intent it = new Intent(Intent.ACTION_VIEW, uri);    
      startActivity(it); 
    ~ uninstall apk 
      Uri uri = Uri.fromParts("package", strPackageName, null);    
      Intent it = new Intent(Intent.ACTION_DELETE, uri);    
      startActivity(it); 
    ~ install apk 
      Uri installUri = Uri.fromParts("package", "xxx", null); 
      returnIt = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri); 
    ~ 打开照相机 
      <1>Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null); 
            this.sendBroadcast(i); 
      <2>long dateTaken = System.currentTimeMillis(); 
              String name = createName(dateTaken) + ".jpg"; 
              fileName = folder + name; 
              ContentValues values = new ContentValues(); 
              values.put(Images.Media.TITLE, fileName); 
              values.put("_data", fileName); 
              values.put(Images.Media.PICASA_ID, fileName); 
              values.put(Images.Media.DISPLAY_NAME, fileName); 
              values.put(Images.Media.DESCRIPTION, fileName); 
              values.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, fileName); 
              Uri photoUri = getContentResolver().insert( 
                      MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); 
              Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
              inttPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); 
              startActivityForResult(inttPhoto, 10); 
    ~ 从gallery选取图片 
        Intent i = new Intent(); 
        i.setType("image/*"); 
        i.setAction(Intent.ACTION_GET_CONTENT); 
        startActivityForResult(i, 11); 
    ~ 打开录音机 
        Intent mi = new Intent(Media.RECORD_SOUND_ACTION); 
        startActivity(mi); 
    ~ 显示应用详细列表       
      Uri uri = Uri.parse("market://details?id=app_id");         
      Intent it = new Intent(Intent.ACTION_VIEW, uri);         
      startActivity(it);         
      //where app_id is the application ID, find the ID          
      //by clicking on your application on Market home          
      //page, and notice the ID from the address bar      
      刚才找app id未果，结果发现用package name也可以 
      Uri uri = Uri.parse("market://details?id=<packagename>"); 
      这个简单多了 
    ~ 寻找应用       
      Uri uri = Uri.parse("market://search?q=pname:pkg_name");         
      Intent it = new Intent(Intent.ACTION_VIEW, uri);         
      startActivity(it); 
      //where pkg_name is the full package path for an application       
    ~ 打开联系人列表 
      <1>            
      Intent i = new Intent(); 
      i.setAction(Intent.ACTION_GET_CONTENT); 
      i.setType("vnd.android.cursor.item/phone"); 
      startActivityForResult(i, REQUEST_TEXT); 
      <2> 
      Uri uri = Uri.parse("content://contacts/people"); 
      Intent it = new Intent(Intent.ACTION_PICK, uri); 
      startActivityForResult(it, REQUEST_TEXT); 
    ~ 打开另一程序 
      Intent i = new Intent(); 
      ComponentName cn = new ComponentName("com.yellowbook.android2", 
              "com.yellowbook.android2.AndroidSearch"); 
      i.setComponent(cn); 
      i.setAction("android.intent.action.MAIN"); 
      startActivityForResult(i, RESULT_OK); 
    ~ 调用系统编辑添加联系人（高版本SDK有效）：
      Intent it = newIntent(Intent.ACTION_INSERT_OR_EDIT);
      it.setType("vnd.android.cursor.item/contact");
        //it.setType(Contacts.CONTENT_ITEM_TYPE);
        it.putExtra("name","myName");
      it.putExtra(android.provider.Contacts.Intents.Insert.COMPANY,  "organization");
      it.putExtra(android.provider.Contacts.Intents.Insert.EMAIL,"email");
        it.putExtra(android.provider.Contacts.Intents.Insert.PHONE,"homePhone");
        it.putExtra(android.provider.Contacts.Intents.Insert.SECONDARY_PHONE,
                      "mobilePhone");
        it.putExtra(  android.provider.Contacts.Intents.Insert.TERTIARY_PHONE,
                      "workPhone");
      it.putExtra(android.provider.Contacts.Intents.Insert.JOB_TITLE,"title");
        startActivity(it);

* Android中数据存储:
  · 常用的数据存储方式有5种
    ~ SharePreferences
      ~ 定义：一种数据存储方式
      ~ 本质：以键值对的形式存储在xml中
      ~ 特点：轻量级
      ~ 应用场景：轻量级存储（如 应用中的配置、参数属性）
      ~ 默认存储路径：/data/data/<PackageName>/shared_prefs
    ~ SQLite数据库
      ~ 定义：一种数据存储方式
      ~ 本质：嵌入式数据库、使用SQL语音，android为此数据库提供了SQLiteDatabase类，封装了操作数据库的API
      ~ 特点：
        ~ 存储结构型、关系型数据
        ~ 可使用sql语音
        ~ 支持事务处理
        ~ 独立、无需服务进程
      ~ 应用场景：结构性数据
      ~ 默认存储路径：/data/data/<PackageName>/databases
      ~ SQLiteOpenHelper：
        ~ 定义：一个SQLite数据库辅助操作的类
        ~ 作用：
          ~ 在Android中实现SQLite数据库操作
          ~ 即管理数据库（创建、增、修、删）和版本的控制
        ~ 原理：
          在实际开发中，为了能够更好的管理和维护数据库，会自定义1个继承自SQLiteOpenHelper类的数据库操作类，然后以该类为基础，根据业务需求实现数据库的操作方法
        ~ demo：
          public class MainActivity extends AppCompatActivity implements View.OnClickListener {
              private Button instablish;
              private Button insert;
              private Button upgrade;
              private Button modify;
              private Button delete;
              private Button query;
              private Button delete_database;
              @Override
              protected void onCreate(Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  setContentView(R.layout.activity_main);
                  //绑定按钮
                  instablish = (Button) findViewById(R.id.instablish);
                  insert = (Button) findViewById(R.id.insert);
                  upgrade = (Button) findViewById(R.id.upgrade);
                  modify = (Button) findViewById(R.id.modify);
                  delete = (Button) findViewById(R.id.delete);
                  query = (Button) findViewById(R.id.query);
                  delete_database = (Button) findViewById(R.id.delete_database);
                  //设置监听器
                  instablish.setOnClickListener(this);
                  insert.setOnClickListener(this);
                  upgrade.setOnClickListener(this);
                  modify.setOnClickListener(this);
                  delete.setOnClickListener(this);
                  query.setOnClickListener(this);
                  delete_database.setOnClickListener(this);
              }
              //设置每个按钮对数据库的操作进行控制
              @Override
              public void onClick(View v) {
                  switch (v.getId()) {
                      //点击创建数据库库
                      case R.id.instablish:
                          // 创建SQLiteOpenHelper子类对象
                          MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(this,"test_carson");
                          //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
                          SQLiteDatabase  sqliteDatabase = dbHelper.getWritableDatabase();
                          // SQLiteDatabase  sqliteDatabase = dbHelper.getReadbleDatabase();
                          break;
                      //点击更新数据
                      case R.id.upgrade:
                          // 创建SQLiteOpenHelper子类对象
                          MySQLiteOpenHelper dbHelper_upgrade = new MySQLiteOpenHelper(this,"test_carson",2);
                          // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                          SQLiteDatabase  sqliteDatabase_upgrade = dbHelper_upgrade.getWritableDatabase();
                          // SQLiteDatabase  sqliteDatabase = dbHelper.getReadbleDatabase();
                          break;
                      //点击插入数据到数据库
                      case R.id.insert:
                          System.out.println("插入数据");
                          // 创建SQLiteOpenHelper子类对象
                          ////注意，一定要传入最新的数据库版本号
                          MySQLiteOpenHelper dbHelper1 = new MySQLiteOpenHelper(this,"test_carson",2);
                          // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                          SQLiteDatabase  sqliteDatabase1 = dbHelper1.getWritableDatabase();
                          // 创建ContentValues对象
                          ContentValues values1 = new ContentValues();
                          // 向该对象中插入键值对
                          values1.put("id", 1);
                          values1.put("name", "carson");
                          // 调用insert()方法将数据插入到数据库当中
                          sqliteDatabase1.insert("user", null, values1);
                          // sqliteDatabase.execSQL("insert into user (id,name) values (1,'carson')");
                          //关闭数据库
                          sqliteDatabase1.close();
                          break;
                      //点击查询数据库
                      case R.id.query:
                          System.out.println("查询数据");
                          // 创建DatabaseHelper对象
                          MySQLiteOpenHelper dbHelper4 = new MySQLiteOpenHelper(MainActivity.this,"test_carson",2);
                          // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                          SQLiteDatabase sqliteDatabase4 = dbHelper4.getReadableDatabase();
                          // 调用SQLiteDatabase对象的query方法进行查询
                          // 返回一个Cursor对象：由数据库查询返回的结果集对象
                          Cursor cursor = sqliteDatabase4.query("user", new String[] { "id",
                                  "name" }, "id=?", new String[] { "1" }, null, null, null);
                          String id = null;
                          String name = null;
                          //将光标移动到下一行，从而判断该结果集是否还有下一条数据
                          //如果有则返回true，没有则返回false
                          while (cursor.moveToNext()) {
                              id = cursor.getString(cursor.getColumnIndex("id"));
                              name = cursor.getString(cursor.getColumnIndex("name"));
                              //输出查询结果
                              System.out.println("查询到的数据是:"+"id: "+id+"  "+"name: "+name);
                          }
                          //关闭数据库
                          sqliteDatabase4.close();
                          break;
                      //点击修改数据
                      case R.id.modify:
                          System.out.println("修改数据");
                          // 创建一个DatabaseHelper对象
                          // 将数据库的版本升级为2
                          // 传入版本号为2，大于旧版本（1），所以会调用onUpgrade()升级数据库
                          MySQLiteOpenHelper dbHelper2 = new MySQLiteOpenHelper(MainActivity.this,"test_carson", 2);
                          // 调用getWritableDatabase()得到一个可写的SQLiteDatabase对象
                          SQLiteDatabase sqliteDatabase2 = dbHelper2.getWritableDatabase();
                          // 创建一个ContentValues对象
                          ContentValues values2 = new ContentValues();
                          values2.put("name", "zhangsan");
                          // 调用update方法修改数据库
                          sqliteDatabase2.update("user", values2, "id=?", new String[]{"1"});
                          //关闭数据库
                          sqliteDatabase2.close();
                          break;
                      //点击删除数据
                      case R.id.delete:
                          System.out.println("删除数据");
                          // 创建DatabaseHelper对象
                          MySQLiteOpenHelper dbHelper3 = new MySQLiteOpenHelper(MainActivity.this,"test_carson",2);
                          // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                          SQLiteDatabase sqliteDatabase3 = dbHelper3.getWritableDatabase();
                          //删除数据
                          sqliteDatabase3.delete("user", "id=?", new String[]{"1"});
                          //关闭数据库
                          sqliteDatabase3.close();
                          break;
                      //点击删除数据库
                      case R.id.delete_database:
                          System.out.println("删除数据库");
                          MySQLiteOpenHelper dbHelper5 = new MySQLiteOpenHelper(MainActivity.this,
                                  "test_carson",2);
                          // 调用getReadableDatabase()方法创建或打开一个可以读的数据库
                          SQLiteDatabase sqliteDatabase5 = dbHelper5.getReadableDatabase();
                          //删除名为test.db数据库
                          deleteDatabase("test_carson");
                          break;
                      default:
                          break;
                  }
              }
          }
      ~ 文档：
        ~ 《Carson带你学Android：SQLlite数据库 使用手册》：https://www.jianshu.com/p/8e3f294e2828
    ~ 文件存储
      ~ 存储分类：
        ~ 内部存储：
          ~ 位于data/data/包名/*，对于设备中每一个安装的App，系统都会在该路径下自动创建与之对应的文件夹
          ~ 系统默认只在内部存储中创建cache目录，并且此时cache目录是空的
          ~ 内部存储是保存与app关联起来的位置，卸载应用时,应用的所有文件都会从内部存储中删除
          ~ Shared Preferences和SQLite数据库都是存储在内部存储空间上的
          ~ 访问内部存储的API方法（以下的打印结果是基于荣耀7的（系统版本6.0）：
            ~ Environment.getDataDirectory() = /data：获取内部存储的根路径
            ~ getFilesDir().getAbsolutePath()= /data/user/0/packname/files：获取某个应用在内部存储中的files路径
            ~ getCacheDir().getAbsolutePath()= /data/user/0/packname/cache：获取某个应用在内部存储中的cache路径
            ~ getDir(“myFile”, MODE_PRIVATE).getAbsolutePath()= /data/user/0/packname/app_myFile：获取某个应用在内部存储中的自定义路径
        ~ 外部存储：
          ~ 访问外部存储的API方法（以下的打印结果是基于荣耀7的（系统版本6.0）：
            ~ Environment.getExternalStorageDirectory().getAbsolutePath() = /storage/emulated/0：获取外部存储的根路径
            ~ Environment.getExternalStoragePublicDirectory(“”).getAbsolutePath() = /storage/emulated/0：获取外部存储的根路径
            ~ getExternalFilesDir(“”).getAbsolutePath() = /storage/emulated/0/Android/data/packname/files：获取某个应用在外部存储中的files路径，一般放一些长时间保存的数据，对应 设置->应用->应用详情里面的”清除数据“选项
            ~ getExternalCacheDir().getAbsolutePath() = /storage/emulated/0/Android/data/packname/cache：获取某个应用在外部存储中的cache路径，一般存放临时缓存数据对应 设置->应用->应用详情里面的”清除缓存“选项
        ~ 其他存储位置：
          ~ Environment.getDownloadCacheDirectory() = /cache
          ~ Environment.getRootDirectory() = /system
      ~ 既然内部存储与外部存储都有APP专属文件，那么我们该使用哪个呢？
        内部存储与外部存储都有APP专属文件，我们该用哪个呢，很显然应该用外部存储的，因为内部存储本身就比较小，而且已经存储了一些系统的文件，因此内部存储我们尽量不要去使用。但是当手机没有外部存储时，我们还是得使用内部存储，一般程序员会做判断是否有外部存储，没有再使用内部存储，代码如下：
        public static String getFilePath(Context context,String dir) {
            String directoryPath="";
            if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用 
                directoryPath =context.getExternalFilesDir(dir).getAbsolutePath();
            }else{//没外部存储就使用内部存储  
                directoryPath=context.getFilesDir()+File.separator+dir;
            }
            File file = new File(directoryPath);
            if(!file.exists()){//判断文件目录是否存在
                file.mkdirs();
                }
            return directoryPath;
        }
      ~ 文档：
        ~ 《完整文档》：https://blog.csdn.net/u010937230/article/details/73303034/
        ~ 《【android】getCacheDir()、getFilesDir()、getExternalFilesDir()、getExternalCacheDir()的作用》：https://www.cnblogs.com/yaowen/p/6344663.html
    ~ ContentProvider
      ~ 定义：一种数据存储方式
      ~ 本质：
      ~ 特点：
      ~ 应用场景：进程间数据共享、交换
      ~ 默认存储路径：通过URI对象
    ~ 网络存储
      ~ 定义：一种数据存储方式
      ~ 本质：
      ~ 特点：
      ~ 应用场景：本地不适合保存
      ~ 默认存储路径：远程服务器

    


* Android多线程：
  · Android中使用异步线程更新UI视图的几种方法：https://www.cnblogs.com/zhujiabin/p/10035523.html
    在Android中子线程是不能更新ui的，所以我们要通过其他方式来动态改变ui视图 
    ~ runOnUiThread
    ~ Handler message
    ~ Handler Runnable
    ~ AsyncTask

* Android多进程：


* Adapter：
  · 简介：Adapter是连接后端数据和前端显示的适配器接口，是数据和UI（View）之间一个重要的纽带。在常见的View(List View,Grid View)等地方都需要用到Adapter。
  · Adapter适配器继承关系：
    android.widget.Adapter
    ~ ListAdapater
      ~ WrapperListAdapter
        ~ HeaderViewListAdaper
    ~ SpinnerAdapater
    ~ BaseAdapter：BaseAdapter同时实现自上面的ListAdapater和SpinnerAdapater
      ~ ArrayAdapter<T>
      ~ SimpleAdapter
      ~ CursorAdapter
        ~ ResourceCursorAdapter
          ~ SimpleCursorAdapter

* 注解：
  · @Override：
    ~ 表示重写(当然不写也可以),不过写上有如下好处:
    1、可以当注释用,方便阅读
    2、编译器可以给你验证@Override下面的方法名是否是你父类中所有的,如果没有则报错比如你如果没写@Override而你下面的方法名又写错了,这时你的编译器是可以通过的(它以为这个方法是你的子类中自己增加的方法)
  · @NonNull:
    ~ 用于指明所修饰的参数，字段或方法的值不可以为null。
    ~ 它是JSR 305（缺陷检查框架）的注解，是告诉编译器这个域不可能为空，当代码检查有空值时会给出一个风险警告。
  · @NotNull：用来标注方法是否能传入null值，如果可以传入NUll值，则标记为nullbale
  · @Nullable：用来标注方法是否能传入null值，如果不可以传入NUll值，则标记为Nonnull

* Android Studio控制台打印日志：
  ~ 在Android API中，提供了一个日志工具类android.util.Log，通过这个类我们可以以不同的级别输出日志，Android的Log等级通常有六类，按照日志级别由低到高分别是Verbose、Debug、Info、Warning、Error、Assert
  ~ Log有个比较糟糕的地方在于只能输出字符串，功能相对比较弱鸡。我们可以采用第三方的类库完成日志输入，比如说com.orhanobut:logger
  ~ 可以通过Project Structure引入com.orhanobut:logger


* Android Studio的快捷键：见《开发工具——IntelliJ IDEA总结.txt》
  ~ 查看类的继承关系：ctrl + h

* Android Studio的调试：
  与chrome浏览器调试类似



* Q&A:
  · drawable和mipmap的区别：
    ~ 在apk安装的时候，mipmap-xxx/下的所有分辨率的图片都会保留，而drawablexxx/下的图片只有保留适配设备分辨率的图片，其余图片会丢弃掉，减少了APP安装大小。
    ~ mipmap：用来放app的启动图标
    ~ drawable：除app启动图标之外的图片
  · hdpi ldpi mdpi xhdpi xxhdpi适配：











































































