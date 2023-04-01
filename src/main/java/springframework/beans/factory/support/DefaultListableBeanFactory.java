package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * 核心实现类：实现类获取 BeanDefinition 对象、注册 BeanDefinition 对象
 * @author gusixue
 * @date 2023/3/26
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry{

    // 缓存 Bean 对象唯一 id 和 BeanDefinition 对象的容器
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 获取 BeanDefinition 对象
     * @param beanName Bean 对象唯一 id
     * @return 返回 BeanDefinition 对象
     * @throws BeansException 获取不到 BeanDefinition 对象
     */
    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    /**
     * 注册 BeanDefinition 对象
     * @param beanName Bean 对象唯一 id
     * @param beanDefinition BeanDefinition 对象
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * BeanDefinition 对象容器是否存在 beanName 的名字
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
}
