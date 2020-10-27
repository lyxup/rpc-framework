package top.liuyuexin.rpc;

import top.liuyuexin.rpc.entity.RpcRequest;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 17:00
 *
 * 客户端通用类接口
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

}
