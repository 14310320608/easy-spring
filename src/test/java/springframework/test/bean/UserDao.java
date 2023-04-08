package springframework.test.bean;

import springframework.beans.factory.DisposableBean;
import springframework.beans.factory.InitializingBean;

/**
 * @author gusixue
 * @date 2023/3/28
 */
public class UserDao implements InitializingBean, DisposableBean {

    public void login() {
        System.out.println("开始登录...");
    }

    public void register(String name, int age, int sex) {
        System.out.println("开始注册... name:" + name + " age:" + age + " sex:" + sex);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserDao 执行 afterPropertiesSet 方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("UserDao 执行 destroy 方法");
    }

    public void destroyMethod() {
        System.out.println("UserDao 执行 destroy-method 方法");
    }
}
