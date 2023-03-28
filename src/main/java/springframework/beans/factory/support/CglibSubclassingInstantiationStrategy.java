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

        if (null == ctor) {
            return enhancer.create();
        }
        return enhancer.create(ctor.getParameterTypes(), args);
    }

}
