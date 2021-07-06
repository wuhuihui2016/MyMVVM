## Jetpack navigation 20210624-25
一、新建 navigation 文件，文件名自定义
   详细见 src/main/res/navigation/navigation_res.xml
   在res目录右键选择new -> Android Resource File -> Resource type:Navigation
   res -> navigation/navigation_res.xml，在其中锁定需要加载的 Fragment
   TODO 疑问：其中的 <action> 在什么时候生效？
   
二、新建 navigation 文件提到的 Fragment 及对应的布局文件
   详细见 src/main/java/com/whh/mymvvm/navigation/OneFragment.java、TwoFragment.java、ThreeFragment.java

三、新建用来切换 Fragment 的 Activity 
   详细见 src/main/java/com/whh/mymvvm/activity/NavigationActivity.java
   (也可通过菜单创建 Bottom Navigation Activity ：new -> activity -> Bottom Navigation Activity)
   1、Navigation + BottomNavigationView 实现 Fragment 切换
      BottomNavigationView 定义 menu ：详细见 src/main/res/menu/bottom_nav_menu.xml
   2、给 Fragment 传参数；
   3、点击返回键处理 Fragment 的回退事件
   TODO 疑问：onSupportNavigateUp在什么时候调用，作用？
