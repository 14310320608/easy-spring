package springframework.utils;

import java.beans.Introspector;
import java.io.Closeable;
import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author gusixue
 * @description 框架内 {@code java.lang.Class} 的工具类
 * @date 2023/4/1
 */
public abstract class ClassUtils {

    /**
     * @return 返回要使用的默认类加载器
     */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			// 无法访问线程上下文类加载器 - 返回
		}
		if (cl == null) {
			// 没有线程上下文类加载器 -> 使用本类的类加载器
			cl = ClassUtils.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() 返回 null 表示 bootstrap 类加载器
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch (Throwable ex) {
				    // 无法访问系统系统类加载器，返回 null
				}
			}
		}
		return cl;
	}
}
