package springframework.context;

import springframework.beans.BeansException;
import springframework.beans.factory.Aware;

/**
 * @author gusixue
 * @description 实现此接口，就能感知到所属的 ApplicationContext
 * @date 2023/4/20
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
