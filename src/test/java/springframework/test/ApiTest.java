package springframework.test;

import cn.hutool.core.io.IoUtil;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.Before;
import org.junit.Test;

import springframework.beans.PropertyValue;
import springframework.beans.PropertyValues;
import springframework.beans.factory.BeanFactory;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanReference;
import springframework.beans.factory.support.DefaultListableBeanFactory;
import springframework.beans.factory.xml.XmlBeanDefinitionReader;
import springframework.context.support.ClassPathXmlApplicationContext;
import springframework.core.io.DefaultResourceLoader;
import springframework.core.io.Resource;
import springframework.test.bean.UserController;
import springframework.test.bean.UserDao;
import springframework.test.bean.UserService;
import springframework.test.common.MyBean1FactoryPostProcessor;
import springframework.test.common.MyBean1PostProcessor;
import springframework.test.common.MyBean2FactoryPostProcessor;
import springframework.test.common.MyBean2PostProcessor;

import java.io.IOException;
import java.io.InputStream;

/**
 * BeanFactory 测试
 * @author gusixue
 * @date 2023/3/27
 */
public class ApiTest {

    private DefaultResourceLoader resourceLoader;

    @Test
    public void test() {
        // 让 Cglib 代理对象的class文件写入到磁盘（必须是标准的 main 方法才行）
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/gusixue/Desktop");


        // 1. 初始化 BeanFactory（依次往上调用父类空参构造器）
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 设置属性与注册 BeanDefinition
        PropertyValues propertyValues2 = new PropertyValues();
        propertyValues2.addPropertyValue(new PropertyValue("userService",
                new BeanReference("userService")));
        BeanDefinition userControllerDefinition = new BeanDefinition(UserController.class, propertyValues2);
        beanFactory.registerBeanDefinition("userController", userControllerDefinition);

        PropertyValues propertyValues1 = new PropertyValues();
        // 注意这儿 Bean 对象名字与 getXxx 方法的参数名字不一致
        propertyValues1.addPropertyValue(new PropertyValue("myUserDao",
                new BeanReference("userDao")));
        propertyValues1.addPropertyValue(new PropertyValue("age", 26));
        BeanDefinition UserServiceDefinition = new BeanDefinition(UserService.class, propertyValues1);
        beanFactory.registerBeanDefinition("userService", UserServiceDefinition);

        BeanDefinition userDaoBeanDefinition = new BeanDefinition(UserDao.class, null);
        beanFactory.registerBeanDefinition("userDao", userDaoBeanDefinition);

        // 通过 Bean 工厂创建对象
        doBeanSolution(beanFactory);
    }

    @Before
    public void init() {
        this.resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void testResourceLoader() throws IOException {

        System.out.println("classpath:Spring.xml");
        resourceLoader("classpath:Spring.xml");
        System.out.println();

        System.out.println("File");
        resourceLoader("src/test/resources/Spring.xml");
        System.out.println();

        System.out.println("File");
        resourceLoader("/Users/gusixue/development/my_workspace/spring-boot/easy-spring/src/test/resources/Spring.xml");
        System.out.println();

        System.out.println("URL");
        resourceLoader("https://raw.githubusercontent.com/g1351612073/easy-spring/master/src/test/resources/Spring.xml");
        System.out.println();
    }

    private void resourceLoader(String location) throws IOException {
        Resource resource = resourceLoader.getResource(location);
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void testXml() {
        // 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 解析 xml 并设置参数，注册 BeanDefinition
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, resourceLoader);
//        xmlBeanDefinitionReader.loadBeanDefinitions("/Spring.xml");
        xmlBeanDefinitionReader.loadBeanDefinitions(resourceLoader.getResource("src/test/resources/Spring.xml"));
//        xmlBeanDefinitionReader.loadBeanDefinitions(resourceLoader.getResource("/Users/gusixue/development/my_workspace/spring-boot/easy-spring/src/test/resources/Spring.xml")
//                , resourceLoader.getResource("https://raw.githubusercontent.com/g1351612073/easy-spring/master/src/test/resources/Spring.xml"));

        // 通过 Bean 工厂创建对象（实例化 Bean 对象，Bean 对象属性填充（依赖注入））
        doBeanSolution(beanFactory);
    }


    /**
     * 通过 Bean 工厂创建对象
     * 实例化 Bean 对象，Bean 对象属性填充（依赖注入）
     */
    private <T extends BeanFactory> void doBeanSolution(T beanFactory) {
        // 第一次获取带参数单例 Bean 对象
        UserController userController1 = (UserController)beanFactory.getBean("userController", "机器人1");
        userController1.login();
        userController1.register();
        System.out.println(userController1);

        // 第二次获取带参数单例 Bean 对象，由于单例模式因此第二次添加参数无效
        UserController userController2 = (UserController)beanFactory.getBean("userController", "机器人2");
        userController2.login();
        userController2.register();
        System.out.println(userController2);

        // 第一次获取单例 Bean 对象
        UserService userService1 = (UserService)beanFactory.getBean("userService");
        userService1.login();
        userService1.register("机器人3");
        System.out.println(userService1);

        // 第二次获取单例 Bean 对象
        UserService userService2 = (UserService)beanFactory.getBean("userService");
        userService2.login();
        userService2.register("机器人4");
        System.out.println(userService2);

        // 第一次获取单例 Bean 对象
        UserDao userDao1 = (UserDao)beanFactory.getBean("userDao");
        userDao1.login();
        userDao1.register("机器人5", 102, 1);
        System.out.println(userDao1);

        // 第二次获取单例 Bean 对象
        UserDao userDao2 = (UserDao)beanFactory.getBean("userDao");
        userDao2.login();
        userDao2.register("机器人6", 204, 0);
        System.out.println(userDao2);
    }


    @Test
    public void testBeanFactoryPostProcessorAndBeanPostProcessor() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 加载 XML 配置文件，注册 BeanDefinition
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory, resourceLoader);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 注册 BeanDefinition 后、实例化 Bean 对象之前，修改 BeanDefinition 的属性值
        MyBean1FactoryPostProcessor bean1FactoryPostProcessor = new MyBean1FactoryPostProcessor();
        bean1FactoryPostProcessor.postProcessBeanFactory(beanFactory);

        MyBean2FactoryPostProcessor bean2FactoryPostProcessor = new MyBean2FactoryPostProcessor();
        bean2FactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean 实例化与属性填充后、Bean 初始化前后，修改 Bean 属性信息
        MyBean1PostProcessor bean1PostProcessor = new MyBean1PostProcessor();
        beanFactory.addBeanPostProcessor(bean1PostProcessor);

        MyBean2PostProcessor bean2PostProcessor = new MyBean2PostProcessor();
        beanFactory.addBeanPostProcessor(bean2PostProcessor);

        // 5. 获取 Bean 对象调用方法
        doBeanSolution(beanFactory);
    }

    @Test
    public void testContext() {
        // 初始化 Spring 应用上下文
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring2.xml");

        // 获取 Bean 对象调用方法
        doBeanSolution(context);
    }

    @Test
    public void testContextInitDestroy() {
        // 初始化 Spring 应用上下文
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring3.xml");
        context.registerShutdownHook();

        // 获取 Bean 对象调用方法
        doBeanSolution(context);
    }
}
