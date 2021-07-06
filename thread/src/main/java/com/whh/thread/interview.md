## interview  from 享学课堂 20210705

1、sychronied修饰普通方法和静态方法的区别？什么是可见性？（https://blog.csdn.net/qq_26414049/article/details/106351170）
    修饰普通方法的为对象锁；（对象锁:用于对象实例方法,或者一个对象实例上的）
    修饰静态方法的时类锁；（类锁:作用于类的静态方法或者一个类的class对象上的）  
    类的对象可以有多个,但每个类只有一个class对象,不同对象实例的对象锁是互相不干扰的；
    但是每个类只有一个类锁，类锁和对象锁之间是相互不干扰的

   可见性是指多个线程访问同一个变量时候,其中一个线程修改了这个变量的值,其他线程能够立即看到修改的值

2、锁分哪几类？（https://www.cnblogs.com/catluo/p/10993197.html）
   公平锁/非公平锁
   可重入锁
   独享锁/共享锁
   互斥锁/读写锁
   乐观锁/悲观锁
   分段锁
   无锁/偏向锁/轻量级锁/重量级锁
   自旋锁/适应性自旋锁
   见图《com/whh/thread/lock/ja va 主流锁.png》   

2、CAS无锁编程的原理。（https://www.jianshu.com/p/3179005184dd）
   CAS实现原理：CAS有三个操作数：内存值V、旧的预期值A、要修改的值B，当且仅当预期值A和内存值V相同时，
   将内存值修改为B并返回true，否则什么都不做并返回false。
   如果地址上的变量，比较变量值和期望的一样，则交换为新值；否则自循重试CAS，直到成功为止；
   CAS 的不足：
     ABA问题：变量中间被修改过，但又被恢复；
     开销问题：循环次数过多，导致开销大；
     只能保证一个共享变量的原子操作。此时sync 加锁更适用
     
3、ReentrantLock的实现原理？
  ReentrantLock的实现基于队列同步器（AbstractQueuedSynchronizer，后面简称AQS）
  ReentrantLock的可重入功能基于AQS的同步状态：state。
  其原理大致为：当某一线程获取锁后，将state值+1，并记录下当前持有锁的线程，再有线程来获取锁时，
  判断这个线程与持有锁的线程是否是同一个线程，如果是，将state值再+1，如果不是，阻塞线程。 
  当线程释放锁时，将state值-1，当state值减为0时，表示当前线程彻底释放了锁，然后将记录当前持有锁的线程的那个字段设置为null，并唤醒其他线程，使其重新竞争锁。

