package springframework.beans.factory.config;

import springframework.beans.PropertyValues;

/**
 * 定义：Bean 对象的 Class 以及属性信息
 * @author gusixue
 * @date 2023/3/26
 */
public class BeanDefinition {

    // Bean 对象的 Class
    private Class beanClass;

    // Bean 对象的所有属性信息
    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass +
                ", propertyValues=" + propertyValues +
                '}';
    }
}
