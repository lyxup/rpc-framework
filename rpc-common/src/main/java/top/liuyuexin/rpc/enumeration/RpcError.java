package top.liuyuexin.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author LiuYueXin
 * @data 2020/9/15 12:41
 *
 * RPC调用过程中的错误
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_CAN_NOT_BE_NULL("注册服务不得为空"),
    SERVICE_NOT_FOUND("找不到对应服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口"),
    UNKNOWN_PROTOCOL("不识别的协议包"),
    UNKNOWN_SERIALAZER("不认识的（反）序列化器"),
    UNKNOWN_PACKAGE_TYPE("不识别的数据报类型"),
    SERIALIZER_NOT_FOUND("找不到序列化器");

    private final String message;

}
