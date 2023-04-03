package springframework.test.bean;

/**
 * 测试用控制器对象
 * @author gusixue
 * @date 2023/3/27
 */
public class UserController {

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
}
