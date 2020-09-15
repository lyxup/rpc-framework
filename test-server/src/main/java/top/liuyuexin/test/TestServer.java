package top.liuyuexin.test;

import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.registry.DefaultServiceRegistry;
import top.liuyuexin.rpc.registry.ServiceRegistry;
import top.liuyuexin.rpc.server.RpcServer;

/**
 * @Author LiuYueXin
 * @data 2020/9/14 16:40
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}