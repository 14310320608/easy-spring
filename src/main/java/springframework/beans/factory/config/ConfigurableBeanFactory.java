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

    /**
     * @return 返回类加载器，如果系统类加载器不可访问返回 null
     */
    ClassLoader getBeanClassLoader();

    /**
     * 设置用于加载 Bean 对象的类加载器，默认值是线程上下文类加载器
     */
    void setBeanClassLoader(ClassLoader beanClassLoader);
}
