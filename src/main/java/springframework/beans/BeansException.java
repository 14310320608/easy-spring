package springframework.beans;


import springframework.beans.factory.NestedRuntimeException;

/**
 * bean 包的总异常，
 * 它是运行时异常
 * @author gusixue
 * @date 2023/3/26
 */
public class BeansException extends NestedRuntimeException {


    public BeansException(String message) {
        super(message);
    }

}
