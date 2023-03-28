package springframework.test.bean;

/**
 * 测试用控制器对象
 * @author gusixue
 * @date 2023/3/27
 */
public class UserManager {

    private String name;

    public UserManager() {}

    public UserManager(String name) {
        this.name = name;
    }

    public void login() {
        System.out.println("开始登录...");
    }

    public void register() {
        System.out.println("开始注册..." + name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + System.identityHashCode(this) + " UserManager{" +
                "name='" + name + '\'' +
                '}';
    }
}
