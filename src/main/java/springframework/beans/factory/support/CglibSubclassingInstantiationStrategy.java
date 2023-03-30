package springframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实现：创建有参 Bean 对象（无论是否有参），使用 Cglib 代理
 * @author gusixue
 * @date 2023/3/27
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    /**
     * 通过 Cglib 代理实例化 Bean 对象
     * @param beanName Bean 对象唯一 id
     * @param beanDefinition 解耦封装 Bean 对象
     * @param ctor Bean 对象构造器
     * @param args Bean 对象参数
     * @return 实例化 Bean 对象
     * @throws BeansException 实例化 Bean 对象异常
     */
    @Override
    public Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor ctor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
//        enhancer.setCallback(new NoOp() {
//            @Override
//            public int hashCode() {
//                return super.hashCode();
//            }
//        });

        enhancer.setCallback(NoOp.INSTANCE);

        // 创建的是代理类，代理类继承父类同时不去重写父类对应方法，调用方法时直接调用父类的方法
        if (null == ctor) {
            return enhancer.create();
        }
        return enhancer.create(ctor.getParameterTypes(), args);
    }

}
