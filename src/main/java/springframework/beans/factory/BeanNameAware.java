package springframework.beans.factory;

/**
 * @author gusixue
 * @description 实现此接口，就能感知到所属的 BeanName
 * @date 2023/4/20
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String beanName);
}
