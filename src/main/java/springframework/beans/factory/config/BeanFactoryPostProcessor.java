package springframework.beans.factory.config;

import springframework.beans.BeansException;

/**
 * @author gusixue
 * @description BeanDefinition 注册后、初始化之前的操作，可以修改 BeanDefinition 的信息
 * @date 2023/4/3
 */
public interface BeanFactoryPostProcessor {

    /**
     * BeanDefinition 注册后、初始化之前的操作，可以修改 BeanDefinition 的信息
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
