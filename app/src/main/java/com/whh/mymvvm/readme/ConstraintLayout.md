## ConstraintLayout 约束布局 [https://www.jianshu.com/p/17ec9bd6ca8a]
1、目的：解决布局嵌套太多问题，已灵活方式定位和调整小部件
2、定位属性，必须指定上下的定位
   比如app:layout_constraintTop_toBottomOf="@+id/TextView1"，即将当前 view 约束到 TextView1 的下方
   layout_constraintBaseline_toBaselineOf //在两个 View 高度不一致时，可使得它俩内部文本对齐
   layout_constraintLeft_toLeftOf             view1左边对齐view2的左边
   layout_constraintLeft_toRightOf            view1左边对齐view2的右边
   layout_constraintRight_toLeftOf            view1右边对齐view2的左边
   layout_constraintRight_toRightOf           view1右边对齐view2的右边
   layout_constraintTop_toTopOf               view1顶部对齐view2的顶部
   layout_constraintTop_toBottomOf            view1顶部对齐view2的底部
   layout_constraintBottom_toTopOf            view1底部对齐view2的顶部
   layout_constraintBottom_toBottomOf         view1底部对齐view2的底部
   layout_constraintBaseline_toBaselineOf     view1基准线对齐view2的基准线
   layout_constraintStart_toEndOf             view1起始位置对齐view2的结束位置
   layout_constraintStart_toStartOf           view1起始位置view2的起始位置
   layout_constraintEnd_toStartOf             view1结束位置对齐view2的起始位置
   layout_constraintEnd_toEndOf               view1结束位置对齐view2的结束位置
   
2、角度属性
   android:id="@+id/TextView2"
   app:layout_constraintCircle="@+id/TextView1"
   app:layout_constraintCircleAngle="120"（角度）
   app:layout_constraintCircleRadius="150dp"（距离）
   指的是TextView2的中心在TextView1的中心的120度，距离为150dp
   
3、边距属性
   android:layout_marginStart
   android:layout_marginEnd
   android:layout_marginLeft
   android:layout_marginTop
   android:layout_marginRight
   android:layout_marginBottom
   貌似和线性或相对布局差不多，但是在约束布局中是不生效的，必须在约束布局中约束一个相对位置！
   如：约束到parent的左边和上边
   <android.support.constraint.ConstraintLayout 
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView
           android:id="@+id/TextView1"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_marginLeft="10dp"
           android:layout_marginTop="10dp" 
           app:layout_constraintLeft_toLeftOf="parent"
           app:layout_constraintTop_toTopOf="parent"/>
   </android.support.constraint.ConstraintLayout>
   
   goneMargin属性：用于约束的控件可见性被设置为gone的时候使用的margin值
       layout_goneMarginStart
       layout_goneMarginEnd
       layout_goneMarginLeft
       layout_goneMarginTop
       layout_goneMarginRight
       layout_goneMarginBottom
       
       如：tv2 在 tv1 的右边，当tv2 不可见时，tv1 右边将出现10dp的边距空白
       <android.support.constraint.ConstraintLayout 
           android:layout_width="match_parent"
           android:layout_height="match_parent">
       
           <TextView
               android:id="@+id/TextView1"/>
       
           <TextView
               android:id="@+id/TextView2"
               app:layout_constraintLeft_toRightOf="@+id/TextView1"
               app:layout_goneMarginLeft="10dp"/>
       
       </android.support.constraint.ConstraintLayout>

4、居中和偏移
   在 RelativeLayout 居中，layout_centerInParent 设为 true 即可；但是在 ConstraintLayout 中的写法是：
     app:layout_constraintBottom_toBottomOf="parent"
     app:layout_constraintLeft_toLeftOf="parent"
     app:layout_constraintRight_toRightOf="parent"
     app:layout_constraintTop_toTopOf="parent"
    
     同理：layout_centerHorizontal 等同于
     app:layout_constraintLeft_toLeftOf="parent"
     app:layout_constraintRight_toRightOf="parent"
     
     layout_centerVertical等同于
     app:layout_constraintBottom_toBottomOf="parent"
     app:layout_constraintTop_toTopOf="parent"
      
     偏移属性：
     layout_constraintHorizontal_bias 水平偏移
     layout_constraintVertical_bias 垂直偏移
     如：0-1 范围值，为0则最在左侧或最上方，为1则在最右侧，为0.5则水平居中或垂直居中
     <TextView
             android:id="@+id/TextView1"
             app:layout_constraintHorizontal_bias="0.3" (偏左位置)
             app:layout_constraintLeft_toLeftOf="parent"
             app:layout_constraintRight_toRightOf="parent" />
             
