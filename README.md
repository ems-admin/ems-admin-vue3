# ems-admin-boot

#### 介绍
ems-admin(easy manage system)是一套极简的后台管理系统，
基于SpringBoot2.5.2、SpringSecurity、Mybatis-plus3.4.3.4、Mysql8.0、
JWT、Vue3.0、Layui2.6.8（或element-plus）开发，
只提供最基础的登录、权限管理功能与日志功能，所有的业务功能，
都可以根据自己的需要在此之上构建。

ems-admin-boot是ems-admin后台的SpringBoot版本，
前端分为基于Layui开发ems-admin-layui和基于element-plus开发的ems-admin-element-plus


[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/ems-admin/ems-admin-boot/blob/master/LICENSE)
[![star](https://gitee.com/ems-admin/ems-admin-boot/badge/star.svg?theme=white)](https://gitee.com/ems-admin/ems-admin-boot)
[![GitHub stars](https://img.shields.io/github/stars/ems-admin/ems-admin-boot.svg?style=social&label=Stars)](https://github.com/ems-admin/ems-admin-boot)
[![GitHub forks](https://img.shields.io/github/forks/ems-admin/ems-admin-boot.svg?style=social&label=Fork)](https://github.com/ems-admin/ems-admin-boot)

[ems-admin-layui演示站点](http://ems-admin-layui.facebook47.cn/)

[ems-admin-element-plus演示站点](http://ems-admin-element.facebook47.cn/)

#### 支持功能

-  登录
   - 账号/密码 admin/123456

   
-  用户管理
   - 添加、编辑、删除、停用/启用、查询
   
   
-  菜单管理
   - 添加、编辑、删除、查询

   
-  角色管理
   - 添加、编辑、删除、授权、查询

   
-  日志管理
   - 查询


#### 交流反馈

- ems-admin-layui技术群: `211296170`


- [issues 问题反馈](https://github.com/ems-admin/ems-admin-boot/issues)


- [更新日志](CHANGELOG.md)


#### 软件架构

- 登录和访问控制通过SpringSecurity 和 JWT来完成


- 用户的访问权限，通过用户-角色-权限的方式进行绑定


#### 后续计划

- 提供验证码功能


- 提供token自动续租功能


- 优化权限的细粒度，目前是通过请求路径进行了校验，还没有精确到按钮


#### 已知问题

- 期待反馈



