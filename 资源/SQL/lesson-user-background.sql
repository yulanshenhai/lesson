# database

create database lesson character set utf8mb4;
use lesson;

# table

create table if not exists lesson.banner
(
    id          int(11) unsigned auto_increment comment '轮播图表主键',
    url         varchar(256)  not null default '' comment '轮播图跳转地址',
    src         varchar(256)  not null default '' comment '轮播图图片地址',
    weight      tinyint(2)    not null default 0 comment '数字越小排越前',
    info        varchar(1024) not null default '' comment '轮播图描述',
    create_time datetime      not null default now() comment '首次创建时间',
    last_modify datetime      not null default now() comment '最后修改时间',
    primary key (id),
    unique (src)
) comment '轮播图表';

create table if not exists lesson.user
(
    id          int(11) unsigned auto_increment comment '用户表主键',
    username    varchar(128)  not null default '' comment '用户账号',
    password    varchar(128)  not null default '' comment '用户密码',
    real_name   varchar(128)  not null default '' comment '用户姓名',
    nick_name   varchar(128)  not null default '' comment '用户昵称',
    gender      tinyint(2)    not null default 0 comment '用户性别，0女，1男，2保密',
    age         tinyint(3)    not null default 0 comment '用户年龄',
    id_card     char(18)      not null default 0 comment '身份证号',
    avatar      varchar(256)  not null default '' comment '头像图片地址',
    phone       char(11)      not null default '' comment '手机号',
    info        varchar(1024) not null default '' comment '用户描述',
    create_time datetime      not null default now() comment '首次创建时间',
    last_modify datetime      not null default now() comment '最后修改时间',
    primary key (id),
    unique (username),
    unique (phone),
    unique (id_card),
    unique (nick_name)
) comment '用户表';

create table if not exists lesson.video
(
    id            int(11) unsigned auto_increment comment '视频表主键',
    title         varchar(128)  not null default '' comment '视频标题',
    author        varchar(128)  not null default '' comment '视频作者',
    info          varchar(1024) not null default '' comment '视频描述',
    summary_image varchar(256)  not null default '' comment '视频摘要图',
    cover_image   varchar(256)  not null default '' comment '视频封面图',
    price         decimal(8, 2) not null default 0.00 comment '视频单价，单位元',
    star          tinyint(2)    not null default 0 comment '视频评分，默认0星，最高5星',
    create_time   datetime      not null default now() comment '首次创建时间',
    last_modify   datetime      not null default now() comment '最后修改时间',
    primary key (id),
    unique (title)
) comment '视频表';

create table if not exists lesson.chapter
(
    id             int(11) unsigned auto_increment comment '视频章表主键',
    title          varchar(128)     not null default '' comment '视频章标题',
    info           varchar(1024)    not null default '' comment '视频章描述',
    video_id       int(11) unsigned not null default 0 comment 'video表外键',
    index_in_video tinyint(4)       not null default 0 comment '视频内第几章',
    create_time    datetime         not null default now() comment '首次创建时间',
    last_modify    datetime         not null default now() comment '最后修改时间',
    primary key (id),
    unique (title),
    foreign key (video_id) references lesson.video (id)
) comment '视频-章表';

create table if not exists lesson.episode
(
    id               int(11) unsigned auto_increment comment '视频集表主键',
    title            varchar(128)     not null default '' comment '集的标题',
    info             varchar(1024)    not null default '' comment '视频章描述',
    cover_image      varchar(256)     not null default '' comment '视频媒体封面图',
    url              varchar(256)     not null default '' comment '视频媒体播放地址',
    chapter_id       int(11) unsigned not null default 0 comment 'chapter表外键',
    index_in_chapter tinyint(4)       not null default 0 comment '章内第几集',
    create_time      datetime         not null default now() comment '集的创建时间',
    last_modify      datetime         not null default now() comment '最后修改时间',
    primary key (id),
    unique (title),
    unique (url),
    foreign key (chapter_id) references lesson.chapter (id)
) comment '视频-集表';

