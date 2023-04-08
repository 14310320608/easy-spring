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

    // 初始化方法名字
    private String initMethodName;

    // 销毁方法名字
    private String destroyMethodName;

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

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass +
                ", propertyValues=" + propertyValues +
                ", initMethodName='" + initMethodName + '\'' +
                ", destroyMethodName='" + destroyMethodName + '\'' +
                '}';
    }
}
