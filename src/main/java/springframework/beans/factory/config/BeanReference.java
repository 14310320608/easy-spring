package springframework.beans.factory.config;

/**
 * 定义依赖注入时属于一个 Bean 对象
 * @author gusixue
 * @date 2023/3/28
 */
public class BeanReference {

    private String propertyName;

    public BeanReference(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        return "BeanReference{" +
                "propertyName='" + propertyName + '\'' +
                '}';
    }
}
