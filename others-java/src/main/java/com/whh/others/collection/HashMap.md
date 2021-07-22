## HashMap
 extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable
    
默认初始化容量为16，默认加载因子为0.75，当hashMap集合底层数组的容量达到75%时，开始扩容

HashMap的工作原理
HashMap基于hashing原理，通过put()和get()方法储存和获取对象。当将键值对传递给put()方法时，它调用键对象的hashCode()方法来计算hashcode，
然后找到bucket位置来储存Entry对象。当两个对象的hashcode相同时，它们的bucket位置相同，‘碰撞’会发生。
因为HashMap使用链表存储对象，这个Entry会存储在链表中，当获取对象时，通过键对象的equals()方法找到正确的键值对，然后返回值对象。

HashMap 死循环：
   在多线程环境下，HashMap 并发执行 put 操作时会触发扩容，多线程会使得 HashMap 的 
   Entry 链表形成环形数据结构，此时 Entry 的 next 节点永远不为空，就会产生死循环获取 Entry
   导致 CPU 利用率接近100%。 

HashMap 扩容过程 resize()
  1、取当前的 table 的2倍作为新 table 的大小；
  2、根据新 table 的大小 new 出一个新的 Entry 数组来，名为 newTable；
  3、轮询原 table 的每个位置，将每个位置上的 Entry 算出在 newTable 的位置，并以链表形式连接；
  4、所有原 table 上的 Entry 都轮询完毕后，则 HashMap 的 table 指向 newTable。
  
HashMap中 put、get 的实现原理
  1、map.put(k,v)实现原理
     （1）首先将k,v封装到Node对象当中（节点）。
     （2）然后它的底层会调用K的hashCode()方法得出hash值。
     （3）通过哈希表函数/哈希算法，将hash值转换成数组的下标，下标位置上如果没有任何元素，就把Node添加到这个位置上。
         如果说下标对应的位置上有链表。此时，就会拿着k和链表上每个节点的k进行equal。
         如果所有的equals方法返回都是false，那么这个新的节点将被添加到链表的末尾。
         如其中有一个equals返回了true，那么这个节点的value将会被覆盖。
  2、map.get(k)实现原理
     (1)先调用k的hashCode()方法得出哈希值，并通过哈希算法转换成数组的下标。
     (2)通过上一步哈希算法转换成数组的下标之后，在通过数组下标快速定位到某个位置上。
        如果这个位置上什么都没有，则返回null。
        如果这个位置上有单向链表，那么它就会拿着K和单向链表上的每一个节点的K进行equals，如果所有equals方法都返回false，则get方法返回null。
        如果其中一个节点的K和参数K进行equals返回true，那么此时该节点的value就是我们要找的value了，get方法最终返回这个要找的value。
  
如果HashMap的大小超过了负载因子(load factor)定义的容量，怎么办？
答：默认的负载因子大小为0.75，也就是说，当一个map填满了75%的bucket时候，和其它集合类(如ArrayList等)一样，
将会创建原来HashMap大小的两倍的bucket数组，来重新调整map的大小，并将原来的对象放入新的bucket数组中。这个过程叫作rehashing，
因为它调用hash方法找到新的bucket位置。
  
ConcurrentHashMap：
   由 Segment(分段锁) 数组结构和 HashEntry 数组结构组成
   Segment(分割片) 是一种重入锁(ReentrantLock)，结构和 HashMap 相似，也是数组和链表结构，每个 ConcurrentHashMap 含有一个 Segment 数组
   在 JDK 1.7 中采用分段锁的方式；JDK 1.8 中采用了 CAS（无锁算法）+ + synchronized + Node + 红黑树，大大缩小了锁的粒度
   HashEntry 则用于存储键值对数据，每个 HashEntry 是一个链表结构的元素
   每个 Segment 含有并守护一个 HashEntry 数组里的元素，当对 HashEntry 数组的数据进行修改时，必须首先获得与它对应的 Segment 锁。
   构造方法：public concurrencyLevel(int initialCapacity, float loadFactor, int concurrencyLevel);
       initialCapacity：初始容量，默认16，也就是 Entry 数量，建议提前预估容量，尽量减少扩容带来的性能损耗
       loadFactor：每个 segment 的加载因子，默认0.75，当元素个数大于 loadFactor * 最大容量时就需要 rehash，扩容
       concurrencyLevel: 并发级别，默认16，允许最多多少线程修改这个 map，不能超过规定的最大 Segment 的个数(16);
   在默认情况下， ssize = 16，initialCapacity = 16，loadFactor = 0.75f，那么 cap = 1，threshold = (int) cap * loadFactor = 0。
       
Hashtable
    Hashtable 容器使用 synchronized 来保证线程安全，如果线程竞争激烈，需要同时访问该同步方法时，
    会进入阻塞或轮询状态，导致 Hashtable 性能低下(比如线程1用 put 添加元素，线程2就不能 put， 甚至 get 元素)
    
HashMap 和 Hashtable 有什么区别？ 
      ①、HashMap 是线程不安全的，Hashtable 是线程安全的； 
      ②、由于线程安全，所以 Hashtable 的效率比不上 HashMap； 
      ③、HashMap 最多只允许一条记录的键为 null，允许多条记录的值为 null， 而 Hashtable 不允许 key/value 为 null；
      ④、HashMap 默认初始化数组的大小为 16，Hashtable 为 11，前者扩容时，扩大两倍，后者扩大两倍+1；
      ⑤、HashMap 需要重新计算 hash 值，而 Hashtable 直接使用对象的 hashCode

ConcurrentHashMap 和 Hashtable 的区别：
      1、同样能保证线程安全；
      2、ConcurrentHashMap 在 JDK 1.7 中采用分段锁的方式；
          JDK 1.8 中采用了 CAS（无锁算法）+ + synchronized + Node + 红黑树，大大缩小了锁的粒度
         Hashtable 使用 synchronized 保证线程安全；
      3、加锁机制的不同：ConcurrentHashMap 效率更高，由于 Hashtable 使用一把锁处理并发问题，多个线程竞争时，容易阻塞
        
HashMap 和 ConcurrentHashMap 的区别
     ConcurrentHashMap 加锁保证了线程安全，HashMap 无加锁机制；
     HashMap 允许键值对有 null，ConcurrentHashMap 不允许!
     
SynchronizedMap 和 ConcurrentHashMap 的区别？
     SynchronizedMap 一次锁住整张表来保证线程安全，所以每次只能有一个线 程来访为 map。 
     ConcurrentHashMap 使用分段锁来保证在多线程下的性能。

【扩展】为什么 ConcurrentHashMap 在 JDK 1.8 中使用内置锁 synchronized 替代了重入锁 ReentrantLock？
     1、JVM 开发团队在 synchronized 上做了大量的优化；
     2、在大量数据操作下，对于 JVM 的内存压力，ReentrantLock 会开销更多的内存。
     

   