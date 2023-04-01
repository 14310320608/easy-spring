package springframework.beans.factory;

import springframework.beans.BeansException;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author gusixue
 * @description 扩展 BeanFactory 的接口，提供了所有的 Bean 对象基本信息的方法
 * @date 2023/3/30
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * @return 返回 BeanDefinition 名称列表
     */
    String[] getBeanDefinitionNames();

    /**
     * @return 返回指定类型的 Bean 实例的 Map
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

}
