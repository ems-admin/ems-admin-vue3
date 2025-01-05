# ems-admin-boot

#### 介绍
ems-admin(easy manage system)是一套极简的后台管理系统，
基于SpringBoot3.4.1、SpringSecurity6.1.1、Mybatis-plus3.5.9、Mysql8.0、
JWT、Vue3.5.13、element-plus进行开发，
只提供最基础的登录、权限管理功能与日志功能，所有的业务功能，
都可以根据自己的需要在此之上构建。
做到真正的开箱即用.


[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/ems-admin/ems-vue3/blob/master/LICENSE)
[![star](https://gitee.com/ems-admin/ems-vue3/badge/star.svg?theme=white)](https://gitee.com/ems-admin/ems-vue3)
[![GitHub stars](https://img.shields.io/github/stars/ems-admin/ems-vue3.svg?style=social&label=Stars)](https://github.com/ems-admin/ems-vue3)
[![GitHub forks](https://img.shields.io/github/forks/ems-admin/ems-vue3.svg?style=social&label=Fork)](https://github.com/ems-admin/ems-vue3)

[ems-admin演示站点](http://ems.facebook47.cn/)

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


- [issues 问题反馈](https://github.com/ems-admin/ems-vue2/issues)


- [更新日志](CHANGELOG.md)


#### 必应壁纸

- 对必应壁纸感兴趣的，可以访问 [必应壁纸](https://bing.fullpx.com)


#### 软件架构

- 登录和访问控制通过SpringSecurity 和 JWT来完成


- 用户的访问权限，通过用户-角色-权限的方式进行绑定


- 权限管理的细粒度到了按钮级别（前端控制）


- 验证码使用的是用Guava做的本地缓存


#### 后续计划

- 暂无


#### 已知问题

- 期待反馈



