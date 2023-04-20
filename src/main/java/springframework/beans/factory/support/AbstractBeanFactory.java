package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.BeanFactory;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.beans.factory.config.ConfigurableBeanFactory;
import springframework.utils.ClassUtils;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心抽象类：实现获取 Bean 对象、定义获取 BeanDefinition 对象、定义创建 Bean 对象
 * @author gusixue
 * @date 2023/3/26
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * 获取无参 Bean 对象
     * 初始化时：实例化 Bean 对象，Bean 对象属性填充（依赖注入）
     * @param beanName Bean 对象唯一 id
     * @return 返回创建好的 Bean 对象
     * @throws BeansException 创建 Bean 对象失败
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    /**
     * 获取有参 Bean 对象
     * 初始化时：实例化 Bean 对象，Bean 对象属性填充（依赖注入）
     * @param beanName Bean 对象唯一 id
     * @param args Bean 对象参数
     * @return 返回创建好的有参 Bean 对象
     * @throws BeansException 创建 Bean 对象失败
     */
    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    private Object doGetBean(String beanName, Object[] args) throws BeansException {
        Object bean = getSingletonBean(beanName);
        if (null != bean) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);

        return createBean(beanName, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException;


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessorList.remove(beanPostProcessor);
        beanPostProcessorList.add(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return beanPostProcessorList.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessorList() {
        return this.beanPostProcessorList;
    }
}
