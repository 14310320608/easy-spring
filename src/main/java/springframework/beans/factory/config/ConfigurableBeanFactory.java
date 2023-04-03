package springframework.beans.factory.config;

import springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @author gusixue
 * @description 注册与获取 BeanPostProcessor、BeanClassLoader 等的一个配置化接口
 * @date 2023/4/1
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     *
     * 添加新的后置处理器，应用于 Bean 对象
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * @return 返回当前注册的后置处理器数量
     */
    int getBeanPostProcessorCount();
}
