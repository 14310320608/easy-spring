package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 核心实现类：实现类获取 BeanDefinition 对象、注册 BeanDefinition 对象
 * @author gusixue
 * @date 2023/3/26
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    // 缓存 Bean 对象唯一 id 和 BeanDefinition 对象的容器
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 获取 BeanDefinition 对象
     * @param beanName Bean 对象唯一 id
     * @return 返回 BeanDefinition 对象
     * @throws BeansException 获取不到 BeanDefinition 对象
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(beanName -> {
            getBean(beanName);
        });
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

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> beanMap = new HashMap<>();

        beanDefinitionMap.entrySet().forEach(beanDefinitionEntry ->  {
            String beanName = beanDefinitionEntry.getKey();
            if (type.isAssignableFrom(beanDefinitionEntry.getValue().getBeanClass())) {
                beanMap.put(beanName, (T)getBean(beanName));
            }
        });
        return beanMap;
    }
}
