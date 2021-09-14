###自定义View
1、自定义View的基本方法
  测量：onMeasure()决定View的大小；
  布局：onLayout()决定View在ViewGroup中的位置；
  绘制：onDraw()决定绘制这个View。
  
2、自定义控件分类
  自定义View: 只需要重写onMeasure()和onDraw()
  自定义ViewGroup: 则只需要重写onMeasure()和onLayout()

3、View 的四个构造方法
   3.1 如果View是在Java代码里面new的，则调用第一个构造函数 
   public CarsonView(Context context) { super(context); }

   3.2 如果View是在.xml里声明的，则调用第二个构造函数，自定义属性是从AttributeSet参数传进来的 
   public CarsonView(Context context, AttributeSet attrs) { super(context, attrs); } 
   
   3.3 不会自动调用，一般是在第二个构造函数里主动调用，如View有style属性时 
   public CarsonView(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }
   
   3.4 API21之后才使用，不会自动调用，一般是在第二个构造函数里主动调用，如 View 有 style 属性时 
   public CarsonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) { super(context, attrs, defStyleAttr, defStyleRes); }
   
4、View视图结构
   4.1 PhoneWindow 是Android 系统中最基本的窗口系统，继承自 Windows 类，负责管理界面显示以及事件响应，它是 Activity 与 View 系统交互的接口。
   4.2 DecorView 是 PhoneWindow 中的起始节点 View，继承于 View 类，作为整个视图容器来使用，用于设置窗口属性，它本质上是一个FrameLayout。
   4.3 ViewRoot 在 Activtiy 启动时创建，负责管理、布局、渲染窗口 UI 等等
       它的主要作用是 View 树的管理者，负责将 DecorView 和 PhoneWindow “组合”起来，而 View 树的根节点严格意义上来说只有 DecorView；
       每个 DecorView 都有一个 ViewRoot 与之关联，这种关联关系是由 WindowManager 去进行管理的。
   无论是 measure 过程、layout 过程还是 draw 过程，永远都是从 View 树的根节点开始测量或计算（即从树的顶端开始），
   一层一层、一个分支一个分支地进行（即树形递归），最终计算整个 View 树中各个 View，最终确定整个 View 树的相关属性

5、Android 坐标系：屏幕的左上角为坐标原点，向右为x轴增大方向，向下为y轴增大方向
   View 的位置由4个顶点决定的 4个顶点的位置描述分别由4个值决定：
      Top：子 View 上边界到父 view 上边界的距离，方法:getTop()
      Left：子 View 左边界到父 view 左边界的距离，方法:getLeft()
      Bottom：子 View 下边距到父 View 上边界的距离，方法:getBottom()
      Right：子 View 右边界到父 view 左边界的距离，方法:getRight()

6、LayoutParams
     布局参数，子View通过LayoutParams告诉父容器（ViewGroup）应该如何放置自己，LayoutParams 与 ViewGroup 息息相关
     事实上，每个 ViewGroup 的子类都有自己对应的 LayoutParams 类，典型的如LinearLayout.LayoutParams，
     可以看出来LayoutParams都是对应ViewGroup子类的内部类
   MeasureSpecs 的意义
     通过将 SpecMode 和 SpecSize 打包成一个 int 值可以避免过多的对象内存分配，为了方便操作，其提供了打包/解包方法；
     MeasureSpec是View中的内部类，基本都是二进制运算。由于int是32位的，用高两位表示mode，低30位表示size，MODE_SHIFT = 30的作用是移位。
     UNSPECIFIED：不对View大小做限制，系统使用
     EXACTLY：确切的大小，如：100dp
     AT_MOST：大小不可超过某数值，如：matchParent, 最大不能超过父布局

7、getMeasureWidth 与 getWidth 的区别
   getMeasureWidth：在measure()过程结束后就可以获取到对应的值，通过setMeasuredDimension()方法来进行设置的.
   getWidth：在layout()过程结束后才能获取到，通过视图右边的坐标减去左边的坐标计算出来的.
