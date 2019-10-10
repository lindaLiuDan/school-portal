# o-owner 这是业主端的api接口

# mybatis-plus封装的方法使用
使用规则：
增加条件只能增加where之后的
group by虽然有封装的方法，但是不推荐
还有关联表查询不能用，只能是当前pojo对应的表

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

@JsonIgnoreProperties(value = {"creatorTime", "editorTime", "gname"})

Assert.isNull(orderId,"订单ID不能为空", ConfigConstant.ERROR);

@NotNull(message = "用户ID不能为空", groups = {AddGroup.class, UpdateGroup.class})

@DataFilter(subDept = true, user = false)

获取业主信息,Redis异常,Exception{},异常信息为:


if(!page.getRecords().isEmpty()){
            try {
                UserInfoEntity userInfoEntity = redisUtils.hget(RedisKeyUtils.UserInfoKyes.USER_INFO+userId,userId,UserInfoEntity.class);

            } catch (Exception e){
                logger.error("获取业主信息,Redis异常,Exception{},异常信息为:", e);
            }
        }
        
UserInfoEntity infoEntity = repairRedisManager.hget(RedisKeyUtils.UserInfoKyes.USER, repair.getUserId().toString(), UserInfoEntity.class,"获取用户信息,Redis异常,Exception{},异常信息为:");
            if(infoEntity==null){
                UserInfoEntity userInfoEntity = userInfoService.getById(repair.getUserId());
                
            }        
        

gt大于,lt小于 
ge 大于等于   
le 小于等于   

