package springframework.beans.factory.config;

/**
 * 定义：获得单例 Bean 对象
 * @author gusixue
 * @date 2023/3/26
 */
public interface SingletonBeanRegistry {

    Object getSingletonBean(String beanName);
}
