## ThreadPool

线程池：(https://www.cnblogs.com/dafanjoy/p/9729358.html)
   （https://www.cnblogs.com/spec-dog/p/11149741.html）
   为什么使用线程池？
   1.降低资源消耗。通过重复利用已创建的线程降低线程创建、销毁线程造成的消耗。
   2.提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。
   3.提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配、调优和监控
   
   见图：《线程池实现.png》
   从线程池中取线程执行任务，如果核心线程数已满，放入阻塞队列执行;
   再启动线程执行，如果最大线程数已满，则启动拒绝策略。
   
   public ThreadPoolExecutor(int corePoolSize,
                                 int maximumPoolSize,
                                 long keepAliveTime,
                                 TimeUnit unit,
                                 BlockingQueue<Runnable> workQueue,
                                 ThreadFactory threadFactory,
                                 RejectedExecutionHandler handler)
   构造函数的参数含义如下：
   corePoolSize:线程池中的线程数量，它的数量决定了添加的任务是开辟新的线程去执行，还是放到workQueue任务队列中去；
   maximumPoolSize:线程池中的最大线程数量，这个参数会根据你使用的workQueue任务队列的类型，决定线程池会开辟的最大线程数量；
   keepAliveTime:当线程池中空闲线程数量超过corePoolSize时，多余的线程会在多长时间内被销毁；
   unit:keepAliveTime的单位
   workQueue:任务队列，被添加到线程池中，但尚未被执行的任务；它一般分为直接提交队列、有界任务队列、无界任务队列、优先任务队列几种；
   threadFactory:线程工厂，用于创建线程，一般用默认即可；
   handler:拒绝策略；当任务太多来不及处理时，如何拒绝任务；
   
   ThreadPoolExecutor提供了四种策略：
     AbortPolicy:丢弃任务并抛出RejectedExecutionException异常；也是默认的处理方式。
     DiscardPolicy：丢弃任务，但是不抛出异常。
     DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     CallerRunsPolicy：由调用线程处理该任务
     可以通过实现RejectedExecutionHandler接口自定义处理方式。     
   
   比如：
   ExecutorService pool = new ThreadPoolExecutor(
   1, 2, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
   Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());                        
   
   运行线程池：
         submit() 该方法返回一个Future对象，可执行带返回值的线程；或者执行想随时可以取消的线程。Future对象的get()方法获取返回值。Future对象的cancel(true/false)取消任务，未开始或已完成返回false，参数表示是否中断执行中的线程
         execute() 没有返回值。
   
   关闭线程池：
   Shutdown:尝试关闭，把当前未执行任务的线程中断；
   ShutdownNow:不论线程是否在运行，都会尝试中断，不一定成功，因为线程是协作机制，只是发出中断信号，还是要看是否处理了中断，比如：if(isInterrupt()){....}
   
   合理配置线程池：
   Runtime.getRuntime().availableProcessors(); //获取 CPU 核心数
   区分任务特性：
   1、CPU 密集型：CPU 在不停计算，线程数不要超过CPU 核心数； ，最大+1    
   +1的原因：会产生虚拟内存，出现页缺失，操作系统为了防止CPU空闲，充分利用CPU
   2、IO 密集型：网络通讯，读写磁盘；线程数：CPU 核心数 * 2
   3、混合型
   网络通讯，读写磁盘时基本不用CPU
   
   Executors线程工厂类
   Executors.newCachedThreadPool();
     说明: 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程.
     内部实现：new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L,TimeUnit.SECONDS,new SynchronousQueue());
   Executors.newFixedThreadPool(int);
     说明: 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     内部实现：new ThreadPoolExecutor(nThreads, nThreads,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue());
   Executors.newSingleThreadExecutor();
     说明:创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照顺序执行。
     内部实现：new ThreadPoolExecutor(1,1,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue())
   Executors.newScheduledThreadPool(int);
     说明:创建一个定长线程池，支持定时及周期性任务执行。
     内部实现：new ScheduledThreadPoolExecutor(corePoolSize)