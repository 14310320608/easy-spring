package springframework.test.common;

import springframework.beans.BeansException;
import springframework.beans.PropertyValue;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanFactoryPostProcessor;
import springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author gusixue
 * @description
 * @date 2023/4/3
 */
public class MyBean1FactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("sex", 1));

    }
}
