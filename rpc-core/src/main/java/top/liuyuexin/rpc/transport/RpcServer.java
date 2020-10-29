package top.liuyuexin.rpc.transport;

import top.liuyuexin.rpc.serializer.CommonSerializer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 17:06
 *
 * 服务器类通用接口
 */
public interface RpcServer {

    void start();

    void setSerializer(CommonSerializer serializer);

    <T> void publishService(T service, Class<T> serviceClass);

}
