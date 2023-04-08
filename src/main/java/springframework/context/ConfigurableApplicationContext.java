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

    /**
     * 注册 Bean 容器销毁的虚拟机钩子
     */
    void registerShutdownHook();

    /**
     * 手动执行关闭
     */
    void close();
}
