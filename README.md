一项目概述
====
    一个基于SSM框架构建的Maven父子工程。项目为一个电商购物项目。项目分为两大部分： 
    后台：对商品信息和广告信息进行管理。
    前台：用户对商品信息进行查看以及完成购物操作。  
二开发环境及相应技术栈
====
    工具    版本信息
    JDK     1.8
    IDE     IDEA 2020.1
    MySql   5.5
    Tomcat  8.5.51
    maven   3.5.2
    redis   2.8.1
    solr    4.10.3
    相关技术栈：SSM框架 redis mybatis分页插件 mybatis逆向工程 solr服务器 cookie httpclient
三项目框架说明
====
    Tatao-manager：后台web，负责对后台商品信息以及广告信息进行管理。也可以对商品和广告信息进行查询。
    Taotao-common：存放一些会被公用的方法及bean对象
    Taotao-portal：不与数据库直接相关，通过调用别的web发布的服务来获取数据
    Taotao-rest：提供前台首页左侧商品信息，及轮播图资源的发布
    Taotao-search：发布搜索功能
    Taotao-sso：发布登录功能
    Taotao-order：发布订单相关的服务