4、AQS原理(小米京东)
  (https://github.com/Lord-X/awesome-it-blog/blob/master/java/Java%E9%98%9F%E5%88%97%E5%90%8C%E6%AD%A5%E5%99%A8%EF%BC%88AQS%EF%BC%89%E5%88%B0%E5%BA%95%E6%98%AF%E6%80%8E%E4%B9%88%E4%B8%80%E5%9B%9E%E4%BA%8B.md)
  队列同步器AbstractQueuedSynchronizer（后面简称AQS）是实现锁和有关同步器的一个基础框架

5、Synchronized的原理以及与ReentrantLock的区别。(360)

   synchronized属于独占式悲观锁通过JVM隐式实现，只允许同一时刻只有一个线程操作资源。
      Java中每个对象都隐式包含一个monitor（监视器）对象
      加锁的过程其实就是竞争monitor的过程，当线程进入字节码monitiorenter指令后
      线程将持有monitor对象，执行monitorexit时释放monitor对象
      当其他线程没有拿到monitor对象时，则需要阻塞等待获取该对象。
   ReentrantLock是Lock的默认实现方式之一
      基于AQS(Abstrack Queued Synchronizer,队列同步器) 实现的默认是通过非公平锁实现的，
      它的内部有一个state的状态字段用于表示锁是否被占用，如果0则表示锁未被占用，此时线程就可以吧state改为1，并成功获得锁而其他未获得锁的线程只能去排队等待获取资源

   JDK1.6之后synchronized性能略低于ReentrantLock

  【区别】：
  1、synchronized是关键字，ReentrantLock是类；
  2、ReentrantLock可以获取锁的时间进行设置，避免死锁；
  3、ReentrantLock可以获取各种锁的信息；
  4、ReentrantLock可以灵活实现多路通知；
  5、机制：sync操作Mark Word，lock调用Unsafe类的park（）方法。
  6、synchronized是JVM隐式实现的，而ReentrantLock是Java语言提供的API
  7、ReentrantLock可设置为公平锁，而synchronized却不行
  8、ReentrantLock只能修饰代码块，而Synchronized可以用于修饰方法、修饰代码块等
  9、ReentrantLock需要手动加锁和释放锁，如果忘记释放锁，则会造成资源被永久占用而synchronized无需手动释放锁
  10、ReentrantLock可以知道是否成功获得了锁，而synchronized不行

6、Synchronized做了哪些优化(京东) **
  (https://blog.csdn.net/u012957549/article/details/105467237)
  jdk1.6对锁的实现引入了大量的优化，如自旋锁、适应性自旋锁、锁消除、锁粗化、偏向锁、轻量级锁等技术来减少锁操作的开销。
  锁主要存在四中状态，依次是：无锁状态、偏向锁状态、轻量级锁状态、重量级锁状态，他们会随着竞争的激烈而逐渐升级。
  注意锁可以升级不可降级，这种策略是为了提高获得锁和释放锁的效率。
  
  1.适应自旋锁:为了减少线程状态改变带来的消耗 不停地执行当前线程  
  2.锁消除：不可能存在共享数据竞争的锁进行消除
  3.锁粗化：将连续的加锁 精简到只加一次锁
  4.轻量级锁：无竞争条件下 通过CAS消除同步互斥
  5.偏向锁：无竞争条件下 消除整个同步互斥，连CAS都不操作。

7、Synchronized static与非static锁的区别和范围（小米)
   当synchronized修饰一个static方法时，多线程下，获取的是类锁(即Class本身，注意:不是实例)，
   作用范围是整个静态方法，作用的对象是这个类的所有对象。
   
   当synchronized修饰一个非static方法时，多线程下，获取的是对象锁(即类的实例对象)，
   作用范围是整个方法，作用对象是调用该方法的对象
   
   【结论】: 类锁和对象锁,一个是类的Class对象的锁,一个是类的实例的锁。

8、volatile能否保证线程安全？在DCL上的作用是什么？ ***

9、volatile和synchronize有什么区别？(B站 小米 京东）
   1.volatile是线程同步的轻量级实现，所以volatile的性能要比synchronize好；
   volatile只能用于修饰变量，synchronize可以用于修饰方法、代码块。随着jdk技术的发展，synchronize在执行效率上会得到较大提升，所以synchronize在项目过程中还是较为常见的；
   2.多线程访问volatile不会发生阻塞；而synchronize会发生阻塞；
   3.volatile能保证变量在私有内存和主内存间的同步，但不能保证变量的原子性；synchronize可以保证变量原子性；
   4.volatile是变量在多线程之间的可见性；synchronize是多线程之间访问资源的同步性；
   对于volatile修饰的变量，可以解决变量读时可见性问题，无法保证原子性。对于多线程访问同一个实例变量还是需要加锁同步。

10、什么是守护线程？你是如何退出一个线程的？
   守护线程，也叫Daemon线程，它是一种支持型、服务型线程，主要被用作程序中后台调度以及支持性工作，
   跟上层业务逻辑基本不挂钩。
   thread.setDaemon(true); 设为守护线程，
         该方法必须在 thread.start() 之前设置，否则异常：IllegalThreadStateException
         即不能把正在运行的线程设为守护线程
   守护线程中，finally 不一定起作用，而用户线程一定会执行 finally
   当主线程结束，守护线程也会跟着结束，反过来说，任何用户线程还在运行，守护线程就不会终止
   守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
   
   线程退出：
       当Thread的run方法执行结束后，线程便会自动退出，生命周期结束，这属于线程正常退出的范畴；
       不建议使用 stop() ，方法已弃用，同时该方法不安全，不能保证资源及时释放；
       可以使用 interrupt()，使用 isInterrupted() 获取当前中断标志，标志为true 则做线程中断处理

11、sleep 、 wait、 yield 的区别，【wait的线程如何唤醒它】？(东方头条) ****
   sleep()：线程的静态方法，在指定的等待时间里，让当前正在执行的线程暂停执行，进入阻塞状态，然其他线程获取执行机会；
            但是不会释放锁，其他线程仍然不能共享数据；
   wait()：Object类的方法，必须在synchronized语句块内使用，会释放对象的“锁标志”
   yield()：sleep()方法类似，也不会释放“锁标志”，
          区别在于，它没有参数，只是使当前线程重新回到可执行状态，执行yield()的线程有可能在进入到可执行状态后马上又被执行，
          另外yield()方法只能使同优先级或者高优先级的线程得到执行机会。

12、sleep是可中断的么？(小米) ***
    可以响应线程中断
    
13、线程生命周期？
    线程的生命周期包含5个阶段，包括：新建New、就绪Runnable、运行Running、阻塞Blocked、销毁Dead。
       新建：new出来的线程；
       就绪：调用的线程的start()方法后，这时候线程处于等待CPU分配资源阶段，谁先抢的CPU资源，谁开始执行;
       运行：当就绪的线程被调度并获得CPU资源时，便进入运行状态，run方法定义了线程的操作和功能;
       阻塞：在运行状态的时候，可能因为某些原因导致运行状态的线程变成了阻塞状态，比如sleep()、wait()之后线程就处于了阻塞状态，
            这个时候需要其他机制将处于阻塞状态的线程唤醒，比如调用notify或者notifyAll()方法。
            唤醒的线程不会立刻执行run方法，它们要再次等待CPU分配资源进入运行状态;
       销毁：如果线程正常执行完毕后或线程被提前强制性的终止或出现异常导致结束，那么线程就要被销毁，释放资源。

14、ThreadLocal是什么？（https://www.jianshu.com/p/3c5d7f09dfbd）
    线程内部的存储类，可以在指定线程内存储数据，数据存储以后，只有指定线程可以得到存储数据
    提供线程的变量副本，每个线程有一个 ThreadLocalMap，默认为长度为16的Entry数组，以键值对的形式保存线程变量，key 是当前线程，value 是需要存储的对象
    保存多个不同数据时：使用自增的索引threadLocalHashCode作为存储的地址

15、线程池基本原理？[https://www.cnblogs.com/spec-dog/p/11149741.html]
   如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
   如果此时线程池中的数量等于corePoolSize，但是缓冲队列workQueue未满，那么任务被放入缓冲队列。
   如果此时线程池中的数量大于等于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
   如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。
   当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
 
   【总结】处理任务判断的优先级为 核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。

16、有三个线程T1，T2，T3，怎么确保它们按顺序执行？
    思路一：在T1的run()中调用T2.start()，在T2的run()中调用T3.start();
    思路二：t1.start();t1.join(); ==> t2.start();t2.join(); ==> t3.start();t3.join();
    