create table if not exists lesson.`order`
(
    id          int(11) unsigned auto_increment comment '订单表主键',
    number      varchar(64)   not null default '' comment '订单号',
    state       tinyint(2)    not null default 0 comment '0未支付，1已支付',
    total_fee   decimal(8, 2) not null default 0.00 comment '支付总金额',
    info        varchar(1024) not null default '' comment '订单描述',
    create_time datetime      not null default now() comment '首次创建时间',
    last_modify datetime      not null default now() comment '最后修改时间',
    primary key (id),
    unique (number)
) comment '订单表';

create table if not exists lesson.video_order
(
    id          int(11) unsigned auto_increment comment '视频订单中间表主键',
    order_id    int(11) unsigned not null default 0 comment 'order表外键',
    video_id    int(11) unsigned not null default 0 comment 'video表外键',
    user_id     int(11) unsigned not null default 0 comment 'user表外键',
    info        varchar(1024)    not null default '' comment '视频订单中间表描述',
    create_time datetime         not null default now() comment '首次创建时间',
    last_modify datetime         not null default now() comment '最后修改时间',
    primary key (id),
    foreign key (order_id) references lesson.order (id),
    foreign key (video_id) references lesson.video (id),
    foreign key (user_id) references lesson.user (id)
) comment '视频订单中间表';

# data

insert into lesson.banner (id, url, src, weight, info)
values (1, 'video', 'banner01.jpg', 2, 'banner01描述'),
       (2, 'video', 'banner02.jpg', 1, 'banner02描述'),
       (3, 'video', 'banner03.jpg', 3, 'banner03描述');
commit;

