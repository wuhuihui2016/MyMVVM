# jectpack room 20210617

1、定义实体类Entity：Student.java；
2、定义Dao接口：StudentDao.java；
3、定义Database：StudentDatabase.java
4、定义VM，作为数据更新的联动：StudentViewModel.java
5、实际操作数据UI：RoomActivity.java

TODO 疑问：1、生成数据库文件怎么查看其中数据？
     2、条件查询怎么查询?为何增加条件后查询无果？
     3、RoomDatabase 是对于一个表的配置，如果有其他表时，需要重写RoomDatabase，
        是否也可认为每一个RoomDatabase，都会生成一个新的数据库？