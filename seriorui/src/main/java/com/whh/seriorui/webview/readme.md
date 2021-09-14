### WebView
[https://blog.csdn.net/carson_ho/article/details/52693322]

1.WebView的状态
  webView.onResume();//激活WebView为活跃状态，能正常执行网页的响应

  //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
  //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
  webView.onPause();

  //当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
  //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
  webView.pauseTimers();
  //恢复pauseTimers状态
  webView.resumeTimers();

  //销毁Webview
  //在关闭了Activity时，如果Webview的音乐或视频，还在播放。就必须销毁Webview
  //但是注意：webview调用destory时,webview仍绑定在Activity上
  //这是由于自定义webview构建时传入了该Activity的context对象
  //因此需要先从父容器中移除webview,然后再销毁webview:
  rootLayout.removeView(webView);
  webView.destroy();

2.关于前进 / 后退网页

  Webview.canGoBack();//是否可以后退
  Webview.goBack();//后退网页
                   
  Webview.canGoForward();//是否可以前进  
  Webview.goForward();//前进网页

   //以当前的index为起始点前进或者后退到历史记录中指定的steps
   //如果steps为负数则为后退，正数则为前进
   Webview.goBackOrForward(intsteps) 

  Back键控制网页后退
  问题：在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身
  目标：点击返回后，是网页回退而不是推出浏览器
  解决方案：在当前Activity中处理并消费掉该 Back 事件

public boolean onKeyDown(int keyCode, KeyEvent event) {
if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
mWebView.goBack();
return true;
}
return super.onKeyDown(keyCode, event);
}

3.清除缓存数据
  //清除网页访问留下的缓存
  //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
  Webview.clearCache(true);

  Webview.clearHistory();//清除当前webview访问的历史记录,只会webview访问历史记录里的所有记录除了当前访问记录

  Webview.clearFormData();//仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据

4.WebSettings类:对WebView进行配置和管理
  
  WebSettings webSettings = webView.getSettings();//声明WebSettings子类

  webSettings.setJavaScriptEnabled(true);//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
  
  webSettings.setPluginsEnabled(true);//支持插件

  //设置自适应屏幕，两者合用
  webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
  webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

  //缩放操作
  webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
  webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
  webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

  //其他细节操作
  webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
  webSettings.setAllowFileAccess(true); //设置可以访问文件
  webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
  webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
  webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

5.设置WebView缓存
  当加载 html 页面时，WebView会在/data/data/包名目录下生成 database 与 cache 两个文件夹
  请求的 URL记录保存在 WebViewCache.db，而 URL的内容是保存在 WebViewCache 文件夹下
  是否启用缓存：
    //优先使用缓存:
    WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    //缓存模式如下：
    //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
    //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
    //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
    //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
	//不使用缓存: 
	WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
  结合使用（离线加载）
    if (NetStatusUtil.isConnected(getApplicationContext())) {
    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
    } else {
    webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
    }
    webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
    webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
    webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
    String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
    webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
   注意： 每个 Application 只调用一次 WebSettings.setAppCachePath()，WebSettings.setAppCacheMaxSize()

6.避免WebView内存泄露
  6.1 不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      mWebView = new WebView(getApplicationContext());
      mWebView.setLayoutParams(params);
      mLayout.addView(mWebView);
  6.2 在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空。
      @Override 
      protected void onDestroy() {
        if (mWebView != null) {
          mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
          mWebView.clearHistory();
          ((ViewGroup) mWebView.getParent()).removeView(mWebView);
          mWebView.destroy();
          mWebView = null;
        }
        super.onDestroy();
    }

7.WebView性能优化总结
  7.1 一个加载网页的过程中，native、网络、后端处理、CPU都会参与，各自都有必要的工作和依赖关系；让他们相互并行处理而不是相互阻塞才可以让网页加载更快：
  7.2 WebView初始化慢，可以在初始化同时先请求数据，让后端和网络不要闲着。
  7.3 后端处理慢，可以让服务器分trunk输出，在后端计算的同时前端也加载网络静态资源。
  7.4 脚本执行慢，就让脚本在最后运行，不阻塞页面解析。
  7.5 同时，合理的预加载、预缓存可以让加载速度的瓶颈更小。
  7.6 WebView初始化慢，就随时初始化好一个WebView待用。
  7.7 DNS和链接慢，想办法复用客户端使用的域名和链接。
  7.8 脚本执行慢，可以把框架代码拆分出来，在请求页面之前就执行好。

8.密码明文存储漏洞
  WebView默认开启密码保存功能
  开启后，在用户输入密码时，会弹出提示框：询问用户是否保存密码；
  如果选择”是”，密码会被明文保到 /data/data/com.package.name/databases/webview.db 中，这样就有被盗取密码的危险
  关闭密码保存提醒:WebSettings.setSavePassword(false) 

9.域控制不严格漏洞
  即 A 应用可以通过 B 应用导出的 Activity 让 B 应用加载一个恶意的 file 协议的 url，从而可以获取 B 应用的内部私有文件，从而带来数据泄露威胁
  // 需要使用 file 协议
  setAllowFileAccess(true);
  setAllowFileAccessFromFileURLs(false);
  setAllowUniversalAccessFromFileURLs(false); // 禁止 file 协议加载 JavaScript
  if (url.startsWith("file://") {
    setJavaScriptEnabled(false);
  } else {
  setJavaScriptEnabled(true);
  } 



