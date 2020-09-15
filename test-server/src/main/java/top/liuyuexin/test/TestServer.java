package top.liuyuexin.test;

import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.server.RpcServer;

/**
 * @Author LiuYueXin
 * @data 2020/9/14 16:40
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}
