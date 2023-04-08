package springframework.context.support;

import springframework.beans.BeansException;
import springframework.beans.factory.BeanFactory;
import springframework.beans.factory.config.BeanFactoryPostProcessor;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.beans.factory.config.ConfigurableListableBeanFactory;
import springframework.context.ConfigurableApplicationContext;
import springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @author gusixue
 * @description 实现了 refresh、invokeBeanFactoryPostProcessors、registerBeanPostProcessors 方法
 *              定义了 refreshBeanFactory、getBeanFactory 方法
 * @date 2023/4/3
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {


    /**
     * 刷新容器
     */
    @Override
    public void refresh() throws BeansException {
        // 创建 BeanFactory，加载 XML，注册 BeanDefinition
        refreshBeanFactory();

        // 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // Bean 对象实例化之前执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // Bean 对象实例化之前注册 BeanPostProcessor
        registerBeanPostProcessors(beanFactory);

        // 预加载所有非懒加载的单例 Bean 对象（实例化、属性填充、初始化及扩展），默认使用空参构造器
        beanFactory.preInstantiateSingletons();

    }

    protected abstract void refreshBeanFactory();

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {

            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }

    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }


    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }
}
