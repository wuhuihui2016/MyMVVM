###view的事件冲突和事件分发
1、单点触摸事件：MotionEvent
   ACTION_DOWN 手指初次接触到屏幕 时触发
   ACTION_MOVE 手指在屏幕上滑动时触发，会多次触发
   ACTION_UP 手指离开屏幕 时触发
   ACTION_CANCEL 事件 被上层拦截时触发
   手指落下(ACTION_DOWN) －> 移动(ACTION_MOVE) －> 离开 (ACTION_UP)

2、多点触控 ( Multitouch，也称 Multi-touch )，即同时接受屏幕上多个点的人机交互操作，多点触控是从 Android 2.0 开始引入的功能
   ACTION_DOWN 第一个 手指 初次接触到屏幕 时触发。 
   ACTION_MOVE 手指 在屏幕上滑动 时触发，会多次触发。 
   ACTION_UP 最后一个 手指 离开屏幕 时触发。 
   ACTION_POINTER_DOWN 有非主要的手指按下(即按下之前已经有手指在屏幕上)。 
   ACTION_POINTER_UP 有非主要的手指抬起(即抬起之后仍然有手指在屏幕上)。 
   以下事件类型不推荐使用,在 2.2 版本以上被标记为废弃－－－ ACTION_POINTER_1_DOWN 第 2 个手指按下，已废弃，不推荐使用。 ACTION_POINTER_2_DOWN 第 3 个手指按下，已废弃，不推荐使用。 ACTION_POINTER_3_DOWN 第 4 个手指按下，已废弃，不推荐使用。 ACTION_POINTER_1_UP 第 2 个手指抬起，已废弃，不推荐使用。 ACTION_POINTER_2_UP 第 3 个手指抬起，已废弃，不推荐使用。 ACTION_POINTER_3_UP 第 4 个手指抬起，已废弃，不推荐使用。

3、事件分发机制
1、dispatchTouchEvent() 负责事件分发。当点击事件产生后，事件首先传递给当前Activity，调用Activity的dispatchTouchEvent()方法，
返回值为false则表示View或子View消费了此事件，如果返回true，则表示没有消费事件，并调用父View的onTouchEvent方法。
2、onTouchEvent()用于处理事件，返回值决定当前控件是否消费了这个事件，也就是说在当前控件在调用父View的onTouchEvent方法完Touch事件后，
是否还允许Touch事件继续向上（父控件）传递，一但返回True，则父控件不用操心自己来处理Touch事件。返回true，则向上传递给父控件。
3、onInterceptTouchEvent() ViewGroup的一个方法，用于处理事件（类似于预处理，当然也可以不处理）并改变事件的传递方向，
也就是决定是否允许Touch事件继续向下（子控件）传递，一但返回True（代表事件在当前的viewGroup中会被处理），则向下传递之路被截断
（所有子控件将没有机会参与Touch事件），同时把事件传递给当前的控件的onTouchEvent()处理；返回false，则把事件交给子控件的onInterceptTouchEvent()。
4、当一个Touch事件(触摸事件为例)到达根节点，即Acitivty的ViewGroup时，它会依次下发，下发的过程是调用子View(ViewGroup)的dispatchTouchEvent方法实现的。
简单来说，就是ViewGroup遍历它包含着的子View，调用每个View的dispatchTouchEvent方法，而当子View为ViewGroup时，又会通过调用ViwGroup的dispatchTouchEvent
方法继续调用其内部的View的dispatchTouchEvent方法。（见图：Android/Image/事件分发机制原理图.jpg）上述例子中的消息下发顺序是这样的：①-②-⑤-⑥-⑦-③-④。dispatchTouchEvent方法只负责事件的分发，
它拥有boolean类型的返回值，当返回为true时，顺序下发会中断。
【小结】onInterceptTouchEvent()默认返回false，不做截获。返回true之后，事件流的后端控件就没有机会处理touch事件。view的onTouchEvent()返回了false，
那么该事件将被传递至其上一层次的view的onTouchEvent()处理，如果onTouchEvent()返回了true，那么后续事件将可以继续传递给该view的onTouchEvent()处理。

     View的事件分发机制？
        事件分发本质：就是对MotionEvent事件分发的过程。即当一个MotionEvent产生了以后，系统需要将这个点击事件传递到一个具体的View上。
        点击事件的传递顺序：Activity（Window） -> ViewGroup -> View
        三个主要方法：
        dispatchTouchEvent：进行事件的分发（传递）。返回值是 boolean 类型，受当前onTouchEvent和下级view的dispatchTouchEvent影响
        onInterceptTouchEvent：对事件进行拦截。该方法只在ViewGroup中有，View（不包含 ViewGroup）是没有的。一旦拦截，则执行ViewGroup的onTouchEvent，在ViewGroup中处理事件，而不接着分发给View。且只调用一次，所以后面的事件都会交给ViewGroup处理。
        onTouchEvent：进行事件处理。
     
        onTouch()、onTouchEvent()和onClick()关系？
        优先度onTouch()>onTouchEvent()>onClick()。因此onTouchListener的onTouch()方法会先触发；如果onTouch()返回false才会接着触发onTouchEvent()，同样的，内置诸如onClick()事件的实现等等都基于onTouchEvent()；如果onTouch()返回true，这些事件将不会被触发。

