package springframework.test.bean;

/**
 * @author gusixue
 * @date 2023/3/28
 */
public class UserDao {

    public void login() {
        System.out.println("开始登录...");
    }

    public void register(String name, int age) {
        System.out.println("开始注册... name:" + name + " age:" + age);
    }
}
