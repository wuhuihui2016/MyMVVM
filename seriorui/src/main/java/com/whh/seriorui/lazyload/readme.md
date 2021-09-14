###Fragment 懒加载 
###[https://www.jianshu.com/p/2201a107d5b5?utm_campaign=hugo]
###[https://github.com/wuhuihui2016/AndroidxLazyLoad.git]


1.Fragment 完整生命周期：
onAttach -> onCreate -> onCreatedView -> onActivityCreated -> onStart -> onResume -> 
onPause -> onStop -> onDestroyView -> onDestroy -> onDetach


Fragment 懒加载 
###[https://www.jianshu.com/p/2201a107d5b5]
1.add+show+hide 模式下的老方案:
  通过 show+hide 方式控制 Fragment 的显隐，在第一次初始化后，Fragment 任何的生命周期方法都不会调用，只有 onHiddenChanged 方法会被调用。
  那么，在 add+show+hide 模式下控制 Fragment 的懒加载，需要做这两步：
    a.在 onResume() 函数中调用 isHidden() 函数，来处理默认显示的 Fragment
    b.在 onHiddenChanged 函数中控制其他不可见的 Fragment.
2.ViewPager+Fragment 模式下的老方案
  使用传统方式处理 ViewPager 中 Fragment 的懒加载，则需要控制 setUserVisibleHint(boolean isVisibleToUser) 函数
     使用 ViewPager，切换回上一个 Fragment 页面时（已经初始化完毕），不会回调任何生命周期方法以及onHiddenChanged()，只有 setUserVisibleHint(boolean isVisibleToUser) 会被回调。
     setUserVisibleHint(boolean isVisibleToUser) 方法总是会优先于 Fragment 生命周期函数的调用。
  调用 setOffscreenPageLimit(int limit) 方法去设置 ViewPager 预缓存的 Fragment 个数。默认情况下 ViewPager 预缓存 Fragment 的个数为 1 。
3.Androidx 下 Fragment 懒加载实现
  在 FragmentPagerAdapter 与 FragmentStatePagerAdapter 新增了含有 behavior 字段的构造函数
     如果 behavior 的值为 BEHAVIOR_SET_USER_VISIBLE_HINT，那么当 Fragment 对用户的可见状态发生改变时，setUserVisibleHint 方法会被调用。
     如果 behavior 的值为 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ，那么当前选中的 Fragment 在 Lifecycle.State#RESUMED 状态 ，
       其他不可见的 Fragment 会被限制在 Lifecycle.State#STARTED 状态。
    使用了 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT 后，确实只有当前可见的 Fragment 调用了 onResume 方法。而导致产生这种改变的原因，是因为 FragmentPagerAdapter 在其 setPrimaryItem 方法中调用了 setMaxLifecycle 方法

   add+show+hide 模式下的新方案
   仍然要考虑 add+show+hide 模式下的 Fragment 懒加载
   [https://github.com/AndyJennifer/AndroidxLazyLoad]
