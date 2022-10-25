package pers.ervinse.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    public static <T> R<T> getSuccessInstance(T object) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static  R<String> getSuccessOperationInstance() {
        R<String> r = new R<>();
        r.data = "success";
        r.code = 1;
        return r;
    }

    public static <T> R<T> getErrorInstance(String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

}