5、尺寸约束
   官方使用 0dp (MATCH_CONSTRAINT)，在ConstraintLayout中不使用match_parent，否则该组件所有的约束失效。
   比如：
       <TextView
               android:id="@+id/TextView1"
               android:layout_width="0dp"
               android:layout_height="wrap_content"
               android:layout_marginLeft="50dp"
               app:layout_constraintLeft_toLeftOf="parent"
               app:layout_constraintRight_toRightOf="parent"/>
   
   设置高宽比：layout_constraintDimensionRatio
   比如：宽高比设置为1:1，view 形成一个正方形
   <TextView
           android:id="@+id/TextView1"
           android:layout_width="0dp"
           android:layout_height="wrap_content"
           app:layout_constraintDimensionRatio="1:1"
           app:layout_constraintLeft_toLeftOf="parent"
           app:layout_constraintRight_toRightOf="parent" />
           
         还可以这样做：app:layout_constraintDimensionRatio="H,2:3" 指的是 高:宽=2:3
                     app:layout_constraintDimensionRatio="W,2:3" 指的是 宽:高=2:3
                     
6、链式(类似于线性布局)
      如：tv1 -> tv2 -> tv3 形成一条链，
      <TextView
          android:id="@+id/TextView1"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          app:layout_constraintLeft_toLeftOf="parent"
          app:layout_constraintRight_toLeftOf="@+id/TextView2" />
      <TextView
          android:id="@+id/TextView2"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          app:layout_constraintLeft_toRightOf="@+id/TextView1"
          app:layout_constraintRight_toLeftOf="@+id/TextView3"
          app:layout_constraintRight_toRightOf="parent" />
      <TextView
          android:id="@+id/TextView3"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          app:layout_constraintLeft_toRightOf="@+id/TextView2"
          app:layout_constraintRight_toRightOf="parent" />
      如果把以上3个 tv 的宽度都设为0dp，那么可以在每个 tv 中设置横向权重layout_constraintHorizontal_weight(constraintVertical为纵向)
          
      layout_constraintHorizontal_chainStyle 改变整条链的样式，chains提供了3种样式，分别是：
      CHAIN_SPREAD —— 展开元素 (默认)；
      CHAIN_SPREAD_INSIDE —— 展开元素，但链的两端贴近parent；
      CHAIN_PACKED —— 链的元素将被打包在一起。
 
7、Barrier
     如：tv1 和 tv2 设为屏障，使得 tv3 在屏障的右侧
     <TextView
             android:id="@+id/TextView1"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content" />
         <TextView
             android:id="@+id/TextView2"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             app:layout_constraintTop_toBottomOf="@+id/TextView1" />
         <android.support.constraint.Barrier
             android:id="@+id/barrier"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             app:barrierDirection="right"
             app:constraint_referenced_ids="TextView1,TextView2" />
         <TextView
             android:id="@+id/TextView3"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             app:layout_constraintLeft_toRightOf="@+id/barrier" />
         
         app:barrierDirection 为屏障所在的位置，可设置的值有：bottom、end、left、right、start、top
         app:constraint_referenced_ids 为屏障引用的控件，可设置多个(用“,”隔开)
         
8、Group 可以把多个控件归为一组，方便隐藏或显示一组控件
      <android.support.constraint.Group
           android:id="@+id/group"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:visibility="invisible"
           app:constraint_referenced_ids="TextView1,TextView3" />
           
9、Placeholder
   如：最终 tv 显示在界面的最左上方
   <android.support.constraint.Placeholder
           android:id="@+id/placeholder"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           app:content="@+id/textview"
           app:layout_constraintLeft_toLeftOf="parent"
           app:layout_constraintTop_toTopOf="parent" />
       <TextView
           android:id="@+id/textview"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:background="#cccccc"
           android:padding="16dp"
           android:text="TextView"
           android:textColor="#000000"
           app:layout_constraintRight_toRightOf="parent"
           app:layout_constraintTop_toTopOf="parent" />

10、Guideline
   Guildline 像辅助线一样，在预览的时候帮助你完成布局（不会显示在界面上）。
   Guildline的主要属性：
      android:orientation 垂直vertical，水平horizontal
      layout_constraintGuide_begin 开始位置
      layout_constraintGuide_end 结束位置
      layout_constraintGuide_percent 距离顶部的百分比(orientation = horizontal时则为距离左边)
   举个例子：
       <android.support.constraint.Guideline
           android:id="@+id/guideline1"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:orientation="horizontal"
           app:layout_constraintGuide_begin="50dp" />
       <android.support.constraint.Guideline
           android:id="@+id/guideline2"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:orientation="vertical"
           app:layout_constraintGuide_percent="0.5" />
           
   guideline1为水平辅助线，开始位置是距离顶部50dp，guideline2位垂直辅助线，开始位置为屏幕宽的0.5(中点位置)
   
   
   
   