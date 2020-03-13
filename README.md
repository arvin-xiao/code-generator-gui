
code-generator-gui是基于[mybatis generator](http://www.mybatis.org/generator/index.html)开发一款界面代码生成工具, 本工具可以使你非常容易及快速生成Mybatis的Java文件及数据库Mapping等文件。

###目标
提高效率，释放生产力，基础代码质量可控，加快产品开发进程

####未来功能
自动生成单元测试，生成UI自动化测试，根据流程图生成代码

### 核心特性
* 按照界面步骤轻松生成代码，省去XML繁琐的学习与配置过程
* 保存数据库连接与Generator配置，每次代码生成轻松搞定
* 内置常用插件，比如分页插件
* 支持OverSSH 方式，通过SSH隧道连接至公司内网访问数据库
* 把数据库中表列的注释生成为Java实体的注释，生成的实体清晰明了
* 可选的去除掉对版本管理不友好的注释，这样新增或删除字段重新生成的文件比较过来清楚
* 目前已经支持Mysql、Mysql8、Oracle、PostgreSQL与SQL Server，暂不对其他非主流数据库提供支持。(MySQL支持的比较好，其他数据库有什么问题可以在issue中反馈)

### 技术栈
```

├─ main
   │  
   ├─ java
 
 ```
####lombok

####JavaFX
中文文档：http://www.javafxchina.net/
https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html

#### mybatis-generator
javaModelGenerator：配置实体类生成器，可以通过配置rootClass属性为实体类指定继承的父类
sqlMapGenerator：配置SQL映射文件生成器
javaClientGenerator：配置dao层接口生成器

####beetl

### 第三方jar
####guava
####easypoi
[文档](https://opensource.afterturn.cn/doc/easypoi.html)

####hutool
小而全的Java工具类库
[Doc文档](https://hutool.cn/docs/#/)
[API文档](https://apidoc.gitee.com/loolly/hutool/ )


### 参考文档
[mybatis generator](https://mybatis.org/generator/apidocs/org/mybatis/generator/internal/DefaultCommentGenerator.html)


### 要求
本工具由于使用了Java 8的众多特性，所以要求JDK <strong>1.8.0.60</strong>以上版本，另外<strong>JDK 1.9</strong>暂时还不支持。
