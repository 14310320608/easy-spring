package springframework.test.bean;

import springframework.beans.BeansException;
import springframework.beans.factory.*;
import springframework.context.ApplicationContext;
import springframework.context.ApplicationContextAware;

/**
 * 测试用控制器对象
 * @author gusixue
 * @date 2023/3/27
 */
public class UserService implements InitializingBean, DisposableBean, BeanFactoryAware, BeanClassLoaderAware,
                                    BeanNameAware, ApplicationContextAware {

    private UserDao myUserDao;

    private int age;

    private int sex;

    public void login() {
        myUserDao.login();
    }

    public void register(String name) {
        myUserDao.register(name, age, sex);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserService 执行 afterPropertiesSet 方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("UserService 执行 destroy 方法");
    }

    public void initMethod() {
        System.out.println("UserService 执行 init-method 方法");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("BeanClassLoaderAware 获取 classLoader:" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware 获取 beanFactory:" + beanFactory);
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("BeanNameAware 获取 beanName:" + beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware 获取 applicationContext:" + applicationContext);
    }

    public UserDao getMyUserDao() {
        return myUserDao;
    }

    public void setMyUserDao(UserDao myUserDao) {
        this.myUserDao = myUserDao;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
