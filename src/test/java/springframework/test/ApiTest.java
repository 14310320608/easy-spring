package springframework.test;

import cn.hutool.core.io.IoUtil;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.Before;
import org.junit.Test;

import springframework.beans.PropertyValue;
import springframework.beans.PropertyValues;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanReference;
import springframework.beans.factory.support.DefaultListableBeanFactory;
import springframework.beans.factory.xml.XmlBeanDefinitionReader;
import springframework.core.io.DefaultResourceLoader;
import springframework.core.io.Resource;
import springframework.test.bean.UserController;
import springframework.test.bean.UserDao;
import springframework.test.bean.UserService;

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

        System.out.println("file");
        resourceLoader("src/test/resources/Spring.xml");
        System.out.println();

        System.out.println("file");
        resourceLoader("/Users/gusixue/development/my_workspace/spring-boot/easy-spring/src/test/resources/Spring.xml");
        System.out.println();

        System.out.println("classpath:Spring.xml");
        resourceLoader("Spring.xml");
        System.out.println();
    }

    private void resourceLoader(String location) throws IOException {
        Resource resource1 = resourceLoader.getResource(location);
        InputStream inputStream = resource1.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void testXml() {
        // 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 解析 xml，设置参数、注册 BeanDefinition
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, resourceLoader);
        xmlBeanDefinitionReader.loadBeanDefinitions("/Spring.xml");

        // 通过 Bean 工厂创建对象
        doBeanSolution(beanFactory);
    }


    /**
     * 通过 Bean 工厂创建对象
     */
    private void doBeanSolution(DefaultListableBeanFactory beanFactory) {
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
        userDao1.register("机器人5", 102);
        System.out.println(userDao1);

        // 第二次获取单例 Bean 对象
        UserDao userDao2 = (UserDao)beanFactory.getBean("userDao");
        userDao2.login();
        userDao2.register("机器人6", 204);
        System.out.println(userDao2);
    }
}
