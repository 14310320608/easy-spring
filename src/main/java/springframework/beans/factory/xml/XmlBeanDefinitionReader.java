package springframework.beans.factory.xml;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import springframework.beans.BeansException;
import springframework.beans.PropertyValue;
import springframework.beans.PropertyValues;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanReference;
import springframework.beans.factory.support.AbstractBeanDefinitionReader;
import springframework.beans.factory.support.BeanDefinitionRegistry;
import springframework.core.io.Resource;
import springframework.core.io.ResourceLoader;
import springframework.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gusixue
 * @description 解析 XML 文件、获取 Bean 配置以及配置中的 id、name、class、value、ref，设置属性与注册 BeanDefinition
 * @date 2023/4/1
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {


    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);

        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        Assert.notNull(resources, "Resource array must not be null");

        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        Assert.notNull(location, "location must not be null");

        ResourceLoader resourceLoader = getResourceLoader();
        Assert.notNull(resourceLoader, "resourceLoader must not be null");

        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }


    /**
     * 解析 xml 并设置参数，注册 Bean
     */
    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            // 开头必须是 bean 标签
            if (!(nodeList.item(i) instanceof Element) || !"bean".equals(nodeList.item(i).getNodeName())) {
                continue;
            }

            // 拆分标签
            Element bean = (Element) nodeList.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String beanName = StringUtils.isEmpty(id) ? name : id;
            Assert.notNull(beanName, "id or name must not all be null");

            String className = bean.getAttribute("class");
            Class<?> clazz = Class.forName(className);

            BeanDefinition beanDefinition = new BeanDefinition(clazz, new PropertyValues());

            // 填充属性
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                // 子标签开头必须是 property 标签
                if (!(bean.getChildNodes().item(j) instanceof Element)
                        || !"property".equals(bean.getChildNodes().item(j).getNodeName())) {
                    continue;
                }
                Element property = (Element) bean.getChildNodes().item(j);
                String propertyName = property.getAttribute("name");
                String propertyVal = property.getAttribute("value");
                String propertyRef = property.getAttribute("ref");

                Object objValue = StringUtils.isEmpty(propertyRef) ? propertyVal : new BeanReference(propertyRef);
                PropertyValue propertyValue = new PropertyValue(propertyName, objValue);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            // XML 配置有问题，快速失败
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            // 注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);

        }
    }
}
