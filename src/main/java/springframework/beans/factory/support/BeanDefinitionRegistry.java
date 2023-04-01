package springframework.beans.factory.support;

import springframework.beans.factory.config.BeanDefinition;

/**
 * 定义：BeanDefinition 对象注册
 * @author gusixue
 * @date 2023/3/26
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String beanName);
}
