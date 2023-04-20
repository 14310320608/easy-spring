package springframework.context.support;

import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.context.ApplicationContext;
import springframework.context.ApplicationContextAware;

/**
 * @author gusixue
 * @description 包装处理器：在 refresh() 方法操作时，把 ApplicationContext 注册到 BeanPostProcessor 容器
 *                        在 createBean() 方法进行 BeanPostProcessor Before 处理时调用
 * @date 2023/4/20
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware)bean).setApplicationContext(applicationContext);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
