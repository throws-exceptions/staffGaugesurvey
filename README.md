# 微服务项目 #

+ 使用springboot+Dubbo+mybatis+Python+Thirft搭建图像处理识别以及web平台整合项目。

1. 克隆项目到本地
    ```
    git clone https://github.com/throws-exceptions/staffGaugesurvey.git
    #出现域名无法解析，将https换成git即可
    ```
2. 安装Docker
    + 更新apt包索引：
        ```
        sudo apt update
        ```
    + 通过HTTPS使用仓库(repository)安装：
        ```
        sudo apt install apt-transport-https ca-certificates curl software-properties-common
       ```
    + 在/etc/apt/sources.list.d/docker.list文件中添加下面内容
        ```
        deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable
       ```
    + 添加Docker官方的GPG密钥：
        ```
      curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
      ```
    + 安装
        ``` 
        sudo apt install docker-ce 
      ```
    + 验证docker查看docker服务是否启动
        ```
      systemctl status docker 
      ```
3. 在Docker中安装Zookeeper
    + 添加国内源
        ```
        vi /etc/docker/daemon.json
      ```
      在其中添加
      ```
      {
        "registry-mirrors": ["https://registry.docker-cn.com","https://pee6w651.mirror.aliyuncs.com"]
       }
        ```
     + 运行docker
         ```
         docker run --privileged=true -d --name zookeeper --publish 2181:2181  -d zookeeper:latest
         ```
4. 在IDEA中运行项目
    + Maven的settings.xml中添加镜像源
        ```
           <mirrors>
               <mirror>
                     <id>alimaven</id>
                     <mirrorOf>central</mirrorOf>
                     <name>aliyun maven</name>
                     <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
                </mirror>
             </mirrors>
       ```
5. 启动项目
    先启动provider中的springbootApplication，再启动Server中的springbootApplication
     
      
      
    
