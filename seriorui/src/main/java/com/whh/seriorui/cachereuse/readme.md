### RecycleView
一、缓存复用
   1、四级缓存(也有三级缓存的说法，则为后三级缓存)  
     1. mChangeScrap与 mAttachedScrap 用来缓存还在屏幕内的 ViewHolder 
     2. mCachedViews 用来缓存移除屏幕之外的 ViewHolder 
     3. mViewCacheExtension 开发给用户的自定义扩展缓存，需要用户自己 管理 View 的创建和缓存 
     4. RecycledViewPool ViewHolder 缓存池
