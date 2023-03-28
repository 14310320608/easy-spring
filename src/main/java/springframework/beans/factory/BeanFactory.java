package springframework.beans.factory;

import springframework.beans.BeansException;

/**
 * 核心接口：定义获取 Bean 对象
 * @author gusixue
 * @date 2023/3/26
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    Object getBean(String beanName, Object... args) throws BeansException;

}
