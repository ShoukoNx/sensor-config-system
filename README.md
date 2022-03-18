## 传感器配置系统

### 简介
来源于硕士实验室项目，临毕业再给团队出一份力。通过Swing和MySQL实现对零部件、测量量和传感器表的CRUD，并带有用户权限控制功能。

### 使用方法
系统分为四个模块
- 转向架零部件重要度分析
- 测量量分析
- 传感器配置
- 账户管理

前三个模块分别实现对相应表的CRUD，账户管理中实现账户注册、删除和密码的修改。
系统设置有唯一管理员账户admin，只有admin可以注册和删除账户

### 开发环境配置说明

1.  在Mysql中手动创建数据库 sensor ，设置字符集为 utf8mb4，排序规则 utf8mb4_general_ci

2.  将 sensor.sql 导入数据库中

3. 若用IDEA打开工程后，出现有import包找不到，则在IDEA中的　File -> Project Structure -> Libraires中，加入工程lib目录中的包

4.  将sensor.component.Database类中的mysql用户名和密码修改为与本机的一致

5.  工程的启动主类是sensor.Main