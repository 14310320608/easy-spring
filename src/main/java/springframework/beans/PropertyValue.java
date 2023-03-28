package springframework.beans;

/**
 * 定义 Bean 对象的属性名与与属性值
 * @author gusixue
 * @date 2023/3/28
 */
public class PropertyValue {

    // Bean 对象属性名
    private String name;

    // Bean 对象具体属性值
    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PropertyValue{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
