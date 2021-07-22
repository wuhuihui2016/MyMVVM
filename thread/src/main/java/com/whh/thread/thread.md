# Thread 2021.06.29

进程：程序运行资源分配的最小单位。
分为用户进程和系统进程，凡是用于完成操作系统的各种功能的进程就是系统进程,它们就 是处于运行状态下的操作系统本身,用户进程就是所有由你启动的进程。

线程是 CPU 调度的最小单位,必须依赖于进程而存在
线程调度分为协同式调度和抢占式调度

什么是线程安全？？？
【百度】 线程安全的代码通过同步机制，保证各个线程都可以正常且正确的执行，不会出现数据污染等意外情况。
必须保证数据的
   原子性：简单来说，就是相关操作不会中途被其他线程干扰，一般通过同步机制实现。
   可见性：是一个线程修改了某个共享变量，其状态能够立即被其他线程知晓，通常被解释为将线程本地状态反映到主内存上，volatile 就是负责保证可见性的。
   有序性：是保证线程内串行语义，避免指令重排等。

1、并行：同时执行不同任务；
   并发：交替执行不同任务，时间片轮转为并发进行
   
2、操作系统(OS)限制线程数：Linux 100个，windows 200个

3、两种线程启动的方法：
   Thread：对线程的抽象，也可以实现共享资源，但不适合，当以 Thread 方式去实现资源共享时，实际上源码内部是将 thread 向下转型为 Runnable，依然以 Runnable 形式去实现资源共享；
   Runnable：对任务的抽象，更容易可以实现多个线程间的资源共享，不能直接run。Runnable 相对于一个 Task，并不具有线程的概念；
      如果直接去调用Runnable 的 run，相当于直接在主线程中执行了一个函数而已，并未开启线程去执行，于是主线程资源来不及释放，导致 OOM 而崩溃。
   Thread 和 Runnable 的关系：
      实质是继承关系。无论 Runnable 还是 Thread，都会 new Thread，然后执行 run 方法。
      用法上，如果有复杂的线程操作需求，那就选择继承 Thread，如果简单的执行一个任务，那就实现 runnable。
   通过 Callable(属于 java.util.concurrent) 和 Future 创建线程，实际也是封装在 Runnable 里执行，Thread 的构造方法里没有 Callable 的参数
   FutureTask 实现了 RunnableFuture 接口，而 RunnableFuture 继承了 Runnable 接口和 Future 接口，所以它既可以 作为 Runnable 被线程执行，又可以作为 Future 得到 Callable 的返回值
      
4、守护线程
   守护线程线程是一种支持型线程，因为它主要被用作程序中后台调度以及支持性工作，比如垃圾回收线程。
   线程分为用户线程和守护线程，thread.setDaemon(true); 设为守护线程，
      该方法必须在 thread.start() 之前设置，否则异常：IllegalThreadStateException
      即不能把正在运行的线程设为守护线程
   守护线程中，finally 不一定起作用，因此不能依靠 finally 块执行关闭或清理资源的逻辑，而用户线程一定会执行 finally
   当主线程结束，守护线程也会跟着结束，反过来说，任何用户线程还在运行，守护线程就不会终止
   守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
   
   守护线程和用户线程的区别？
      判断虚拟机(JVM)何时离开，Daemon 是为其他线程提供服务，如果全部的 User Thread 已经结束，Daemon 没有可服务的线程，JVM 关闭。 
      扩展：Thread Dump 打印出来的线程信息，含有 daemon 字样的线程即为守 护进程
       

5、锁练习：SynchronizedThread.java
   
   sleep() 不释放锁、释放cpu
   yiled() 不释放锁、释放cpu
   notify() / notifyAll() 不释放锁
   wait() 释放锁、释放cpu
   join() 释放锁、抢占cpu
   只有running的时候才会获取CPU时间片
   
   死锁：多个操作者争夺多个资源；争夺资源的顺序不对；拿到资源不放手(通俗理解)
        互斥条件；请求和保持条件；不剥夺条件；环路等待条件
   活锁：多个线程想要用一个资源而互相谦让，导致最终都没有取用到该资源；
   线程饥饿：低优先级的线程，总是拿不到执行时间。
   
   如何减少锁的竞争？
       减少锁的粒度(如果锁保护了多个对象时，增长了锁的力度)；缩小锁的范围；避免多余的锁；锁分段(比如：ConcurrrentHashMap)
        
   锁的状态：无锁状态，偏向锁状态，轻量级锁状态和重量级锁状态。
   
   如何检查一个线程拥有锁？ Thread.java ==> public static native boolean holdsLock(Object obj);
   
