package top.liuyuexin.test;

import top.liuyuexin.rpc.registry.DefaultServiceRegistry;
import top.liuyuexin.rpc.registry.ServiceRegistry;
import top.liuyuexin.rpc.socket.server.SocketServer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:50
 *
 * 测试服务提供方（服务端）
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}
