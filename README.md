# demo
## 10.13
1. springboot作为spring的一个子模块，方便spring项目的构建。优点：自动包含jar包的起步依赖；自动配置bean。
2. 明白spring的bean的含义和整体的运行逻辑。bean本身是作为一个标准化的类存在，方便依赖对象对被依赖对象的调用。为了减少依赖对象和被依赖对象之间的耦合度，所以提出了以一个新的概念就是IOC容器注入。IOC的思想是反转依赖关系，主动的将需要的被依赖对象注入依赖对象中，所以依赖关系的控制就由IOC容器来完成。spring本身就是一个大工厂，里面的类都是一个个的bean，所以在加上修饰符后，就完成了对这个bean类的配置，在后面的使用中可以直接通过@Autowired的方式来完成自动注入，可以直接创建类的实例对象，并调用方法，而不需要再主动的进行new来创建。
3. 完成了对环境的搭建，包括对java开发环境，maven项目环境，数据库的创建等工作。主要困难在于：
4. 本地git和GitHub连接时，已经把公钥在GitHub上创建，但ssh连接不起作用。原因没有ssh-add 私钥。
5. java的jdk已经下载好了，但是vscode就是识别不到。因为没有配置java.home，java.configuration.runtimes。（特别注意，vscode的setting是分本地和远程连接的
6. maven本地安装好了，但是在vscode上创建maven项目失败。首先设置的本地仓库路径是没有权限改动的。第二没有配置java.configuration.maven.globalSettings，java.configuration.maven.userSettings，maven.executable.path。此外本地maven中的setting中下载的网址也要用国内镜像。
7. vscode上配置springboot插件，要配置spring-boot.ls.java.home。
8. 本地安装了mysql，但是无法远程连接。主要原因，首先，要修改root的连接地址，改成"%"。第二，要把mysql的监听地址改成0.0.0.0，在"sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf"中改bind-address的值。第三，确认防火墙设置，要允许3306端口开放，"sudo ufw allow 3306"。
