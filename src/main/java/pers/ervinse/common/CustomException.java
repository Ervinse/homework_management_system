package pers.ervinse.common;

/**
 * 业务异常
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
