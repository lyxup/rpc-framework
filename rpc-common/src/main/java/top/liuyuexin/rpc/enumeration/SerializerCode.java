package top.liuyuexin.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author LiuYueXin
 * @data 2020/9/17 10:48
 *
 * 字节流标识序列化和反序列化器
 */

@AllArgsConstructor
@Getter
public enum SerializerCode {

    JSON(1);

    private final int code;
}
