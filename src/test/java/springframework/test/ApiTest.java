package springframework.test;

import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.Test;

import springframework.beans.PropertyValue;
import springframework.beans.PropertyValues;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanReference;
import springframework.beans.factory.support.DefaultListableBeanFactory;
import springframework.test.bean.UserController;
import springframework.test.bean.UserDao;
import springframework.test.bean.UserService;

/**
 * BeanFactory 测试
 * @author gusixue
 * @date 2023/3/27
 */
public class ApiTest {

    @Test
    public void test() {
        // 让 Cglib 代理对象的class文件写入到磁盘（必须是标准的 main 方法才行）
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/gusixue/Desktop");


        // 1. 初始化 BeanFactory（依次往上调用父类空参构造器）
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 设置属性与注册 BeanDefinition
        PropertyValues propertyValues2 = new PropertyValues();
        propertyValues2.getPropertyValues().add(new PropertyValue("userService",
                new BeanReference("userService")));
        BeanDefinition userControllerDefinition = new BeanDefinition(UserController.class, propertyValues2);
        beanFactory.registerBeanDefinition("userController", userControllerDefinition);

        PropertyValues propertyValues1 = new PropertyValues();
        // 注意这儿 Bean 对象名字与 getXxx 方法的参数名字不一致
        propertyValues1.getPropertyValues().add(new PropertyValue("myUserDao",
                new BeanReference("userDao")));
        propertyValues1.getPropertyValues().add(new PropertyValue("age", 26));
        BeanDefinition UserServiceDefinition = new BeanDefinition(UserService.class, propertyValues1);
        beanFactory.registerBeanDefinition("userService", UserServiceDefinition);

        BeanDefinition userDaoBeanDefinition = new BeanDefinition(UserDao.class, null);
        beanFactory.registerBeanDefinition("userDao", userDaoBeanDefinition);

        // 3. 第一次获取带参数单例 Bean 对象
        UserController userController1 = (UserController)beanFactory.getBean("userController", "机器人1");
        userController1.login();
        userController1.register();
        System.out.println(userController1);

        // 4. 第二次获取带参数单例 Bean 对象，由于单例模式因此第二次添加参数无效
        UserController userController2 = (UserController)beanFactory.getBean("userController", "机器人2");
        userController2.login();
        userController2.register();
        System.out.println(userController2);

        // 5. 第一次获取单例 Bean 对象
        UserService userService1 = (UserService)beanFactory.getBean("userService");
        userService1.login();
        userService1.register("机器人3");
        System.out.println(userService1);

        // 6. 第二次获取单例 Bean 对象
        UserService userService2 = (UserService)beanFactory.getBean("userService");
        userService2.login();
        userService2.register("机器人4");
        System.out.println(userService2);

        // 7. 第一次获取单例 Bean 对象
        UserDao userDao1 = (UserDao)beanFactory.getBean("userDao");
        userDao1.login();
        userDao1.register("机器人5", 102);
        System.out.println(userDao1);

        // 8. 第二次获取单例 Bean 对象
        UserDao userDao2 = (UserDao)beanFactory.getBean("userDao");
        userDao2.login();
        userDao2.register("机器人6", 204);
        System.out.println(userDao2);
    }
}
