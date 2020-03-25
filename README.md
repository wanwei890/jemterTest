# jmeter http请求 压测脚本

## 使用方法

1、使用maven打包

2、将jar包放到jmeter安装目录\lib\ext下

3、重启jmter

4、添加测试计划->添加线程组->添加用户自定义变量
| ![jmeter_fig02](docs/imgs/userdefinedvars.jpg) |

5、添加BeanShell Sample
| ![jmeter_fig03](docs/imgs/beanshellscripts.jpg) |

6、设置压测并发线程数、压测时间，即可
| ![jmeter_fig04](docs/imgs/threadsconfig.jpg) |
