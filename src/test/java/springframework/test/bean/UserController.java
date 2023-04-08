package springframework.test.bean;

import springframework.beans.factory.DisposableBean;
import springframework.beans.factory.InitializingBean;

/**
 * 测试用控制器对象
 * @author gusixue
 * @date 2023/3/27
 */
public class UserController implements InitializingBean, DisposableBean {

    private String name;

    private UserService userService;

    public UserController() {

    }

    public UserController(String name) {
        this.name = name;
    }

    public void login() {
        userService.login();
    }

    public void register() {
        userService.register(name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserController 执行 afterPropertiesSet 方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("UserController 执行 destroy 方法");
    }
}
