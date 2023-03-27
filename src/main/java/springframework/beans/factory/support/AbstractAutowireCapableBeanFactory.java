package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanDefinition;

/**
 * 实现：创建 Bean 对象
 * @author gusixue
 * @date 2023/3/26
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;

        try {
            bean = beanDefinition.getBeanClass().newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        addSingleBean(beanName, bean);
        return bean;
    }
}
