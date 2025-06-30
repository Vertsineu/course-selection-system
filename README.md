# course-selection-system

## 介绍

这是 USTC 24 春季学期的《程序设计进阶与实践》课程的大作业，一个前后端分离的，前端使用原生 html + css + javascript，后端基于 Spring Boot + MyBatis + MySQL 的学生选课系统。

## 特点

- 实现基本的学生身份认证功能，通过后端导入的学生数据进行登录和鉴权。
- 实现学生选课，退课，查看已选课程等功能，保证选课时间不能冲突，并提供每周课表展示。
- 导入了 USTC 23 秋季学期的全校开课数据，模拟真实的一学期的课程选课功能。

## 前置

- jdk >= 17
- MySQL >= 5.7
- Maven >= 3.6

## 运行

首先 clone 本项目到本地：

```bash
git clone https://github.com/Vertsineu/course-selection-system.git
cd course-selection-system
```

然后添加 `css_dev` 用户到 MySQL 数据库中：

```bash
mysql -u root -p < init.sql
```

如果是通过 docker 启用的 MySQL 数据库，比如：

```bash
docker run -itd --name mysql-test -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql
```

或者远程启用的 MySQL 数据库，则需指定目的 IP 地址：

```bash
mysql -h 127.0.0.1 -u root -p < init.sql
```

接下来，使用 Maven 编译项目：

```bash
mvn compile
```

为确保正确初始化数据库，首先修改 `src/main/resources/conf/mysql.yml` 中最后一行的 `spring.sql.mode` 字段的值为 `always`：

```yaml
spring:
    ...
    sql:
        ...
        mode: always
```

然后运行以下命令添加一个测试用户：

```bash
mvn -Dtest=CourseSelectionSystemApplicationTests#addUser test
```

该过程会导入 USTC 23 秋季学期的全校开课数据，并添加测试用户，可能需要运行几分钟时间，请耐心等待。

接着，为了防止重复初始化数据库，在 `src/main/resources/conf/mysql.yml` 中将 `spring.sql.mode` 字段的值改回 `never`：

```yaml
spring:
    ...
    sql:
        ...
        mode: never
```

最后，运行以下命令运行该项目：

```bash
mvn spring-boot:run
```

如果运行过程中报错，显示端口已被占用，可以修改 `src/main/resources/application.yml` 中的 `server.port` 字段：

```yaml
server:
  port: 8000
```

## 使用

浏览器中输入 `localhost:8000` 即可访问前端页面，用户名 `username`，密码 `password`，登录后即可使用系统。
