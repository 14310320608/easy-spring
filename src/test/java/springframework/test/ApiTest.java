package springframework.test;

import org.junit.Test;

import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.support.DefaultListableBeanFactory;
import springframework.test.bean.UserController;
import springframework.test.bean.UserManager;

/**
 * BeanFactory 测试
 * @author gusixue
 * @date 2023/3/27
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1. 初始化 BeanFactory（依次往上调用父类空参构造器）
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 注册 BeanDefinition
        BeanDefinition userControllerDefinition = new BeanDefinition(UserController.class);
        beanFactory.registerBeanDefinition("userController", userControllerDefinition);

        BeanDefinition userManagerDefinition = new BeanDefinition(UserManager.class);
        beanFactory.registerBeanDefinition("userManager", userManagerDefinition);

        // 3. 第一次获取单例 Bean 对象
        UserController userController1 = (UserController)beanFactory.getBean("userController");
        userController1.login();
        System.out.println(userController1);

        // 4. 第二次获取单例 Bean 对象
        UserController userController2 = (UserController)beanFactory.getBean("userController");
        userController2.login();
        System.out.println(userController2);

        // 5. 第一次获取带参数单例 Bean 对象
        UserManager userManager1 = (UserManager)beanFactory.getBean("userManager", "辜思学");
        userManager1.register();
        System.out.println(userManager1);

        // 6. 第二次获取带参数单例 Bean 对象，由于单例模式因此第二次添加参数无效
        UserManager userManager2 = (UserManager)beanFactory.getBean("userManager", "机器人辜思学");
        userManager2.register();
        System.out.println(userManager2);
    }
}
