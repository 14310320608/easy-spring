package springframework.beans.factory.config;

import springframework.beans.BeansException;
import springframework.beans.factory.ListableBeanFactory;

/**
 * @author gusixue
 * @description 提供分析和修改 Bean 对象以及预先实例化的操作接口
 * @date 2023/4/1
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

    /**
     * @return 返回指定 Bean 对象注册的 BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
