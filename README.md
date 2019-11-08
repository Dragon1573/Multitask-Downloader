# 【Java课程设计】多任务下载器

[![GitHub license](https://img.shields.io/github/license/Dragon1573/Multitask-Downloader?label=License)](https://github.com/Dragon1573/Multitask-Downloader)
[![CSDN account](https://img.shields.io/badge/CSDN-%40%E3%81%B9%E6%96%AD%E6%A1%A5%E7%83%9F%E9%9B%A8%E3%83%9F-important?style=social)](https://blog.csdn.net/u011367208/article/details/85471954)

## 第一章　设计内容及要求

### 第1节　下载工具的基本要求

　　生活中，我们需要使用大量的文件下载工具。迅雷X、QQ旋风、快车、电驴，它们都能够实现多任务、多线程下载。本次程序设计的首要任务是以Java作为唯一编程语言编写一个简易的下载器。具体要求如下：

1. 下载器拥有完善的图形化交互界面，能够与用户形成高效的互动；
2. 下载器应该能够正常地连接网络、获取在线文件数据；
3. 下载器应支持多任务并行下载。

### 第2节　需要实现的主要功能

1. 用户可以自由新建下载任务；
2. 用户可以自由设置资源在本地另存为的文件位置；
3. 当出现网址格式错误、资源无法连接、文件读写失败等各类异常情况时，能够通知用户进行更正；
4. 下载过程中，应有进度条等元素实时反馈下载进度；
5. 文件下载完成后，能够提示用户。

## 第二章　总体设计

### 第1节　总体功能简介

　　下载器主界面采用边界布局（[`java.awt.BorderLayout`](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/BorderLayout.html)），区分顶端菜单栏与下方进度栏。

　　菜单栏分为2大主菜单——开始、帮助。开始菜单包括新建任务与退出程序，帮助菜单包含软件版本相关信息。

　　新建任务应按顺序弹出资源输入框、另存为选择框，任务开始后应在进度栏生成相应的下载详情，下载完成后应关闭相应的下载详情。

　　进度栏采用网格布局（[`java.awt.GridLayout`](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/GridLayout.html)）、分为5行1列，每一行对应一个下载任务的详情面板。

## 第三章　使用方法

### 第1节　使用二进制发行包

1. 此发行版本适用于 *Oracle JDK 11* 及以上版本，请通过以下命令确认您当前使用的 JDK 版本：
    ```shell script
    java --version
    ```
2. 从 [*GitHub Release*](https://github.com/Dragon1573/Multitask-Downloader/releases) 下载二进制发行包（以 `*.jar` 为文件后缀名）。
3. 双击下载的 `*.jar` 文件，应用程序应当自行启动并展示 Java Swing 程序主界面。

### 第2节　克隆源代码并运行

1. 通过以下命令验证您的 *Git* 版本，它应该处于 `v2.23.0` 或更高版本：
    ```shell script
    git --version
    ```
2. 通过以下命令验证您的 *Apache Maven* 版本，它应该处于 `v3.6.1` 或更高版本：
    ```shell script
    mvn --version
    ```
3. 通过以下命令将本存储库克隆至本地：
    ```shell script
    # 使用 HTTPS 链接进行克隆
    git clone https://github.com/Dragon1573/Multitask-Downloader.git
   
    # 使用 SSH 链接进行克隆（如果您配置了 SSH 密钥）
    git clone git@github.com:Dragon1573/Multitask-Downloader.git
    ```
4. 进入存储库文件夹，修改 `pom.xml` 中的以下2个标签，更改为您当前使用的 JDK 版本：
    ```xml
      <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
      </properties>
    ```
5. 使用以下命令编译并生成可执行 `*.jar` 文件：
    ```shell script
    mvn clean package
    ```
6. 双击编译生成的 `*.jar` 文件，应用程序应当自行启动并展示 Java Swing 程序主界面。

### 第3节　下载源代码并运行

1. 通过以下命令验证您的 *Apache Maven* 版本，它应该处于 `v3.6.1` 或更高版本：
    ```shell script
    mvn --version
    ```
2. 通过以下链接下载本仓库的归档版本：
    - [Windows](https://github.com/Dragon1573/Multitask-Downloader/archive/master.zip)
    - [Linux & macOS](https://github.com/Dragon1573/Multitask-Downloader/archive/master.tar.gz)
3. 解压归档文件并进入存储库文件夹，使用以下命令编译并生成可执行 `*.jar` 文件：
    ```shell script
    mvn clean package
    ```
4. 双击编译生成的 `*.jar` 文件，应用程序应当自行启动并展示 Java Swing 程序主界面。

## 第四章　后记

　　完整开源代码共6个 Java 文件，耗时1月零2天、基于 [*Oracle Java Development Kit 11.0.4*](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html) 在 [*JetBrains Intellij IDEA 2019.2.3*](https://www.jetbrains.com/idea/) 编写而成。代码换行、缩进、注释写法等代码风格在阿里巴巴代码规约系统（[*Alibaba P3C Formatter*](https://github.com/alibaba/p3c)）的基础上定制得到，不一定遵循 Java 规范。若您有严重的强迫症，请使用您自己的集成开发环境或代码编辑器重新格式化。

　　感谢所有复制、使用此开源代码的用户，你们的访问是对此作品的认可！若您有更好的改进建议，请发起 [*GitHub Issues*](https://github.com/Dragon1573/Multitask-Downloader/issues) 或 [*GitHub Pull Requests*](https://github.com/Dragon1573/Multitask-Downloader/pulls)。

　　谢谢！