insert into lesson.video (id, title, summary_image, cover_image, author, price, star)
values (01, 'JB0-0-新手村', 'JB0-0-summary.jpg', 'JB0-0-cover.jpg', '赵四', 1000.00, 1),
       (02, 'JB1-1-基础入门', 'JB1-1-summary.jpg', 'JB1-1-cover.jpg', '刘能', 2000.00, 2),
       (03, 'JB1-2-数据结构', 'JB1-2-summary.jpg', 'JB1-2-cover.jpg', '王云', 3000.00, 3),
       (04, 'JB1-3-JVM虚拟机', 'JB1-3-summary.jpg', 'JB1-3-cover.jpg', '谢广坤', 4000.00, 4),
       (05, 'JB1-4-多线程', 'JB1-4-summary.jpg', 'JB1-4-cover.jpg', '王小蒙', 5000.00, 5),
       (06, 'JB1-5-设计模式', 'JB1-5-summary.jpg', 'JB1-5-cover.jpg', '王老七', 6000.00, 1),
       (07, 'JB1-6-NIO流', 'JB1-6-summary.jpg', 'JB1-6-cover.jpg', '刘英', 7000.00, 2),
       (08, 'JB1-7-Socket网络编程', 'JB1-7-summary.jpg', 'JB1-7-cover.jpg', '谢飞机', 8000.00, 3),
       (09, 'JB2-0-Linux系统', 'JB2-0-summary.jpg', 'JB2-0-cover.jpg', '二丫', 9000.00, 4),
       (10, 'JB2-1-MySQL数据库', 'JB2-1-summary.jpg', 'JB2-0-cover.jpg', '赵玉田', 10000.00, 5),
       (11, 'JB2-2-JDBC中间件', 'JB2-2-summary.jpg', 'JB2-2-cover.jpg', '苏玉红', 11000.00, 1),
       (12, 'JB2-3-Servlet', 'JB2-3-summary.jpg', 'JB2-3-cover.jpg', '王长贵', 12000.00, 2),
       (13, 'JB2-4-H5C3JS6前端', 'JB2-4-summary.jpg', 'JB2-4-cover.jpg', '刘志', 13000.00, 3),
       (14, 'JB3-1-MyBatis框架', 'JB3-1-summary.jpg', 'JB3-1-cover.jpg', '张中维', 14000.00, 4),
       (15, 'JB3-2-Spring框架', 'JB3-2-summary.jpg', 'JB3-2-cover.jpg', '李成', 15000.00, 5),
       (16, 'JB3-3-SpringMVC框架', 'JB3-3-summary.jpg', 'JB3-3-cover.jpg', '王大拿', 16000.00, 1),
       (17, 'JB3-4-Bootstrap前端框架', 'JB3-4-summary.jpg', 'JB3-4-cover.jpg', '王木生', 17000.00, 2),
       (18, 'JB4-1-SpringBoot框架', 'JB4-1-summary.jpg', 'JB4-1-cover.jpg', '王天来', 18000.00, 3),
       (19, 'JB4-2-Redis消息中间件', 'JB4-2-summary.jpg', 'JB4-2-cover.jpg', '谢永强', 19000.00, 4),
       (20, 'JB4-3-ElasticSearch搜索引擎', 'JB4-3-summary.jpg', 'JB4-3-cover.jpg', '宋青莲', 20000.00, 5),
       (21, 'JB4-4-Nginx服务器', 'JB4-4-summary.jpg', 'JB4-4-cover.jpg', '刘一水', 21000.00, 1),
       (22, 'JB4-5-Vue框架', 'JB4-5-summary.jpg', 'JB4-5-cover.jpg', '宋晓峰', 22000.00, 2),
       (23, 'JB5-1-项目搭建Alibaba', 'JB5-1-summary.jpg', 'JB5-1-cover.jpg', '宋富贵', 23000.00, 3),
       (24, 'JB5-2-注册中心Nacos', 'JB5-2-summary.jpg', 'JB5-2-cover.jpg', '李银萍', 24000.00, 4),
       (25, 'JB5-3-远程调用Feign', 'JB5-3-summary.jpg', 'JB5-3-cover.jpg', '陈艳南', 25000.00, 5),
       (26, 'JB5-4-服务哨兵Sentinel', 'JB5-4-summary.jpg', 'JB5-4-cover.jpg', '杜小双', 26000.00, 1),
       (27, 'JB5-5-服务网关Gateway', 'JB5-5-summary.jpg', 'JB5-5-cover.jpg', '杨晓燕', 27000.00, 2),
       (28, 'JB5-6-链路追踪Sleuth', 'JB5-6-summary.jpg', 'JB5-6-cover.jpg', '谢小梅', 28000.00, 3),
       (29, 'JB5-7-消息队列MessageQueue', 'JB5-7-summary.jpg', 'JB5-7-cover.jpg', '谢兰', 29000.00, 4),
       (30, 'JB5-8-配置中心NacosConfig', 'JB5-8-summary.jpg', 'JB5-8-cover.jpg', '皮长山', 30000.00, 5),
       (31, 'JB5-9-分布式事务Seata', 'JB5-9-summary.jpg', 'JB5-9-cover.jpg', '李大国', 31000.00, 1);
commit;

insert into lesson.chapter (id, title, video_id, index_in_video)
values (01, 'Java学前准备', 1, 1),
       (02, 'Java环境搭建', 1, 2),
       (03, '集成开发环境', 1, 3),
       (04, '使用Maven工具', 1, 4),
       (05, '使用Git和GitEE', 1, 5),
       (06, 'JavaSE-基础语法', 2, 1),
       (07, 'JavaSE-流程控制', 2, 2),
       (08, 'JavaSE-线性结构', 2, 3),
       (09, 'JavaSE-面向对象', 2, 4),
       (10, 'JavaSE-异常例外', 2, 5),
       (11, 'JavaSE-泛型容器', 2, 6),
       (12, 'JavaSE-反射机制', 2, 7),
       (13, 'JavaSE-流类操作', 2, 8),
       (14, 'JavaSE-常用工具', 2, 9);
commit;

