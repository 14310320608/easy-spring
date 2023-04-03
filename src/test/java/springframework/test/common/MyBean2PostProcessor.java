package springframework.test.common;

import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.test.bean.UserController;

/**
 * @author gusixue
 * @description
 * @date 2023/4/3
 */
public class MyBean2PostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userController".equals(beanName)) {
            UserController userController = (UserController)bean;
            userController.setName("替换成辜思学3");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("userController".equals(beanName)) {
            UserController userController = (UserController)bean;
            userController.setName("替换成辜思学4");
        }

        return bean;
    }
}
