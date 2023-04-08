package springframework.beans.factory;

/**
 * @author gusixue
 * @description 定义 Bean 初始化前执行的接口
 * @date 2023/4/8
 */
public interface InitializingBean {

    /**
     * Bean 对象属性填充后，初始化 init-method 前进行的初始化
     */
    void afterPropertiesSet() throws Exception;
}