insert into lesson.episode (id, title, cover_image, url, chapter_id, index_in_chapter)
values (01, '我为何选择Java', 'EP1-1-cover.jpg', 'EP1-1.mp4', 1, 1),
       (02, '我该如何学Java', 'EP1-2-cover.jpg', 'EP1-2.mp4', 1, 2),
       (03, '何时开始学Java', 'EP1-3-cover.jpg', 'EP1-3.mp4', 1, 3),
       (04, '安装JDK和JRE', 'EP2-1-cover.jpg', 'EP2-1.mp4', 2, 1),
       (05, '配置Path变量', 'EP2-2-cover.jpg', 'EP2-2.mp4', 2, 2),
       (06, '配置JAVA_HOME变量', 'EP2-3-cover.jpg', 'EP2-3.mp4', 2, 3),
       (07, '开发HelloWorld程序', 'EP2-4-cover.jpg', 'EP2-4.mp4', 2, 4),
       (08, '详解HelloWorld代码', 'EP2-5-cover.jpg', 'EP2-5.mp4', 2, 5),
       (09, 'IDEA环境安装', 'EP3-1-cover.jpg', 'EP3-1.mp4', 3, 1),
       (10, 'IDEA创建项目', 'EP3-2-cover.jpg', 'EP3-2.mp4', 3, 2),
       (11, 'IDEA基础配置', 'EP3-3-cover.jpg', 'EP3-3.mp4', 3, 3),
       (12, 'IDEA插件安装', 'EP3-4-cover.jpg', 'EP3-4.mp4', 3, 4),
       (13, '安装Maven工具', 'EP4-1-cover.jpg', 'EP4-1.mp4', 4, 1),
       (14, '搭建Maven仓库', 'EP4-2-cover.jpg', 'EP4-2.mp4', 4, 2),
       (15, '整合Maven到IDEA', 'EP4-3-cover.jpg', 'EP4-3.mp4', 4, 3),
       (16, '创建Maven父项目', 'EP4-4-cover.jpg', 'EP4-4.mp4', 4, 4),
       (17, '创建Maven子项目', 'EP4-5-cover.jpg', 'EP4-5.mp4', 4, 5),
       (18, '使用Junit单元测试', 'EP4-6-cover.jpg', 'EP4-6.mp4', 4, 6),
       (19, '三方依赖配置详解', 'EP4-7-cover.jpg', 'EP4-7.mp4', 4, 7),
       (20, 'Git存储分区介绍', 'EP5-1-cover.jpg', 'EP5-1.mp4', 5, 1),
       (21, '安装并整合Git服务端', 'EP5-2-cover.jpg', 'EP5-2.mp4', 5, 2),
       (22, '整合GitEE代码托管中心', 'EP5-3-cover.jpg', 'EP5-3.mp4', 5, 3),
       (23, '赵四分享项目到GitEE', 'EP5-4-cover.jpg', 'EP5-4.mp4', 5, 4),
       (24, '赵四邀请刘能加入团队', 'EP5-5-cover.jpg', 'EP5-5.mp4', 5, 5),
       (25, '刘能克隆项目到本地', 'EP5-6-cover.jpg', 'EP5-6.mp4', 5, 6),
       (26, '刘能修改代码并Push', 'EP5-7-cover.jpg', 'EP5-7.mp4', 5, 7),
       (27, '文档化编程思想', 'EP6-1-cover.jpg', 'EP6-1.mp4', 6, 1),
       (28, '输出与特殊字符', 'EP6-2-cover.jpg', 'EP6-2.mp4', 6, 2),
       (29, '常量与特殊进制', 'EP6-3-cover.jpg', 'EP6-3.mp4', 6, 3),
       (30, '变量与代码沼泽', 'EP6-4-cover.jpg', 'EP6-4.mp4', 6, 4),
       (31, '基本数据的类型', 'EP6-5-cover.jpg', 'EP6-5.mp4', 6, 5),
       (32, '引用数据的类型', 'EP6-6-cover.jpg', 'EP6-6.mp4', 6, 6),
       (33, '正则表达式专题', 'EP6-7-cover.jpg', 'EP6-7.mp4', 6, 7),
       (34, '虚拟机内存分布', 'EP6-8-cover.jpg', 'EP6-8.mp4', 6, 8),
       (35, '八大类型包装类', 'EP6-9-cover.jpg', 'EP6-9.mp4', 6, 9),
       (36, '常用运算符专题', 'EP6-10-cover.jpg', 'EP6-10.mp4', 6, 10),
       (37, '选择结构', 'EP7-1-cover.jpg', 'EP7-1.mp4', 7, 1),
       (38, '循环结构', 'EP7-2-cover.jpg', 'EP7-2.mp4', 7, 2),
       (39, '流控关键字', 'EP7-3-cover.jpg', 'EP7-3.mp4', 7, 3),
       (40, '一维数组', 'EP8-1-cover.jpg', 'EP8-1.mp4', 8, 1),
       (41, '二维数组', 'EP8-2-cover.jpg', 'EP8-2.mp4', 8, 2),
       (42, '栈结构', 'EP8-3-cover.jpg', 'EP8-3.mp4', 8, 3),
       (43, '队列结构', 'EP8-4-cover.jpg', 'EP8-4.mp4', 8, 4),
       (44, '位组结构', 'EP8-5-cover.jpg', 'EP8-5.mp4', 8, 5),
       (45, 'OOP之抽象', 'EP9-1-cover.jpg', 'EP9-1.mp4', 9, 1),
       (46, 'OOP之封装', 'EP9-2-cover.jpg', 'EP9-2.mp4', 9, 2),
       (47, '属性和方法', 'EP9-3-cover.jpg', 'EP9-3.mp4', 9, 3),
       (48, '初始化代码', 'EP9-4-cover.jpg', 'EP9-4.mp4', 9, 4),
       (49, 'Lombok工具', 'EP9-5-cover.jpg', 'EP9-5.mp4', 9, 5),
       (50, 'OOP之继承', 'EP9-6-cover.jpg', 'EP9-6.mp4', 9, 6),
       (51, 'OOP之多态', 'EP9-7-cover.jpg', 'EP9-7.mp4', 9, 7),
       (52, '动态绑定', 'EP9-8-cover.jpg', 'EP9-8.mp4', 9, 8),
       (53, '特殊类之抽象类', 'EP9-9-cover.jpg', 'EP9-9.mp4', 9, 9),
       (54, '特殊类之接口类', 'EP9-10-cover.jpg', 'EP9-10.mp4', 9, 10),
       (55, 'Lambda表达式', 'EP9-11-cover.jpg', 'EP9-11.mp4', 9, 11),
       (56, '特殊类之枚举类', 'EP9-12-cover.jpg', 'EP9-12.mp4', 9, 12),
       (57, '特殊类之内部类', 'EP9-13-cover.jpg', 'EP9-13.mp4', 9, 13),
       (58, '特殊类之注解类', 'EP9-14-cover.jpg', 'EP9-14.mp4', 9, 14),
       (59, '异常捕获', 'EP10-1-cover.jpg', 'EP10-1.mp4', 10, 1),
       (60, '异常抛出', 'EP10-2-cover.jpg', 'EP10-2.mp4', 10, 2),
       (61, '异常重写', 'EP10-3-cover.jpg', 'EP10-3.mp4', 10, 3),
       (62, '泛型技术', 'EP11-1-cover.jpg', 'EP11-1.mp4', 11, 1),
       (63, 'List序列容器', 'EP11-2-cover.jpg', 'EP11-2.mp4', 11, 2),
       (64, 'Set集合容器', 'EP11-3-cover.jpg', 'EP11-3.mp4', 11, 3),
       (65, 'Map映射容器', 'EP11-4-cover.jpg', 'EP11-4.mp4', 11, 4),
       (66, '迭代器', 'EP11-5-cover.jpg', 'EP11-5.mp4', 11, 5),
       (67, '反射构造器', 'EP12-1-cover.jpg', 'EP12-1.mp4', 12, 1),
       (68, '反射成员属性', 'EP12-2-cover.jpg', 'EP12-2.mp4', 12, 2),
       (69, '反射成员方法', 'EP12-3-cover.jpg', 'EP12-3.mp4', 12, 3),
       (70, '反射注解', 'EP12-4-cover.jpg', 'EP12-4.mp4', 12, 4),
       (71, '反射方法参数的泛型', 'EP12-5-cover.jpg', 'EP12-5.mp4', 12, 5),
       (72, '动态编译', 'EP12-6-cover.jpg', 'EP12-6.mp4', 12, 6),
       (73, '动态运行', 'EP12-7-cover.jpg', 'EP12-7.mp4', 12, 7),
       (74, 'File', 'EP13-1-cover.jpg', 'EP13-1.mp4', 13, 1),
       (75, 'RandomAccessFile', 'EP13-2-cover.jpg', 'EP13-2.mp4', 13, 2),
       (76, '文件流', 'EP13-3-cover.jpg', 'EP13-3.mp4', 13, 3),
       (77, '字节数组流', 'EP13-4-cover.jpg', 'EP13-4.mp4', 13, 4),
       (78, '缓冲流', 'EP13-5-cover.jpg', 'EP13-5.mp4', 13, 5),
       (79, '转换流', 'EP13-6-cover.jpg', 'EP13-6.mp4', 13, 6),
       (80, '打印流', 'EP13-7-cover.jpg', 'EP13-7.mp4', 13, 7),
       (81, '数据流', 'EP13-8-cover.jpg', 'EP13-8.mp4', 13, 8),
       (82, '对象流', 'EP13-9-cover.jpg', 'EP13-9.mp4', 13, 9),
       (83, '字符串工具类', 'EP14-1-cover.jpg', 'EP14-1.mp4', 14, 1),
       (84, '数组工具类', 'EP14-2-cover.jpg', 'EP14-2.mp4', 14, 2),
       (85, '日期工具类', 'EP14-3-cover.jpg', 'EP14-3.mp4', 14, 3),
       (86, '文件工具类', 'EP14-4-cover.jpg', 'EP14-4.mp4', 14, 4),
       (87, 'Optional工具类', 'EP14-5-cover.jpg', 'EP14-5.mp4', 14, 5);
