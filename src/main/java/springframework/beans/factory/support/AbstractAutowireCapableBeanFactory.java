package springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import springframework.beans.BeansException;
import springframework.beans.PropertyValue;
import springframework.beans.PropertyValues;
import springframework.beans.factory.config.AutowireCapableBeanFactory;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 实现：创建 Bean 对象，依赖注入（暂未考虑循环依赖）
 * @author gusixue
 * @date 2023/3/26
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    // 使用 JDK 进行 Bean 对象的实例化
//    InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    // 使用 Cglib 进行 Bean 对象的实例化
    InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     * 实例化 Bean 对象，Bean 对象属性填充（依赖注入）
     * @param beanName Bean 对象唯一 id
     * @param beanDefinition 解耦封装 Bean 对象
     * @param args Bean 对象参数
     * @return 返回已经创建好的 Bean 对象
     * @throws BeansException 创建 Bean 对象失败或者依赖注入失败
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean = null;

        try {
            // 实例化 Bean 对象
            bean = createBeanInstance(beanName, beanDefinition, args);

            // Bean 对象属性填充（依赖注入）
            applyPropertyValues(beanName, bean, beanDefinition);

            // 初始化 Bean 对象以及扩展
            bean = initializeBean(beanName, bean, beanDefinition);

        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingletonBean(beanName, bean);
        return bean;
    }

    /**
     * 选择正确的构造器，通过策略类创建 Bean 对象
     * @param beanName Bean 对象唯一 id
     * @param beanDefinition 解耦封装 Bean 对象
     * @param args Bean 对象参数
     * @return 返回已经创建好的 Bean 对象
     */
    private Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Constructor ctor = null;
        Constructor<?>[] constructors = beanDefinition.getBeanClass().getConstructors();
        /**
         * 简化：根据传入参数选择最匹配的构造器
         * Spring 实际会使用这个：getBean 传入参数与 BeanDefinition 中所有构造器权重相差最小的就使用那个构造器
         * int typeDiffWeight = (mbd.isLenientConstructorResolution() ?
         * 						argsHolder.getTypeDifferenceWeight(paramTypes) : argsHolder.getAssignabilityWeight(paramTypes));
         */
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

    /**
     * Bean 对象属性填充（依赖注入）
     * @param beanName Bean 对象唯一 id
     * @param bean 创建好的 Bean 对象
     * @param beanDefinition 解耦封装 Bean 对象
     */
    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {

            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (propertyValues != null && propertyValues.getPropertyValues() != null
                    && propertyValues.getPropertyValues().size() > 0) {

                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

                    String propertyName = propertyValue.getName();
                    Object propertyValueObj = propertyValue.getValue();

                    if (propertyValueObj instanceof BeanReference) {

                        BeanReference beanReference = (BeanReference)propertyValueObj;
                        /**
                         * 注意这儿为了方便获取 Bean 对象没有加参数
                         * 注意 beanReference.getPropertyName() 可能与 propertyName 不一致，propertyName 方法与 setXxx 参数是一致的
                         */
                        propertyValueObj = getBean(beanReference.getPropertyName());
                    }

                    // 直接反射未考虑 Cglib 代理的 Bean 对象，set 方法内部是基本数据类型
//                String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
//                bean.getClass().getMethod(setMethodName, propertyValueObj.getClass())
//                        .invoke(bean, propertyValueObj);

                    BeanUtil.setFieldValue(bean, propertyName, propertyValueObj);
                }
            }

        } catch (BeansException e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
    }

    /**
     * 初始化 Bean 对象以及扩展
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // BeanPostProcessor Before 操作
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // Bean 对象初始化
        invokeInitMethod(wrappedBean, beanName, beanDefinition);

        // BeanPostProcessor After 操作
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethod(Object bean, String beanName, BeanDefinition beanDefinition) throws BeansException {

    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object resultBean = existingBean;

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            Object currentBean = beanPostProcessor.postProcessBeforeInitialization(resultBean, beanName);
            if (null == currentBean) {
                return resultBean;
            }
            resultBean = currentBean;
        }

        return resultBean;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object resultBean = existingBean;

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            Object currentBean = beanPostProcessor.postProcessAfterInitialization(resultBean, beanName);
            if (null == currentBean) {
                return resultBean;
            }
            resultBean = currentBean;
        }

        return resultBean;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