6、CAS：compare and swap。原子操作（变量的内存地址，期望的值(旧值)，新值）【乐观锁的实现方式】
  利用现代处理器都支持的CAS的指令，循环该指令，直到成功为止。
  
  AtomicInteger 原子操作，相比于加锁机制，原子变量性能更高。
  自旋=不停循环
  
  CAS 的不足：
  ABA问题：变量中间被修改过，但又被恢复；
  开销问题：循环次数过多，导致开销大；
  只能保证一个共享变量的原子操作。此时 sync 加锁更适用
  
  Jdk中相关原子操作类的使用
  更新基本类型类: AtomicBoolean，AtomicInteger，AtomicLong
  更新数组类: AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray
  更新引用类型: AtomicReference，AtomicMarkableReference，AtomicStampedReference
  
  解决ABA问题：解决办法：在每次修改时加版本戳
  AtomicMarkableReference：关心变量是否被修改
  AtomicStampedReference：关心变量是否被修改和修改次数
  
7、阻塞队列出现：队列已满时；队列为空时。

   -ArrayBlockingQueue:一个由数组结构组成的有界阻塞队列。
   -LinkedBlockingQueue:一个由链表结构组成的有界阻塞队列。
   -PriorityBlockingQueue:一个支持优先级排序的无界阻塞队列。
   -DelayQueue:一个使用优先级队列实现的无界阻塞队列。
   -SynchronousQueue:一个不存储元素的阻塞队列。
   -LinkedTransferQueue:一个由链表结构组成的无界阻塞队列。
   -LinkedBlockingDeque:一个由链表结构组成的双向阻塞队列。
   
   无界：未限制容量，但是建议使用有界，容量有限，有界队列能增加系统的稳定性和预警能力，可以根据需要设大点，比如几千。
   
8、ReentrantLock 重入锁 同一个线程对于已经获得到的锁，可以多次继续申请到该 锁的使用权
   ReentrantReadWriteLock 读写锁 能够提供比排它锁更好的并发性和吞吐量
   不论是重入锁还是读写锁，他们都是通过 AQS（AbstractQueuedSynchronizer）来实现的，
   并发包作者（Doug Lea）期望 AQS 作为一个构建所或者实现其它自定义同步组件的基础框架
   
   可重入锁（ReentrantLock）的实现？ 
       线程可以重复进入任何一个它已经拥有的锁所同步着的代码块， synchronized、ReentrantLock 都是可重入的锁。
       实现上，线程每次获取锁时判定如果获得锁的线程是它自己时，简单将计数器累积即可，每释放一次锁，进行计数器累减，直到计算器归零，表示线程已经彻底释放锁。

ReentrantLock 的两种实现：
  NonfairSync 非公平锁（默认）：不做上述判断，插队执行
  FairSync 公平锁：判断当前是否有元素在等待，顺序执行；
  公平锁和非公平锁的实现，仅一行代码的区别：
  ReentrantLock lock = new ReentrantLock(true);  // true：公平锁
  
  公平锁指多个线程同时尝试获取同一把锁时，获取锁的顺序按照线程达到的顺序；ReentrantLock的默认实现是非公平锁，但是也可以设置为公平锁。
  非公平锁则允许线程“插队”。synchronized是非公平锁，
  
9、悲观锁和乐观锁 (https://www.jianshu.com/p/d2ac26ca6525)
   悲观锁：在进行数据库中一条数据修改时，为了避免那其他人修改，直接将该数据进行加锁以防止并发；
          具有强烈的独占和排他性
          悲观锁的实现：          
          传统的关系型数据库使用这种锁机制，比如行锁、表锁、读锁、写锁等，都是在操作之前先上锁。
          Java 里面的同步 synchronized 关键字的实现。
          悲观锁主要分为共享锁和排他锁：
          共享锁【shared locks】又称为读锁，简称 S 锁。多个事务对于同一数据可以共享一把锁，都能访问到数据，但是只能读不能修改。
          排他锁【exclusive locks】又称为写锁，简称 X 锁。不能与其他锁并存，如果一个事务获取了一个数据行的排他锁，其他事务就不能再获取该行的其他锁，包括共享锁和排他锁。获取排他锁的事务可以对数据行读取和修改。
   乐观锁:假设数据一般情况不会造成冲突，所以在数据进行提交更新的时候，才会正式对数据的冲突与否进行检测，如果冲突，则返回给用户异常信息，让用户决定如何去做。乐观锁适用于读多写少的场景，这样可以提高程序的吞吐量。
         乐观锁实现方式:不需要借助数据库的锁机制
         主要两个步骤：冲突检测和数据更新。比较典型的就是 CAS (Compare and Swap)。
         
   悲观锁：线程一旦得到锁，其他线程就挂起等待，适用于写入操作频繁的场景；synchronized 就是悲观锁
   乐观锁：假设没有冲突，不加锁，更新数据时判断该数据是否过期，过期的话则不进行数据更新，适用于读取操作频繁的场景
   乐观锁 CAS：Compare And Swap，更新数据时先比较原值是否相等，不相等则表示数据过去，不进行数据更新
   乐观锁实现：AtomicInteger、AtomicLong、AtomicBoolean
   作者：Alvin老师
   链接：https://www.jianshu.com/p/686f36a12994
   
   强引用：不会被回收
   软引用：内存不足时会被回收
   弱引用：gc 时会被回收
   虚引用：无法通过虚引用得到对象，可以监听对象的回收

  
  
         
   