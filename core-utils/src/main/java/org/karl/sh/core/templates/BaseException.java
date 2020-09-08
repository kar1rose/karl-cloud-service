package org.karl.sh.core.templates;

/**
 *  自定义异常
 * @author KARL.ROSE
 * @date 2019/12/17 8:09 下午
 **/
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}