package top.liuyuexin.test;

import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.transport.netty.server.NettyServer;
import top.liuyuexin.rpc.serializer.ProtobufSerializer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:49
 *
 * 测试Netty服务提供者（服务端）
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new ProtobufSerializer());
        server.publishService(helloService, HelloService.class);
    }

}
