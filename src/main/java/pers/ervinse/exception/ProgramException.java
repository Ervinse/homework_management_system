package pers.ervinse.exception;

/**
 * 程序异常
 */
public class ProgramException extends RuntimeException {

    public ProgramException(String message) {
        super(message);
    }

    public ProgramException() {
        super("服务器错误!");
    }
}
