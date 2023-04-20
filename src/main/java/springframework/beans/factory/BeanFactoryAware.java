package springframework.beans.factory;

import springframework.beans.BeansException;

/**
 * @author gusixue
 * @description 实现此接口，就能感知到所属的 BeanFactory
 * @date 2023/4/20
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
