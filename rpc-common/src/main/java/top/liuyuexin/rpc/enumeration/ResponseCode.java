package top.liuyuexin.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author LiuYueXin
 * @data 2020/9/13 22:46
 *
 * 方法调用的响应状态码
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    METHOD_NOT_FIND(500, "未找到指定方法"),
    CLASS_NOT_FIND(500, "未找到指定类");

    private final int code;
    private final String message;

}
