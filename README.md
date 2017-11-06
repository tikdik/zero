### base
它是一个基于springmvc + mybatis + redis为核心的模板。
同时拥有如下功能
 - druid数据库连接池
 - liquibase数据库迁移工具
 - swagger在线文档
 - jetty快速调试部署
 
### async
它在base的基础上加入了rabbitmq消息队列

### async_handler
它是async的异步消息处理程序。
基于srping + mybatis + rabbitmq
