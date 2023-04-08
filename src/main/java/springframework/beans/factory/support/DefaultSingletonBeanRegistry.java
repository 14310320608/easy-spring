package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.DisposableBean;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 实现：获取单例 Bean 对象、注册单例 Bean 对象
 * @author gusixue
 * @date 2023/3/26
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 缓存 Bean 对象唯一 id 和单例 Bean 对象的容器
    private Map<String, Object> singletonBeanMap = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 获取单例 Bean 对象
     * @param beanName Bean 对象唯一 id
     * @return 返回单例 Bean 对象
     */
    @Override
    public Object getSingletonBean(String beanName) {
        return singletonBeanMap.get(beanName);
    }

    /**
     * 注册单例 Bean 对象
     * @param beanName Bean 对象唯一 id
     * @param singletonBean 单例 Bean 对象
     */
    protected void addSingletonBean(String beanName, Object singletonBean) {
        singletonBeanMap.put(beanName, singletonBean);
    }

    /**
     * 注册 DisposableBean 与 destroy-method 的对象
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    /**
     * 触发 destroy 方法，并销毁单例对象
     * 实现子类 AbstractBeanFactory 实现的 ConfigurableBeanFactory 接口的抽象方法，用于解耦合与分配职责
     */
    public void destroySingletons() {
        String[] disposableBeanNames = disposableBeans.keySet().toArray(new String[0]);

        for (String disposableBeanName : disposableBeanNames) {
            DisposableBean disposableBean = disposableBeans.remove(disposableBeanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + disposableBeanName + "' threw an exception", e);
            }
        }
    }
}
