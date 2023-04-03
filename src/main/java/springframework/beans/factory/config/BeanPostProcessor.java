package springframework.beans.factory.config;

import springframework.beans.BeansException;

/**
 * @author gusixue
 * @description Bean 初始化前后均可操作，可以扩展 Bean 功能甚至替换 Bean 对象，与 AOP 有紧密联系
 * @date 2023/4/1
 */
public interface BeanPostProcessor {

    /**
     * Bean 初始化之前执行
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * Bean 初始化之后执行
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
