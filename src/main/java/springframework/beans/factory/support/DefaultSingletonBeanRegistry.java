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

    // 缓存 Bean 对象唯一 id 和单例 Bean 对象的容器
    private Map<String, Object> singletonBeanMap = new HashMap<>();

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
}
