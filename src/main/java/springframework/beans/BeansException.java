package springframework.beans;

/**
 * bean 包的总异常，运行时异常
 * @author gusixue
 * @date 2023/3/26
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
