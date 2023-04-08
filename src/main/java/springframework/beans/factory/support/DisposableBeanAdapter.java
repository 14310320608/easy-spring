package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.DisposableBean;
import springframework.beans.factory.config.BeanDefinition;
import springframework.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * @author gusixue
 * @description 实现｛@link DisposableBean｝接口的适配器，在给定的 bean 对象上执行各种销毁步骤
 * @date 2023/4/8
 */
public class DisposableBeanAdapter implements DisposableBean {

    private String beanName;
    private Object bean;
    private BeanDefinition beanDefinition;

    public DisposableBeanAdapter(String beanName, Object bean, BeanDefinition beanDefinition) {
        this.beanName = beanName;
        this.bean = bean;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public void destroy() throws Exception {
        // 执行 DisposableBean
        if (bean instanceof DisposableBean) {
            ((DisposableBean)bean).destroy();
        }

        // 执行 destroy-method，避免过两次销毁
        String destroyMethodName = beanDefinition.getDestroyMethodName();
        boolean destroyMethodFlag = StringUtils.isNotEmpty(destroyMethodName);
        boolean nonRepeatDestroyMethodFlag = !(bean instanceof DisposableBean
                && "destroy".equals(destroyMethodName));
        if (destroyMethodFlag && nonRepeatDestroyMethodFlag) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Could not find an destroy method named '" + destroyMethodName
                        + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
