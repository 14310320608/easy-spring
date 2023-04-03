package springframework.context.support;

import springframework.beans.BeansException;

import java.util.Map;

/**
 * @author gusixue
 * @description 具体对外提供应用上下文的核心类，实现了 getConfigLocations 方法
 * @date 2023/4/3
 */
public class ClassPathXmlApplicationContext extends AbstractXMLApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 加载 XML 文件、刷新上下文
     */
    public ClassPathXmlApplicationContext(String configLocations) {
        this(new String[]{configLocations});
    }

    /**
     * 加载 XML 文件、刷新上下文
     */
    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}
