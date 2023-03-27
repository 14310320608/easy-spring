package springframework.beans.factory.support;

import springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现：获取单例 Bean 对象、注册单例 Bean 对象
 * @author gusixue
 * @date 2023/3/26
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonBeanMap = new HashMap<>();

    @Override
    public Object getSingletonBean(String beanName) {
        return singletonBeanMap.get(beanName);
    }

    protected void addSingleBean(String beanName, Object singletonBean) {
        singletonBeanMap.put(beanName, singletonBean);
    }
}
