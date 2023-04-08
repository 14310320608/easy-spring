package springframework.beans.factory;

/**
 * @author gusixue
 * @description 定义 Bean 容器销毁前执行的接口
 * @date 2023/4/8
 */
public interface DisposableBean {

    /**
     * Bean 销毁前，destroy-method 执行前执行的接口
     * @throws Exception
     */
    void destroy() throws Exception;
}
