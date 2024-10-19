# demo
## 10.13
1. springboot作为spring的一个子模块，方便spring项目的构建。优点：自动包含jar包的起步依赖；自动配置bean。
2. 明白spring的bean的含义和整体的运行逻辑。bean本身是作为一个标准化的类存在，方便依赖对象对被依赖对象的调用。为了减少依赖对象和被依赖对象之间的耦合度，所以提出了以一个新的概念就是IOC容器注入。IOC的思想是反转依赖关系，主动的将需要的被依赖对象注入依赖对象中，所以依赖关系的控制就由IOC容器来完成。spring本身就是一个大工厂，里面的类都是一个个的bean，所以在加上修饰符后，就完成了对这个bean类的配置，在后面的使用中可以直接通过`@Autowired`的方式来完成自动注入，可以直接创建类的实例对象，并调用方法，而不需要再主动的进行new来创建。
3. 完成了对环境的搭建，包括对java开发环境，maven项目环境，数据库的创建等工作。主要困难在于：
   1. 本地git和GitHub连接时，已经把公钥在GitHub上创建，但ssh连接不起作用。原因没有ssh-add 私钥。
   2. java的jdk已经下载好了，但是vscode就是识别不到。因为没有配置java.home，java.configuration.runtimes。（特别注意，vscode的setting是分本地和远程连接的
   3. maven本地安装好了，但是在vscode上创建maven项目失败。首先设置的本地仓库路径是没有权限改动的。第二没有配置java.configuration.maven.globalSettings，java.configuration.maven.userSettings，maven.executable.path。此外本地maven中的setting中下载的网址也要用国内镜像。
   4. vscode上配置springboot插件，要配置spring-boot.ls.java.home。
   5. 本地安装了mysql，但是无法远程连接。主要原因，首先，要修改root的连接地址，改成"%"。第二，要把mysql的监听地址改成0.0.0.0，在"sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf"中改bind-address的值。第三，确认防火墙设置，要允许3306端口开放，"sudo ufw allow 3306"。


## 10.18
1. SpringBoot有两种配置文件形式，*.properties和 *.yml(.yaml)
   1. *.properties的配置方式是：`a.b.c.d=XXX`，通过'.'来表现配置项的层级关系。
   2. *.yml的配置方式是：
      ```
      a:
      	b:
      		c:
      			d: XXX
      ```
     通过'换行和缩进'来表现配置项的层级关系。
2. 除了第三方的配置项外，还会需要自定义的配置项，自定义的配置项会将项目和关键参数解耦，方便对项目参数的调整。
   1. 对于配置文件中的值的获取，使用注解`@Value("${键名}")`。键名的表示方式是"a.b.c.d"形式。
   2. 会存在多个获取值的键名前缀重复的情况，为了简化配置复杂度，使用注解`@ConfigurationProperties(prefix="前缀")`来替代相同的前缀，方便开发。
3. Spring项目采用`@Component`注解来自动的对项目中Class类进行的Bean对象声明。同时也包括`@Component`的一些衍生注解，如`@Controller`，`@service`，`@Repository`等。这些注解会简化创建生成函数的过程，自动对类进行Bean对象注册。
4. Spring项目中，对这些Bean对象的扫描通过注解`@ComponentScan(basePackages = "包名")`来完成，如果没有填写包名，默认扫描当前文件所在的包及其子包。注解`@SpringBootApplication`包含了一个默认的`@ComponentScan`。但是`@ComponentScan`只能扫描识别出采用`@Component`注解的方式来声明的Bean对象，并对这些Bean对象进行注册。
5. 如果要注册的Bean对象来自于第三方而不是自定义的类，就无法使用`@Component`注解及其衍生注解来声明Bean对象。因为第三方文件是只读文件，所以无法在Class类的上方加上`@Component`注解。只能通过`@Bean`或`@Import`两种注解来完成对Bean对象的注册。
   1. `@Bean`，这个注解需要加在具体的实现类的上方，例如
      ```
      //注入Province对象
      @Bean
      public Province province(Country country){
      	System.out.println("province: " + country);
      	return new Province();
      }
      ```
      * 其中Province为第三方类，所以注册方式为，需要实现一个对象生成函数，并在函数上方加上注解`@Bean`，默认Bean对象的名称为函数名，也可以自定义Bean对象的名称`@Bean("名称")`。<br>
      * 如果方法内部需要使用到ioc容器中已存在的bean对象，那么只需要在方法上参数中声明即可，spring会自动注入。（创建对象存在依赖对象的情况）<br>
      * 只要放在可以被`@ComponentScan`扫描到的类中都可以。不建议放在启动类下，建议放在配置类中集中注册。配置类采用注解`@Configuration`。
   2. `@Import`，这个注解加在启动类上方。这个注解解决的问题是，如果配置类并不在`@ComponentScan`扫描的范围中。（这种情况下不需要再考虑注解`@Component`）
      1. 导入配置类：需要在启动类中使用`@Import(XXX.class)`，值是对应的配置类。支持数组方式注入，`@Import({XXX.class,XXX.class,XXX.class,XXX.class,XXX.class})`
      2. 导入ImportSelector接口实现类：需要实现spring接口`ImportSelector`的`selectImports`方法，方法需要返回`String[]`(内容是需要导入的配置类)，替代上面的数组注入方式。使用`@Import(XXX.class)`即可,值是对应的ImportSelector接口实现类。
      3. 在实际情况下，为了便于维护，会考虑把配置类信息写在项目的配置文件中，而不是直接在ImportSelector接口实现类中写死。所以会在resources文件夹下创建一个项目配置文件如`common.imports`。然后在selector类中读取文件内容，并以正确格式返回。
         ```
         List<String> imports = new ArrayList<>();
         InputStream is = CommonImportSelector.class.getClassLoader().getResourceAsStream("common.imports");
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line = null;
         while ((line = br.readLine())!=null) {
            imports.add(line);
         }
         br.close();
         return imports.toArray(new String[0]);
         ```
      4. 为了提高代码的整洁性，所以一般不会在启动类上方加那么多的注解，这就需要定义组合注解，将自定义的注解封装一下。特别注意，除了需要组合的注解外，还要加上`@Target(ElementType.TYPE)`（可以在类上使用）和`@Retention(RetentionPolicy.RUNTIME)`（保留到运行时阶段）。
