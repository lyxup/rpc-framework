package top.liuyuexin.rpc;

import top.liuyuexin.rpc.serializer.CommonSerializer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 17:06
 *
 * 服务器类通用接口
 */
public interface RpcServer {

    void start(int port);

    void setSerializer(CommonSerializer serializer);

}
