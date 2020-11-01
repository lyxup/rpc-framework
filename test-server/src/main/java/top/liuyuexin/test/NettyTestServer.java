package top.liuyuexin.test;

import top.liuyuexin.rpc.annotation.ServiceScan;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.transport.RpcServer;
import top.liuyuexin.rpc.transport.netty.server.NettyServer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:49
 *
 * 测试Netty服务提供者（服务端）
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
