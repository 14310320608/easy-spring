package springframework.core.io;

/**
 * @author gusixue
 * @description 资源加载器接口
 * @date 2023/4/1
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);

}
