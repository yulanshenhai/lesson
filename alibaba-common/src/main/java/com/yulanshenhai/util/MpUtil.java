//package com.alibaba.util;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
///**
// * @author yulanshenhai
// */
//public class MpUtil {
//
//    public static void main(String[] args) {
//
//        // 定义代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        // 拼接出代码最终输出的目录
//        gc.setOutputDir("D:\\mp");
//        // 设置生成目录后不打开目录
//        gc.setOpen(false);
//        // 重新生成文件时覆盖原文件
//        gc.setFileOverride(true);
//        // 配置主键生成策略为自增
//        gc.setIdType(IdType.AUTO);
//        // 配置日期类型为java.util.date
//        gc.setDateType(DateType.ONLY_DATE);
//        // 删除默认生成的service的I前缀
//        gc.setServiceName("%sService");
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/alibaba?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("alibaba");
//        dsc.setPassword("alibaba");
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setParent("");
//        mpg.setPackageInfo(pc);
//
//        // ORM映射策略
//        StrategyConfig strategy = new StrategyConfig();
//        // 指定1或N个表名，多个表用逗号分隔
//        strategy.setInclude("user", "product", "order");
//        // 数据库表名（下划线）映射为实体类名（驼峰）
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        // 数据库字段名（下划线）映射为实体类属性名（驼峰）
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        // 使用lombok模型生成实体类
//        strategy.setEntityLombokModel(true);
//        // 对控制类标记rest风格的@RestController注解
//        strategy.setRestControllerStyle(true);
//        // 配置路由值使用驼峰转连字符
//        strategy.setControllerMappingHyphenStyle(true);
//        mpg.setStrategy(strategy);
//
//        // 执行代码生成操作
//        mpg.execute();
//
//        System.out.println("生成完毕");
//    }
//}



package com.yulanshenhai.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

/**
 * @author JoeZhou
 */
public class MpUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/alibaba?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "alibaba";
    private static final String PASSWORD = "alibaba";
    private static final String AUTHOR = "yulanshenhai";
    private static final String OUTPUT_DIR = "D://mp";
    private static final String PARENT_PACKAGE = "com.yulanshenhai";
    private static final String TABLES = "user,order,product";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> builder.author(AUTHOR).dateType(DateType.ONLY_DATE).outputDir(OUTPUT_DIR))
                .packageConfig(builder -> builder.parent(PARENT_PACKAGE))
                .strategyConfig(builder -> {
                    builder.addInclude(TABLES);
                    builder.serviceBuilder().formatServiceFileName("%sService");
                    builder.entityBuilder().enableLombok();
                    builder.controllerBuilder().enableRestStyle().enableHyphenStyle();
                }).execute();
        System.out.println("生成完毕");
    }
}
