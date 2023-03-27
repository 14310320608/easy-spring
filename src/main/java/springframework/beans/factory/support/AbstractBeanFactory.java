package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.BeanFactory;
import springframework.beans.factory.config.BeanDefinition;

import java.beans.Beans;

/**
 * 核心抽象类：实现获取 Bean 对象、定义获取 BeanDefinition 对象、定义创建 Bean 对象
 * @author gusixue
 * @date 2023/3/26
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object bean = getSingletonBean(beanName);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);

        return createBean(beanName, beanDefinition);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