# 经常使用的条件有：
.eq("id", 10) 字段，参数 查出对应条件
.ge("id", 10）	字段 参数 包括10 之后的所有
.gt("id",10) 字段 参数	不包括10 之后的所有
.le("id", 10）	字段 参数 包括10 之前的所有
.lt("id",10) 字段 参数	不包括10 之前的所有
.in("id"，值（或者集合或数组）) 和普通in方法相同
.like("name","q")
.between("id",10,15) 字段 开始区间，结束区间 包左包右
.notbetween 不解释 了
.setSqlSelect("age") 查询指定字段
.setSqlSelect("name,age") 指定多个字段（
.where("id={0}",52) 正常应该这样写 但是为了方便 可以忽略第二个参数直接写个sql条件即可
.where("id=52")
.or("id=1")
.and("id=1")
不常用的
.ne("age",20) age 20> 同时 20<
.exists()根据这个条件判断 如果为true 默认返回所有行的所有字段
.group by() 因为group 基本要和函数一起使用 所以不建议用
.having()
还有一些notlike notexists 等很好理解的就没放上去
可以进行一些稍复杂一点的逻辑
如：
查询id字段或者age字段包括1的
.like("id","1").or().like("age","1")
最后是 网上的一些示例
.where("name={0}", "'zhangsan'").and("id=1") .orNew("status={0}", "0").or("status=1")
 .notLike("nlike", "notvalue") .andNew("new=xx").like("hhh", "ddd") .andNew("pwd=11")
 .isNotNull("n1,n2").isNull("n3") .groupBy("x1").groupBy("x2,x3") .having("x1=11")
 .having("x3=433") .orderBy("dd").orderBy("d1,d2");

# TODO 测试redis键值对的字典数据是否正确是否
List<SysDictEntity> dictEntityList = sysDictService.selectList(new EntityWrapper<SysDictEntity>()
                .setSqlSelect("id,type,code,value,name")
                .eq("type", RedisKeys.dict.USER_SEX)
        );
        redisUtils.set(RedisKeys.dict.DICT + RedisKeys.dict.USER_SEX, dictEntityList);
        String userSex = redisUtils.get(RedisKeys.dict.DICT + RedisKeys.dict.USER_SEX);
        logger.info("SEX：" + userSex);        
logger.info("短信接口返回的数据 Code = {},Message = {}, RequestId = {}, BizId = {}", response.getCode(), response.getMessage(), response.getRequestId(), response.getBizId());

#ClearRedisCache 的使用方式
    // 创建/更新时的锁, 用于同步检查唯一性
    private static final Lock UNIQUENESS_CHECK = new ReentrantLock();
    /**
     * 创建礼品信息
     * <p>
     * 清除礼品列表缓存
     *
     * @param giftInfo 礼品信息
     * @throws GiftInfoMgtException 创建失败
     */
    @ClearRedisCache({GIFT_INFO_LIST, EXCLUSIVE_GIFT_LIST})
    @Transactional(rollbackFor = GiftInfoMgtException.class)
    @Override
    public void save(GiftInfo giftInfo) throws GiftInfoMgtException {
        ValidatorUtils.validateEntity(giftInfo);

        if (giftInfo.isExclusive()) {
            final UserLevelEntity level = userLevelDao.queryObject(giftInfo.getLevelId());

            if (level == null) {
                logger.error("等级ID不合法");
                throw new AppRuntimeException("等级ID不合法");
            }
        }
        // 同步确保礼品名称唯一性
        UNIQUENESS_CHECK.lock();
        try {
            final int count = giftInfoDao.countByGname(giftInfo.getGname());
            if (count > 0) {
                throw new GiftInfoMgtException("礼品名称[%s]已存在, 创建失败!", giftInfo.getGname());
            }
            // 创建的时候, 默认为启用
            giftInfo.setDisabled(1);
            final int affectedRow = giftInfoDao.save(giftInfo);
            if (affectedRow != 1) {
                throw new GiftInfoMgtException("创建失败！");
            }
        } finally {
            UNIQUENESS_CHECK.unlock();
        }
    }
    
    
# Spring boot 特定拦截器具体实现和应用场景
1.REST服务中一个常见需求，希望异常时能在响应正文中包含错误的详细信息。
Spring Framework不会自动执行此类操作，因为，响应正文中的错误详情，是特定于应用程序的。
但是，可以通过由@ExceptionHandler标记的类，来处理带有ResponseEntity返回值的@RestController，以此，
来设置响应的状态和主体（body）。这种处理类需要引入@ControllerAdvice注解来声明，然后，在全局范围内应用它。
2.在异常处理中响应主体返回错误详情，全局性实现也可以通过实现ResponseEntityExceptionHandler接口做到，
它提供对Spring MVC引发的异常，以及对定制响应主体的处理。
具体做法是：创建一个实现了ResponseEntityExceptionHandler接口的类，
并引入@ControllerAdvice注解，然后，在实现方法中去做相应的处理，并将其声明为Spring bean（如：使用@Component注解）。

 // 这个是主配置文件中的
    @Value("${cms.test}")
    private String test;
    // 这个则是通过include引入进来的
    @Value("${cms.db.url}")
    private String url;
    

1.函数式接口本质上还是一个接口，但是它是一种特殊的接口：
其实就是通过闭包作为参数定义接口内方法的参数，以及方法体。
一个函数式的接口，必须使用@FunctionalInterface进行修饰。
函数式接口其实就是定义不同参数和返回值类型的闭包。

//(1)定义一个函数式接口 Function<T, R> -T作为输入，返回的R作为输出
@FunctionalInterface
interface TestFunction<T, R> {
    R test(T from);
}

// 使用该接口
 TestFunction<String,String> function = (x) -> {System.out.print(x);return "Function";};
 System.out.println(function.test("hello world"));

//(2) TestFunction<T>  T作为输入，返回的boolean值作为输出
@FunctionalInterface
interface TestFunction<T> {
    boolean test(T from);
}

// 使用该接口.
 TestFunction<String> function = (x) -> {System.out.print(x);return  true;};
 System.out.println(function.test("hello world"));

//(3) TestFunction<T>  没有任何输入，返回T
@FunctionalInterface
interface TestFunction<T> {
    T test();
}

// 使用该接口.
 TestFunction<String> function = () -> {return "Supplier";};
 System.out.println(function.test());

//(4) TestFunction<T, T>  两个T作为输入，返回一个T作为输出，对于“reduce”操作很有用
@FunctionalInterface
interface TestFunction<T, T> {
    T test(T from, T from);
}

// 使用该接口.
 TestFunction<String, String> function =(x, y) -> {System.out.print(x+" "+y);return "AddOperator";};
 System.out.println(function.test("hello ","world"));


# 二、闭包（Lambda表达式）

2.Lambda表达式 也称闭包。
其实和iOS oc 里面的block 和 swift中的 closure 一样，本质都一样，都是匿名函数。

// Arrays.asList 返回的数组不能add 和 remove
 Arrays.asList( "a", "b", "d" ).forEach( e -> System.out.println( e ) );
// 等同于,只是隐藏了String类型
Arrays.asList( "a", "b", "d" ).forEach( ( String e ) -> System.out.println( e ) );

// 如果需要语句块执行多条语句则如下
Arrays.asList( "a", "b", "d" ).forEach( e -> {
    System.out.print( e );
    System.out.print( e );
} );


# 三、Streams使用
// 定义一Task 类
private static class Task {
     private  boolean status;
    Task( boolean status, Integer points ) {
            this.status = status;
            this.points = points;
        }
     public Status getStatus() {
            return status;
        }
     public Integer getPoints() {
            return points;
        }
}

// 使用
Collection< Task > tasks = Arrays.asList(
    new Task( true, 5 ),
    new Task( true, 13 ),
    new Task( false, 8 ) 
);
// 计算status=true总和，mapToInt操作基于每个task实例的Task::getPoints方法将task流转换成Integer集合；
最后，通过sum方法计算总和，得出最后的结果 ：18。
double totalPointsOfOpenTasks = tasks
    .stream()
    .filter( task -> task.getStatus() == Status.OPEN )
    .mapToInt( Task::getPoints )
    .sum();

// 计算所有数总和，并行处理（parallel）。
这里我们使用parallel方法并行处理所有的task，并使用reduce方法计算最终的结果。
double totalPoints = tasks
   .stream()
   .parallel()
   .map( task -> task.getPoints() ) // or map( Task::getPoints ) 
   .reduce( 0, Integer::sum );

//对于一个集合，经常需要根据某些条件对其中的元素分组。利用steam可以快速实现
< Status, List< Task > > map = tasks
    .stream()
    .collect( Collectors.groupingBy( Task::getStatus ) );
System.out.println( map );
控制台的输出如下：
{CLOSED=[[CLOSED, 8]], OPEN=[[OPEN, 5], [OPEN, 13]]}

// 流转换为其它数据结构
String[] strArray1 = stream.toArray(String[]::new);
// 2. Collection
List<String> list1 = stream.collect(Collectors.toList());
List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
Set set1 = stream.collect(Collectors.toSet());
Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
// 3. String
String str = stream.collect(Collectors.joining()).toString();

# reduce 的用
// 字符串连接，concat = "ABCD"
String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat); 
// 求最小值，minValue = -3.0
double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min); 
// 求和，sumValue = 10, 有起始值
int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
// 求和，sumValue = 10, 无起始值
sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
// 过滤，字符串连接，concat = "ace"
concat = Stream.of("a", "B", "c", "D", "e", "F").
 filter(x -> x.compareTo("Z") > 0).
 reduce("", String::concat);


