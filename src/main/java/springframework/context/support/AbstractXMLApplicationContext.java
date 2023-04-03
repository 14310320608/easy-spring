package springframework.context.support;

import springframework.beans.factory.support.DefaultListableBeanFactory;
import springframework.beans.factory.xml.XmlBeanDefinitionReader;
import springframework.core.io.ResourceLoader;

/**
 * @author gusixue
 * @description 实现了 loadBeanDefinitions 方法，定义了 getConfigLocations 方法
 * @date 2023/4/3
 */
public abstract class AbstractXMLApplicationContext extends AbstractRefreshableApplicationContext {

    /**
     * 加载 XML，注册 BeanDefinition
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);

        String[] locations = getConfigLocations();
        if (null != locations) {
            xmlBeanDefinitionReader.loadBeanDefinitions(locations);
        }
    }

    protected abstract String[] getConfigLocations();
}
