package top.liuyuexin.test;

import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.transport.socket.server.SocketServer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:50
 *
 * 测试服务提供方（服务端）
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }

}
