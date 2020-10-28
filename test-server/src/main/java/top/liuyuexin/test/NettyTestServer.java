package top.liuyuexin.test;

import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.netty.server.NettyServer;
import top.liuyuexin.rpc.registry.DefaultServiceRegistry;
import top.liuyuexin.rpc.registry.ServiceRegistry;
import top.liuyuexin.rpc.serializer.KryoSerializer;
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
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.setSerializer(new ProtobufSerializer());
        server.start(9999);
    }

}
