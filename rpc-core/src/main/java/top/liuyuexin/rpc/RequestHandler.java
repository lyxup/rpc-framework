package top.liuyuexin.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.liuyuexin.rpc.entity.RpcRequest;
import top.liuyuexin.rpc.entity.RpcResponse;
import top.liuyuexin.rpc.enumeration.ResponseCode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author LiuYueXin
 * @data 2020/9/15 19:11
 *
 * 通过反射方法进行调用
 */
public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(RpcRequest rpcRequest, Object service){
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest, service);
            logger.info("服务：{}成功调用方法：{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error("调用或发送时有错误发生：", e);
        } return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FIND, rpcRequest.getRequestId());
        }
        return method.invoke(service, rpcRequest.getParameters());
    }

}
