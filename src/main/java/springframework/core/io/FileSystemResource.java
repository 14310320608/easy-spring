package springframework.core.io;

import cn.hutool.core.lang.Assert;
import springframework.utils.StringUtils;

import java.io.*;

/**
 * @author gusixue
 * @description FileSystem 流文件获取数据
 * @date 2023/4/1
 */
public class FileSystemResource implements Resource {

    private final String path;

    private final File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "Path must not be null");
        this.path = StringUtils.cleanPath(path);
        this.file = new File(path);
    }

    public FileSystemResource(File file) {
        Assert.notNull(file, "File must not be null");
        this.path = StringUtils.cleanPath(file.getPath());
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }
}
