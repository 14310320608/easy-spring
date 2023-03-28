package springframework.test.bean;

/**
 * 测试用控制器对象
 * @author gusixue
 * @date 2023/3/27
 */
public class UserController {

    private String name;

    public UserController() {}

    public UserController(String name) {
        this.name = name;
    }

    public void login() {
        System.out.println("开始登录...");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
