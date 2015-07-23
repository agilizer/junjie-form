#springboot
springMvc进行web开发,工程可以使用groovy java代码.
详细可以访问:    http://start.spring.io/

相关开发文档： http://git.oschina.net/asdtiang/spring-boot-jpa-security-groovy doc目录


use groovy and java code in one project,Enjoy the dynamic language.

#开发工具
请看doc/项目导入视频.exe
开发工具下载地址：http://pan.baidu.com/s/1i3ovyaL 
需要集成 groovy  gradle.<br>
可以使用eclipse 或者 sts 或者idea.我使用的是sts.gvm安装 springboot,groovy,gradle.<br>
导入工程为gradle,第一次导入时由于要下载jar包,可能会花一些时间,请耐心等待.<br>

集成:spring security 



#spring loaded
spring loaded作用为,更改代码后更新虚拟机字节码,即热部署.<br>
具体配置参考: http://wiselyman.iteye.com/blog/2146475<br>
spring loaded地址:https://github.com/spring-projects/spring-loaded <br>
jar包已经下载到devLib目录<br>
虚拟机参数示例:<br>
-javaagent:/home/asdtiang/workspace-sts-3.6.3.SR1/study/devLib/springloaded-1.2.3.RELEASE.jar -noverify
‪E:\study\devLib\springloaded-1.2.3.RELEASE.jar

#环境相关配置
环境主要有默认,dev,production,test等.把默认设置为生产环境.<br>

#开发环境应该加入以下参数
-javaagent:path/springloaded-1.2.3.RELEASE.jar  -noverify   -Dspring.profiles.active=dev  -Ddb.conf.value=dev

#项目启动
直接运行com.agilemaster.form.war.JunjieFormWarApplication
可以run as Groovy Script 或者 run as Java Application

