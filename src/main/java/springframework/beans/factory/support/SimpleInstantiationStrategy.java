package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 实现：创建有参 Bean 对象（无论是否有参），使用 JDK 反射
 * @author gusixue
 * @date 2023/3/27
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor ctor, Object[] args) throws BeansException {
        Object bean = null;

        Class clazz = beanDefinition.getBeanClass();

        try {
            if (null == args) {
                bean = clazz.getDeclaredConstructor().newInstance();

            } else {
                bean = clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);

            }
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]");
        }

        return bean;
    }
}
