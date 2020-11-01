package top.liuyuexin.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author LiuYueXin
 * @data 2020/9/17 10:41
 */
@AllArgsConstructor
@Getter
public enum PackageType {
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
