# Jetpack DataBinding + MVVM

为什么配置了 dataBinding{enabled = true}之后就可以使用dataBinding方式进行开发了？
Android Studio中是依靠gradle来管理项目的，在创建一个项目时，从开始创建一直到创建完毕，整个过程是需要执行很多个gradle task的，
这些task有很多是系统预先帮我们定义好的，例如build task，clean task等，DataBinding相关的task也是系统预先帮我们定义好的，
但是默认情况下，DataBinding相关的task在task列表中是没有的，因为我们没有开启dataBinding，但是一旦我们通过 dataBinding{enabled = true}
的方式开启DataBinding之后，DataBinding相关的task就会出现在task列表中，每当我们执行编译之类的操作时，就会执行这些dataBinding Task, 
这些task的作用就是检查并生成相关dataBinding代码，例如dataBindingExportBuildInfoDebug这个task就是用来导出debug模式下的build信息的。
[作者：唠嗑008](https://www.jianshu.com/p/53925ccb900e)

=================================<<start Jetpack DataBinding start>>=================================
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

   3、lambda表达式和规则(lambda表达式是Java8的新特性)：
      在控件中设置属性，可通过@{}来引用
      三目表达式：@{TextUtils.isEmpty(user.name) ? User.defultName : user.name}
      在表达式中，二层字符串引用`` 例如：android:text="@{TextUtils.isEmpty(item_user.name) ? `defult` : item_user.name}" />
      在""特殊符号需要转义，"<"为"&lt;"，">"为"&gt;"，否则报错,例如：《main_activity.xml》
      注意：在xml中即使表达式过长，也不可换行，否则报错：AAPT: error: not well-formed (invalid token).
     
   4、include关键字
        例如： <include layout="@layout/layout_item" bind:user="@{user}"/>
       使用bind，需导入：xmlns:bind="http://schemas.android.com/apk/res-auto"
       使用tools，需导入：xmlns:tools="http://schemas.android.com/tools"
      
二、java绑定
    1、Binding生成的规则：根据绑定的布局文件名，去除_变驼峰命名方式
         例如绑定的布局名为main_activity，则生成的Binding名为MainActivityBinding，
         所在路径：build/intermediates/javac/debug/classes/com/example/mymvvm/databinding
    2、DataBinding 代码生成会自动检查是否为 null，避免NullPointerException，
         例如在表达式 @{user.name} 中，若 user 为 null，@{user.name} 值为 null，@{user.age} 值为 0
    3、bean类配置成可直接在xml中绑定，需继承自"androidx.databinding.BaseObservable",例如：《com.whh.mymvvm.bean.User.java》
       java赋值方法
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
          例如：android:text="@={user.name}"
          在activity中还可以通过addOnPropertyChangedCallback来监听视图的改变；
     6、动画初现:
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

=================================<<end Jetpack DataBinding end>>=================================


=================================<<start MVVM start>>=================================
1、MVVM 概念：
    **MVVM模式加载数据**
    把数据请求放在ViewModel中MVVM将“数据模型数据双向绑定”的思想作为核心，
    因此在View和Model之间没有联系，通过ViewModel进行交互，而且Model和ViewModel之间的交互是双向的，
    因此视图的数据的变化会同时修改数据源，而数据源数据的变化也会立即反应到View上。
    即，ViewModel 是一个 View 信息的存储结构，ViewModel 和 View 上的信息是一一映射关系。
    **使用MVVM模式的好处**
    1.1 低耦合。View可以独立于Model变化和修改，一个ViewModel可以绑定到不同的View上，当View变化的时候Model可以不变，当Model变化的时候View也可以不变。
    1.2 可重用性。可以把一些视图的逻辑放在ViewModel里面，让很多View重用这段视图逻辑。
    1.3 独立开发。开发人员可以专注与业务逻辑和数据的开发(ViewModel)。设计人员可以专注于界面(View)的设计。
    1.4 可测试性。可以针对ViewModel来对界面(View)进行测试
    
2、MVVM 使用
   2.1 Model：继承自“androidx.databinding.BaseObservable”(目的：为了使用上述的databinding)，例如：《com.whh.mymvvm.bean.User.java》
   2.2 VM：继承自"androidx.lifecycle.ViewModel"，例如：《com.whh.mymvvm.real_mvvm.UserViewModel.java》
           定义好需要更新的变量，例如：private MutableLiveData<List<User>> users = new MutableLiveData<>();
           请求数据在这里做：okhttp3 + Retrofit，返回的数据用setUsers(userList)更新
   2.3 View：xml文件布局，在layout文件中绑定bean类值，例如：《activity_list_mvvm.xml》
       Avitivity、Fragment接收、更新数据：XRecyclerView + Binding，例如：《com.whh.mymvvm.real_mvvm.RealMVVMActivity.java》
          2.3.1 获取ViewModel： new ViewModelProvider(this).get(UserViewModel.class);
          2.3.2 获取lifecycleOwner：
                LifecycleOwner lifecycleOwner = new LifecycleOwner() {
                            @Override
                            public Lifecycle getLifecycle() {
                                return RealMVVMActivity.super.getLifecycle();
                            }
                        };
          2.3.3 监听数据请求失败原因
                userViewModel.getErrorMsg().observe(lifecycleOwner, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                RealMVVMActivity.this.loadFailure(s);
                            }
                        });                                
          2.3.4 userViewModel.loadData(currPage); //请求数据

3、引用的组件：   
    //Glide网络图加载
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    //网络请求
    implementation 'io.reactivex.rxjava2:rxjava:2.1.5'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    //okhttp3日志拦截器
    implementation 'com.squareup.okhttp3:logging-interceptor:3.4.2'
    //加载更多的xrecyclerview
    implementation 'com.jcodecraeer:xrecyclerview:1.3.2'
    //MVVM需要引用的生命周期组件
    implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0"
   
=================================<<end   MVVM   end>>=================================


