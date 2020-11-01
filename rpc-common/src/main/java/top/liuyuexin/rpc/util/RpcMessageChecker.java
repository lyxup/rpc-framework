package top.liuyuexin.rpc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.liuyuexin.rpc.entity.RpcRequest;
import top.liuyuexin.rpc.entity.RpcResponse;
import top.liuyuexin.rpc.enumeration.ResponseCode;
import top.liuyuexin.rpc.enumeration.RpcError;
import top.liuyuexin.rpc.exception.RpcException;

/**
 * @Author LiuYueXin
 * @data 2020/10/28 11:35
 *
 * 检查响应与请求
 */
public class RpcMessageChecker {
    private static final Logger logger = LoggerFactory.getLogger(RpcMessageChecker.class);
    public static final String INTERFACE_NAME = "interfaceName";

    private RpcMessageChecker(){}

    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse) {
        if (rpcResponse == null) {
            logger.error("服务调用失败，serviceName：{}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())) {
            logger.error("调用服务失败,serviceName:{},RpcResponse:{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }
}
