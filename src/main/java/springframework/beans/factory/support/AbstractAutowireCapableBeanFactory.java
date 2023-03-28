package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实现：创建 Bean 对象
 * @author gusixue
 * @date 2023/3/26
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

//    InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();
    InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean = null;

        try {
            bean = createBeanInstance(beanName, beanDefinition, args);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BeansException("Instantiation of bean failed");
        }

        addSingleBean(beanName, bean);
        return bean;
    }

    private Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Constructor ctor = null;
        Constructor<?>[] constructors = beanDefinition.getBeanClass().getConstructors();
        // 根据传入参数选择最匹配的构造器
        if (null != args) {
            for (Constructor constructor : constructors) {
                if (null != constructor && constructor.getParameterCount() == args.length) {
                    ctor = constructor;
                    break;
                }
            }
        }

        return instantiationStrategy.instantiate(beanName, beanDefinition, ctor, args);
    }
}
