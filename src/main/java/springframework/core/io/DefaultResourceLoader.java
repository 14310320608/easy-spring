package springframework.core.io;


import cn.hutool.core.lang.Assert;
import jdk.nashorn.api.scripting.URLReader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author gusixue
 * @description 资源加载器实现类，通过策略模式：外部传入地址即可获取对应 Resource，而不用管具体那种流文件
 * @date 2023/4/1
 */
public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");

        if (location.startsWith("/")) {
            return new FileSystemResource(location);

        } else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));

        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);

            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