commit;

# views

create view lesson.video_order_detail as
(
SELECT VO.ID          VO_ID,
       VO.ORDER_ID,
       VO.VIDEO_ID,
       VO.USER_ID,
       VO.INFO        VO_INFO,
       VO.CREATE_TIME VO_CREATE_TIME,
       VO.LAST_MODIFY VO_LAST_MODIFY,
       U.ID           U_ID,
       U.USERNAME,
       U.PASSWORD,
       U.REAL_NAME,
       U.NICK_NAME,
       U.GENDER,
       U.AGE,
       U.ID_CARD,
       U.AVATAR,
       U.PHONE,
       U.INFO         U_INFO,
       U.CREATE_TIME  U_CREATE_TIME,
       U.LAST_MODIFY  U_LAST_MODIFY,
       V.ID           V_ID,
       V.TITLE,
       V.AUTHOR,
       V.INFO         V_INFO,
       V.SUMMARY_IMAGE,
       V.COVER_IMAGE,
       V.PRICE,
       V.STAR,
       V.CREATE_TIME  V_CREATE_TIME,
       V.LAST_MODIFY  V_LAST_MODIFY,
       O.ID           O_ID,
       O.NUMBER,
       O.STATE,
       O.TOTAL_FEE,
       O.INFO         O_INFO,
       O.CREATE_TIME  O_CREATE_TIME,
       O.LAST_MODIFY  O_LAST_MODIFY
FROM lesson.video_order VO
         LEFT JOIN lesson.user U ON VO.USER_ID = U.ID
         LEFT JOIN lesson.video V ON VO.VIDEO_ID = V.ID
         LEFT JOIN lesson.order O ON VO.ORDER_ID = O.ID
    );

