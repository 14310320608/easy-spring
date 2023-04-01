package springframework.beans.factory;

/**
 * @author gusixue
 * @description 扩展了 BeanFactory，提供了父容器访问功能的方法
 * @date 2023/4/1
 */
public interface HierarchicalBeanFactory extends BeanFactory {

    /**
     * @return 返回 BeanFactory 的父类，允许为 null
     */
    BeanFactory getParentBeanFactory();
}
