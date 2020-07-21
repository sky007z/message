# 短信任务管理平台
## 介绍
本项目基于spring-boot，腾讯云SMS服务、mybatis-plus、前端采用Vue.js2.0 + element-ui,可以定时发送天气预报短信(自由配置)、生日祝福短信、待办提醒短信等。天气信息由jsoup转换入mysql存储。
短信效果：
![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/pic1.jpg)
前端地址：

![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/pic2.png)
![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/pic3.png)
![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/pic4.png)
本项目前后端分离，单独部署后端也可实现所有功能
![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/pic5.jpg)
## 本项目所用技术

| 选型                  | version       |
| --------------------- | ------------- |
| spring-boot           | 2.3.1.RELEASE |
| tencentcloud-sdk-java | 3.1.90        |
| jsoup                 | 1.11.3        |
| mybatis-plus          | 3.3.2         |
| mysql                 | 8.0.17        |
| swagger-ui            | 2.9.2         |
| vue                   | 2.2.6         |
| element-ui            | 1.2.9         |

## 数据库
运行前请执行weather.sql建库
## 后端运行

mvn install

在application.yml中配置好数据库

运行application

http://localhost:9522/swagger-ui.html

## 使用 以发送短信为例
### 注册腾讯云SMS服务
https://console.cloud.tencent.com/smsv2
**按照流程申请，腾讯云会免费赠送200条短信。**
![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/pic6.png)
![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/pic7.png)
### 创建你需要的短信模板
![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/pic8.jpg)
由于短信模板的id不相同，需要更改templateID。
如发送天气短信的WeatherController类sendWeatherMsgTom方法，需要更改你申请的天气短信templateID
### 添加secretId，secretKey，appid
修改SendSmsService、SendStatusStatistics两个短信发送服务类。
` Credential cred = new Credential Credential(String secretId, String secretKey)
  String appid = "xxxxxxx";
  `

### 添加用户
可用前端添加
可以在swagger-ui中查看添加接口
http://localhost:9522/phone/add/{name}/{phone}/{cityCode}/{state}/{rule}/{birthday}/{birthdayState}
cityCode可在city_list查询得知
### jsoup获取天气数据
存储全部用户绑定城市的7日天气信息
http://localhost:9522/weather/setAll/7d
### 发送天气短信
全部用户发送明日天气短信
http://localhost:9522/weather/send/msg/tom

### 定时任务
采用spring-boot的定时任务，配置文件为task-config.properties，可实现自动定时发送。
            
## docker部署
### 1、打包
`mvn package`
将message-0.0.1-SNAPSHOT.jar重命名为message.jar，并将message.jar和目录下的Dockerfile上传至服务器同级目录。

### 2、镜像
为保证定时任务准确，请检查服务器时区，并把docker的时间卷localtime挂载出来
`  docker build -t message .
   docker run -d -p 9522:9522 --name message message -v /etc/localtime:/etc/localtime:ro`

### 3、日志
`docker ps`   

`docker logs message`

![docker1](https://github.com/laughingfuzihao/message/blob/master/src/main/resources/static/pic/docker1.png)


如果有帮助，请点亮一颗星，如有问题请联系-微信号：qq619426619 

