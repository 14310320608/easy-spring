package springframework.context;

import springframework.beans.BeansException;

/**
 * @author gusixue
 * @description 定义了核心方法 refresh 方法、用于刷新容器
 * @date 2023/4/3
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     */
    void refresh() throws BeansException;
}