6. Bean对象注册条件，有些时候对于一些Bean对象是否注入会加一些前置的判断，比如配置项存在时，哪个对象不存在时等等，这种情况下就需要对Bean对象的注册加一些条件。例如<br>
   `@ConditionalOnProperty(prefix = "country",name = {"name","system"})` 判断对应配置项是否存在（为了方便修改和维护，会选择将Bean对象需要的静态数据值放在项目配置文件中，需要时再读取）<br>
   `@ConditionalOnMissingBean(Country.class)` 判断指定类的Bean对象是否存在，若存在则不注入，否则注入。（可能是对某些场景下的分情况处理）<br>
   `@ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")` 判断环境中是否存在指定的类，如果存在则注入，否则不注入。（可以保证注入的Bean对象的类是存在的，也可能是为了应对一些前置条件，或当前Bean对象对某些类存在依赖）
7. SpringBoot自动配置原理：
   1. 首先在主启动类上添加了`@SpringBootApplication`注解，这个注解组合了`@EnableAutoConfiguration`注解，这个注解会开启自动配置。
   2. `@EnableAutoConfiguration`注解也是一个组合注解，组合了`@Import`注解，`@Import(AutoConfigurationImportSelector.class)`这个注解导入了AutoConfigurationImportSelector类。
   3. 这个AutoConfigurationImportSelector类实现了`ImportSelector`接口，并实现了接口的`selectImports`方法，这个方法它经过层层调用，最终会读取到jar包中META-INF目录下后缀名为.import的文件，当然SpringBoot2.7以前的版本读取的是Spring.factories文件，2.7-3.0之间这两个文件都兼容，3.0之后只读取.import后缀的这个文件。这个文件中配置了很多相关配置类的全类名。
   4. SpringBoot读取到全类名后，会解析对应的配置类的注册条件，也就是`@Conditional`注解及其衍生注解，把满足注册条件的Bean对象自动注入到IOC容器中。
8. 编写自动配置的jar包：
   1. 针对jar包中想要实现自动注入的类，实现对应的配置类。
   2. 需要编写一个自动配置的类，上面加上`@AuotoConfiguration`注解和`@Import`注解，把实现的配置类加入。`@Import(配置类.class)`
   3. 在jar包的META-INF/spring目录下创建org.springframework.boot.autoconfigure.AutoConfiguration.imports文件，并把自动配置类的全类名写入。
   4. 导入jar包就会实现Bean对象的自动注入
9. 编写starterjar包：
    1. autoconfigurer：用来提供自动配置功能，需要的内容包括：
       1. autoconfig，实现自动配置，包括注解`@AutoConfiguration`和下面的对象生成方法并用`@Bean`注解来注入IOC容器。
       2. 需要在resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports中写入配置类的全类名，保证spring会主动解析配置类。
    2. starter：用来进行jar包管理的工作，需要在pom文件中引入所有用到的jar包，包括autoconfig和config引入的jar包。
    3. 在spring项目中引入starter jar包即可
       
   
