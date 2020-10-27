package top.liuyuexin.rpc.exception;

import top.liuyuexin.rpc.enumeration.RpcError;

/**
 * @Author LiuYueXin
 * @data 2020/9/15 12:47
 *
 * RPC异常调用
 */
public class RpcException extends RuntimeException{

    public RpcException(RpcError error, String detail){
        super(error.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause){
        super(message, cause);
    }

    public RpcException(RpcError error){
        super(error.getMessage());
    }

}