# 数据库进行主从配置步骤：
# 1 给主服务器数据库进行基础配置
vi /etc/my.cnf
server-id=1 #设置主服务器的ID(不能和别的服务器重复，建议使用ip的最后一段)
innodb_flush_log_at_trx_commit=2  #
sync_binlog=1  #开启binlog日志同步功能
log-bin=mysql-bin-5  #binlog日志文件名
binlog-do-db=duoke_pro  # 这个表示只同步某个库 （如果没有此项，表示同步所有的库）
# 2 给从服务器授权账号和权限 从数据库服务器192.168.10.131
mysql> GRANT REPLICATION SLAVE ON *.* to 'gaoshanxi'@'122.114.157.38' identified by 'gaoshanxi'; 不要重启查看服务器的状态
# 3 从配置(slave_mysql配置)
vi /etc/my.cnf
server-id=2
innodb_flush_log_at_trx_commit=2
sync_binlog=1
log-bin=mysql-bin-2
# 4给从服务器进行授权 从的IP对应主的IP
change master to master_host='122.114.157.14', master_user='gaoshanxi' ,master_password='gaoshanxi', master_log_file='mysql-bin-5.000001' ,master_log_pos=154;
mysql> start slave;  ##开启从库   (stop slave：关闭从库）
mysql> show slave status; ###Slave_IO_Running,Slave_SQL_Running 都为Yes的时候表示配置成功
service mysql restart; mysql服务器重启命令
show slave status\G; 主从检查

# 5如果主服务器已经存在应用数据，则在进行主从复制时，需要做以下处理： 这个方法没有试验过待定；
(1)主数据库进行锁表操作，不让数据再进行写入动作
mysql> FLUSH TABLES WITH READ LOCK;
(2)查看主数据库状态
mysql> show master status;
(3)记录下 FILE 及 Position 的值。
将主服务器的数据文件（整个/opt/mysql/data目录）复制到从服务器，建议通过tar归档压缩后再传到从服务器解压。
(4)取消主数据库锁定
mysql> UNLOCK TABLES;