create view lesson.video_detail as
(
SELECT V.ID             V_ID,
       V.TITLE          V_TITLE,
       V.AUTHOR,
       V.INFO           V_INFO,
       V.SUMMARY_IMAGE,
       V.COVER_IMAGE    V_COVER_IMAGE,
       V.PRICE,
       V.STAR,
       V.CREATE_TIME    V_CREATE_TIME,
       V.LAST_MODIFY    V_LAST_MODIFY,
       C.ID             C_ID,
       C.TITLE          C_TITLE,
       C.INFO           C_INFO,
       C.VIDEO_ID       C_VIDEO_ID,
       C.INDEX_IN_VIDEO C_INDEX_IN_VIDEO,
       C.CREATE_TIME    C_CREATE_TIME,
       C.LAST_MODIFY    C_LAST_MODIFY,
       E.ID             E_ID,
       E.TITLE          E_TITLE,
       E.INFO           E_INFO,
       E.COVER_IMAGE    E_COVER_IMAGE,
       E.URL,
       E.CHAPTER_ID,
       E.INDEX_IN_CHAPTER,
       E.CREATE_TIME    E_CREATE_TIME,
       E.LAST_MODIFY    E_LAST_MODIFY
FROM lesson.video V
         LEFT JOIN lesson.chapter C ON V.ID = C.VIDEO_ID
         LEFT JOIN lesson.episode E ON C.ID = E.CHAPTER_ID
    );

