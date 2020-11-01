package top.liuyuexin.test;

import top.liuyuexin.rpc.annotation.ServiceScan;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.transport.RpcServer;
import top.liuyuexin.rpc.transport.socket.server.SocketServer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:50
 *
 * 测试服务提供方（服务端）
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERALIZER);
        server.start();
    }
}
