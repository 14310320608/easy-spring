package springframework.beans.factory.config;

/**
 * 定义：Bean 对象的 Class 信息
 * @author gusixue
 * @date 2023/3/26
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