# trigger

-- user表的删除记录日志表
create table if not exists lesson.user_delete_log
(
    id              int(11) unsigned auto_increment comment '日志表主键',
    operation_user  varchar(128) comment '本次操作的MySQL用户',
    operation_info  varchar(512) comment '本次操作的信息',
    operation_time  datetime comment '本次操作的时间',
    old_id          int(11) unsigned comment '原始用户表主键',
    old_username    varchar(128)  not null default '' comment '原始用户账号',
    old_password    varchar(128)  not null default '' comment '原始用户密码',
    old_real_name   varchar(128)  not null default '' comment '原始用户姓名',
    old_nick_name   varchar(128)  not null default '' comment '原始用户昵称',
    old_gender      tinyint(2)    not null default 0 comment '原始用户性别，0保密，1男，2女',
    old_age         tinyint(3)    not null default 0 comment '原始用户年龄',
    old_id_card     char(18)      not null default 0 comment '原始身份证号',
    old_avatar      varchar(256)  not null default '' comment '原始头像图片地址',
    old_phone       char(11)      not null default '' comment '原始手机号',
    old_info        varchar(1024) not null default '' comment '原始用户描述',
    old_create_time datetime      not null default now() comment '原始首次创建时间',
    old_last_modify datetime      not null default now() comment '原始最后修改时间',
    primary key (id)
) comment 'user表的删除记录表';

-- user表的行级触发器：当删除行记录时触发
drop trigger if exists lesson.user_delete_trigger;

-- 从此处开始，使用 `$$` 符号替换 `;` 以表示结束，以规避CMD报错
delimiter $$

-- 创建触发器：当lesson.user表中的某行记录被删除后，向lesson.user_delete_log中备份数据
create trigger lesson.user_delete_trigger
    after delete
    on lesson.user
    for each row
begin
    insert into lesson.user_delete_log (operation_user, operation_info, operation_time, old_id,
                                        old_username, old_password, old_real_name, old_nick_name,
                                        old_gender, old_age, old_id_card, old_avatar, old_phone,
                                        old_info, old_create_time, old_last_modify)
    values (current_user(), concat('删除了id为', OLD.id, '的记录'), now(),
            OLD.id,
            OLD.username, OLD.password, OLD.real_name, OLD.nick_name,
            OLD.gender, OLD.age, OLD.id_card, OLD.avatar, OLD.phone,
            OLD.info, OLD.create_time, OLD.last_modify);
end $$

delimiter ;
-- 到此处结束，恢复使用 `;` 符号替换 `$$` 以表示结束，以规避后续命令失效
