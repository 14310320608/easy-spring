package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.core.io.Resource;
import springframework.core.io.ResourceLoader;

/**
 * @author gusixue
 * @description BeanDefinition 读取接口，定义：getRegistry()、getResourceLoader()，与三个加载 BeanDefinition 的方法
 * @date 2023/4/1
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... location) throws BeansException;

}
