package springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装了 PropertyValue 对象
 * @author gusixue
 * @date 2023/3/28
 */
public class PropertyValues {

    private List<PropertyValue> propertyValues;

    public PropertyValues() {
        this.propertyValues = new ArrayList<>();
    }

    public PropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues != null ? propertyValues : new ArrayList<>();
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public String toString() {
        return "PropertyValues{" +
                "propertyValues=" + propertyValues +
                '}';
    }
}
