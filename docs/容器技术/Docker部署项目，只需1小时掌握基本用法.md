这周开始使用Docker 部署自己开发的项目，由于之前没有用过，所以，开始慢慢学习Docker，学习的过程中有很多懵逼的地方。那么，接下来我会把我学习中感到困惑的概念，以及到底该怎么使用Docker 是正确的方法，分享出来，提供给可以用到的同学。闲话不唠，直接进入正题。

####  Docker的一些常用概念

- DockerFile，用来生成镜像的一个指令文件，告诉Docker 生成镜像文件，该怎么样一步一步的完成
- 镜像，就是一个模板（也可以说是Java的 Class）。用来生成容器的一个模具
- 容器，是通过镜像运行起来的一个实例（就相当于Java  new 出来的一个对象）
- 仓库，仓库就是存放镜像的一个库房。类似于GitHub，所以Docker 也有 Docker Hub

####  Docker项目部署

以Java为例，当创建了一个 Spring-Boot 项目之后。我该如何部署到Docker中呢？部署到Docker以后，我又该如何访问项目呢？下面我介绍我研究出来的两种部署方式：

#####  第一、通过Maven插件进行打包

1. 首先需要在 pom.xml 中引入如下Maven Docker 插件

   ```
   <docker.image.prefix>spring-boot-demo:V1.0.0</docker.image.prefix>
   
   <!-- Maven Docker 插件，如果直接使用docker build 命令打镜像，则不需要该插件 -->
   <plugin>
       <groupId>com.spotify</groupId>
       <artifactId>docker-maven-plugin</artifactId>
       <version>0.4.11</version>
       <configuration>
           <imageName>${docker.image.prefix}</imageName>
           <!-- DockerFile 文件所在路径 -->
           <dockerDirectory>${project.basedir}</dockerDirectory>
           <resources>
               <resource>
                   <targetPath>/</targetPath>
                   <directory>${project.build.directory}</directory>
                   <include>${project.build.finalName}.jar</include>
               </resource>
           </resources>
       </configuration>
   </plugin>
   ```

2. 使用 mvn clean 清理项目 -> mvn install 进行打包，生成 jar 文件

3. 使用 mvn package -Dmaven.test.skip=true docker:build     命令进行打包镜像

   ![docker:build](https://java-internal-work.oss-cn-beijing.aliyuncs.com/%E5%AE%B9%E5%99%A8%E6%8A%80%E6%9C%AF/docker/dockerbuildmaven.png)

4. 使用 docker images  命令，可以看到镜像已经打包成功放入 docker  镜像中

   ![Docker Images 镜像列表](https://java-internal-work.oss-cn-beijing.aliyuncs.com/%E5%AE%B9%E5%99%A8%E6%8A%80%E6%9C%AF/docker/dockerimages.png)

5. 使用命令导出镜像文件到宿主机

   docker save spring-boot-demo:V1.0.0 | gzip > /Users/pangh/Documents/Docker/docker-image/spring-boot-demo.tar.gz

   参数说明：

   1. spring-boot-demo:V1.0.0  是 镜像名称（imageName+tag）

   2. ">" 后边的参数是宿主机的文件目录

6.  将镜像文件上传到服务器目录中（服务器目录自己定义，放到哪里都行）

7. 使用命令导入镜像文件到服务器的Docker 中

   docker load<spring-boot-demo.tar.gz

   参数说明：

   1. spring-boot-demo.tar.gz是上传到服务器的镜像文件

8. 导入镜像文件之后，使用 命令 docker images 查看，镜像已经加载到docker 中

9. 使用  docker run -p 9099:8088 -t spring-boot-demo:V1.0.0  命令启动镜像

   参数说明：

   1. 9099 是对外暴露的端口号

   2. 8088 服务本身的端口号，也就是配置文件 application.yml 中配置的端口号

   ![Docker 容器启动列表](https://java-internal-work.oss-cn-beijing.aliyuncs.com/%E5%AE%B9%E5%99%A8%E6%8A%80%E6%9C%AF/docker/dockerimages.png)

10. 之后就可以通过 ip:9099 端口进行服务访问啦~

#####  第二、通过Docker自身命令打包，并不会依赖第三方的插件（推荐）

1. 首先需要准备dockerfile  文件

   注意：dockerfile 中的 COPY 指令，第一个参数可执行的 jar 包需要跟dockerfile 在同一个目录，才能不写路径。否则的加上路径，不加路径会报错，错误如下：

   ```
   COPY failed: stat /var/lib/docker/tmp/docker-builder231303134/spring-boot-demo-0.0.1-SNAPSHOT.jar: no such file or directory
   ```

2. 执行 mvn install 命令打包项目，生成可执行jar 文件

3. 执行 docker build -t spring-boot-demo:v1.0 .

   参数说明：

   1. 最后那个 点 代表使用当前路径的 Dockerfile 进行构建 

   2. -t spring-boot-demo:v1.0  给新构建的镜像取名为 spring-boot-demo， 并设定版本（tag）为 v1.0

   使用docker images 可以看出镜像名称和tag

4. 如果以上操作是在自己开发电脑或者Mac上，那么之后要放到服务器的话，可以继续执行“方式一”第5步骤之后操作。

5. 如果是直接在linux 服务器上操作docker  build 的话，直接就可以在docker 中启动容器，命令如下：docker run -p 9099:8088 -t spring-boot-demo:v1.0

####  Docker常用命令

- docker build  - 打包可执行文件为Docker 镜像
- docker images  - 查看当前Docker 中的所有镜像文件
- docker rmi 镜像文件ID  - 删除镜像文件
- docker run  -  启动镜像文件为运行容器
- docker ps  - 查看当前正在运行的容器
- docker ps -a  - 查看正在运行的和已经停止的容器
- docker start/stop/restart  - 启动/停止/重启容器
- docker logs 容器ID  - 可以查看容器运行日志
- docker save   - 导出镜像文件
- docker load  - 导入镜像文件

####  Docker注意事项

1. 升级版本的时候，一定要先把Docker 正在运行的容器停止，然后删除Docker 容器。之后再删除Docker容器对应的镜像文件。最后在Docker build 打包镜像文件，在用docker run 启动镜像文件。

