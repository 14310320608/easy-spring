package springframework.context.support;

import springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author gusixue
 * @description 创建与获取 DefaultListableBeanFactory、实现 refreshBeanFactory 方法，定义 loadBeanDefinitions 方法
 * @date 2023/4/3
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 创建 BeanFactory，加载 XML，注册 BeanDefinition
     */
    @Override
    protected void refreshBeanFactory() {
        this.beanFactory = createBeanFactory();
        // 加载 XML，注册 BeanDefinition
        loadBeanDefinitions(this.beanFactory);
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
