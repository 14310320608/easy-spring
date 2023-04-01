package springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gusixue
 * @description 处理资源加载流，提供获取 InputStream 流的方法，也可看做 classPath、FileSystem、URL 三种方式获取流的适配器
 * @date 2023/4/1
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
