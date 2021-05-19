一、xml绑定   
   1、import用于导入使用到的类
       如果在导入时，类名有冲突，可以使用alias重命名，例如：
   <import type="android.view.View"/>
   <import type="com.example.real.estate.View"
           alias="Vista"/>

   2、variable定义变量，其中name、type是必要属性：
       name可自定义，type则必须是基本类型或引用类型
      变量可能不能在 IDE 中完成自动提示功能
   import、variable两者为同一级

   3、表达式和规则：
      在控件中设置属性，可通过@{}来引用
      三目表达式：@{TextUtils.isEmpty(user.name) ? User.defultName : user.name}
      在表达式中，二层字符串引用`` 比如：android:text="@{TextUtils.isEmpty(item_user.name) ? `defult` : item_user.name}" />
      在""特殊符号需要转义，<为&gt;，>为&gt;，否则报错
      注意：在xml中即使表达式过长，也不可换行，否则报错：AAPT: error: not well-formed (invalid token).
     
   4、include关键字
        例如： <include layout="@layout/layout_item" bind:user="@{user}"/>
       使用bind，需导入：xmlns:bind="http://schemas.android.com/apk/res-auto"
       使用tools，需导入：xmlns:tools="http://schemas.android.com/tools"
      
二、java绑定
    1、Binding生成的规则：根据绑定的布局文件名，去除_变驼峰命名方式
         例如绑定的布局名为main_activity，则生成的Binding名为MainActivityBinding，所在路径：build/intermediates/javac/debug/classes/com/example/mymvvm/databinding
    2、DataBinding 代码生成会自动检查是否为 null，避免NullPointerException，
         例如在表达式 @{user.name} 中，若 user 为 null，@{user.name} 值为 null，@{user.age} 值为 0
    3、java赋值方法
         3.1 通过MainActivityBinding.控件id，直接赋值 如:binding.textView.setText(user.name);
         3.2 MainActivityBinding调用set方法  如：binding.setUser(user);
         3.3 MainActivityBinding调用setVariable方法 如：binding.setVariable(BR.user, user);
    4、事件绑定
         方法1 android:onClick="@{eventlistener.listButtonClik}" //可用，已过时，使用方法2替代（ERROR: Method references using '.' is deprecated. Instead of 'eventlistener.listButtonClik', use 'eventlistener::listButtonClik'）
         方法2 android:onClick="@{eventlistener::listButtonClik}"
         方法3 android:onClick="@{(View)->eventlistener.listButtonClik(View)}"
       android:onLongClick也可同样设置
    5、双向绑定：
          将数据model的值显示到视图上面，model改变时自动更新视图，视图改变时自动更新model；
          在dataBinding中通过BaseObservable和@={}来实现，注意不是@{}(赋值)，是@={}
          比如：android:text="@={user.name}"
          在activity中还可以通过addOnPropertyChangedCallback来监听视图的改变；
     6、动画初现
        binding.addOnRebindCallback(new OnRebindCallback() {
                   @Override
                   public boolean onPreBind(ViewDataBinding binding) {
                       ViewGroup viewGroup = (ViewGroup) binding.getRoot();
                       TransitionManager.beginDelayedTransition(viewGroup);
                       return true;
                   }
               });
     7、ImagView + Glide
        自定义ImageViewAttrAdapter
        ImageView控件绑定：
           app:imageUrl="@{item_user.photo}"
           app:placeHolder="@{@drawable/loading}"
           app:error="@{@drawable/error}" 
