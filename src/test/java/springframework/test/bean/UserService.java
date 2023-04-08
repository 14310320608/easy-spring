package springframework.test.bean;

import springframework.beans.factory.DisposableBean;
import springframework.beans.factory.InitializingBean;

/**
 * 测试用控制器对象
 * @author gusixue
 * @date 2023/3/27
 */
public class UserService implements InitializingBean, DisposableBean {

    private UserDao myUserDao;

    private int age;

    private int sex;

    public void login() {
        myUserDao.login();
    }

    public void register(String name) {
        myUserDao.register(name, age, sex);
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
}
