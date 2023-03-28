package springframework.test.bean;

/**
 * 测试用控制器对象
 * @author gusixue
 * @date 2023/3/27
 */
public class UserService {

    private UserDao myUserDao;

    private int age;

    public void login() {
        myUserDao.login();
    }

    public void register(String name) {
        myUserDao.register(name, age);
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
