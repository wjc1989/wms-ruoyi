## 开发

```bash
# 安装依赖
npm install

# 建议No 要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过e.g.下Operate解决 npm 下载速度慢的问题
npm install --registry=https://registry.npmmirror.com

# 启动服务
npm run dev
```

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```
## 后端开Source 项目Address
### gitee
https://gitee.com/zccbbg/wms-ruoyi
### github
https://github.com/zccbbg/wms-ruoyi

## 在线体验
- 演示Address：
- 陆陆续续收到一些打赏，了更好的体验已用于演示服务器升级。谢谢各位小伙伴。

## CSDNWMS技术专栏
- 常见问题：https://blog.csdn.net/qq_27575627/category_12336113.html
- 后端技术：https://blog.csdn.net/qq_27575627/category_12331868.html
- 前端技术：https://blog.csdn.net/qq_27575627/category_12331867.html
- 运维：https://blog.csdn.net/qq_27575627/category_12332546.html

## B站视频介绍
https://www.bilibili.com/video/BV1ys4y1q7uG

## WMSwms简介
WMSwmsYes一套基于WMS的WMS，支持lodop And 网页PrintInbound、Outbound 。毫无保留给个人及企业免费使用。
* 前端采用Vue、Element UI（ant design 正在开发中）。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* Prower认证使用Jwt，支持多终端认证系统。
* 支持加载动态ProwerMenu ，多方式轻松Prower控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。

## WMSwms功能
1. Index：InventoryWarn与到期提醒、基础Data报表展示
2. Warehouse/ Rack：管理维护Warehouse基础Data
3. Item：管理维护Item基础Data
4. Custom/Supplier/ Carrier：管理维护Contacts基础Data
5. Inbound：CreateInbound后包括e.g.下几个Status：未Outbound、在途（已Outbound未Inbound）、部分Inbound、作废、Inbound完成，Type包括：采购Inbound、外协Inbound、退货Inbound，Inbound支持lodop And 网页Print
6. Outbound ：CreateOutbound 后包括e.g.下几个Status：未Outbound、部分Outbound、已Outbound、作废，Type包括：销售Outbound 、外协Outbound 、调拨Outbound ，Outbound 支持lodop And 网页Print
7. Move：CreateMove后包括e.g.下几个Status：未Operate、部分移动、Operate完毕、作废
8. Inventory看板：View当前ItemInventoryCount
9. Inventory Record：View当前ItemInventoryOperate Record
## Status流转
#### Status流转
![Status流转](https://oscimg.oschina.net/oscnet/up-6bdb5ad6d8ab236f763300b71cf175d9a99.jpg)
#### Outbound Status流转
![Outbound Status流转](https://oscimg.oschina.net/oscnet/up-55cad3f077f914e357efeaae0b3feecf942.jpg)

## 关于我们
开发团队成立于2017年，我们前端开发、后端架构，有一颗热爱开Source 的心，致力于打造企业级的通用产品设计UI体系让项目  Or者更直观，更高效、更简，未来将持续关注UI交互，持续推出高质量的交互产品。

## 演示图
![Index](https://oscimg.oschina.net/oscnet/up-89f751967b4145f7da92e23536bf231fbe8.jpg)
![支持两种Print](https://oscimg.oschina.net/oscnet/up-6daf90ef19571c7f0e7641ae59c403d8272.jpg)
![lodopPrint](https://oscimg.oschina.net/oscnet/up-146d2105ae31a27e497323ad19f8bd0d7bd.jpg)
![网页Print](https://oscimg.oschina.net/oscnet/up-5664440042861199d1f3e60928e0700a9ce.jpg)
![Warehouse列表](https://oscimg.oschina.net/oscnet/up-a00eb79bee48e481249a12cb5e6c476aaa3.jpg)
![Inventory看板](https://oscimg.oschina.net/oscnet/up-78990915dfba902384ed4b09e3dc0f0fe05.jpg)

## WMS内置功能

1.  User 管理：User Yes系统Operate者，该功能主要完成系统User 配置。
2.  Department管理：配置系统组织机构（公司、Department、小组），树结构展现支持。
3.  Post管理：配置系统User 担任职务。
4.  Menu 管理：配置系统Menu ，OperatePrower，ButtonPerms等。
5.  Role管理：RoleMenu Prower Distribute 、设置Role按机构进行Data范围Prower划分。
6.  Dict管理：对系统中经常使用的一些较固定的Data进行维护。
7.  Params管理：对系统动态配置常用Params。
8.  通知Notice：系统通知Notice Info发布维护。
9.  OperateLogs：系统正常OperateLogs Record And Search；系统 Exception InfoLogs Record And Search。
10. LoginLogs：系统LoginLogs RecordSearch包含Login Exception。
11. 在线User ：当前系统中活跃User Status监控。
12. 定时 Task：在线（Add、Modify、Delete) Task调度包含执行结果Logs。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码Auto生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关 Info。
16. Cache监控：对系统的Cache InfoSearch，命令统计等。
17. 在线构建器：拖动表元素生成相应的HTML代码。
18. 连接池监视：监视当前系统Data库连接池Status，可进行分析SQL找出系统性能瓶颈。
<table>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/cd1f90be5f2684f4560c9519c0f2a232ee8.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/1cbcf0e6f257c7d3a063c0e3f2ff989e4b3.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8074972883b5ba0622e13246738ebba237a.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-9f88719cdfca9af2e58b352a20e23d43b12.png"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-39bf2584ec3a529b0d5a3b70d15c9b37646.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-936ec82d1f4872e1bc980927654b6007307.png"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-b2d62ceb95d2dd9b3fbe157bb70d26001e9.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-d67451d308b7a79ad6819723396f7c3d77a.png"/></td>
    </tr>	 
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/5e8c387724954459291aafd5eb52b456f53.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/644e78da53c2e92a95dfda4f76e6d117c4b.jpg"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8370a0d02977eebf6dbf854c8450293c937.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-49003ed83f60f633e7153609a53a2b644f7.png"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-d4fe726319ece268d4746602c39cffc0621.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-c195234bbcd30be6927f037a6755e6ab69c.png"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/b6115bc8c31de52951982e509930b20684a.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-5e4daac0bb59612c5038448acbcef235e3a.png"/></td>
    </tr>
</table>


## 关于我们
* 开发团队成立5年，我们前端开发、后端架构，有一颗热爱开Source 的心，致力于打造企业级的通用产品设计UI体系让项目  Or者更直观，更高效、更简，未来将持续关注UI交互，持续推出高质量的交互产品。
* 这五年我主要做isv对接淘宝、拼多多、抖音、美团等平台的订处理应用，日处理订300w条，因要熟悉业务也开过淘宝 And 拼多多店铺运营了一个网易严选的品牌。我们的公众No.会陆续更新一些我一边撸代码一边做客服的经历。也会更新一些我的读书笔记以及编程、创业、生活中踩坑的文章。另外还会开放一些米哈游、博世、企查查、同程、阿里、京东、拼多多等大厂、中厂 Or外企的内推Post！

## 大厂、外企内推
关注“编写美好前程”公众No.： Record编程、创业、生活中踩坑的文章，陆续会开放一些微软、阿里、京东、拼多多等大厂 Or外企的内推Post！

## 加群/技术支持

|                    公众No.                     |
|:------------------------------------------:|
| <img src="doc/datacall.jpg" width="200px"> |
扫公众No.二维码，关注后回复：“加群” Or“支持”