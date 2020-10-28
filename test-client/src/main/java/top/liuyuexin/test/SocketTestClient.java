package top.liuyuexin.test;

import top.liuyuexin.rpc.api.HelloObject;
import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.RpcClientProxy;
import top.liuyuexin.rpc.serializer.KryoSerializer;
import top.liuyuexin.rpc.socket.client.SocketClient;

/**
 * @Author LiuYueXin
 * @data 2020/9/14 16:34
 *
 * 测试消费者（客户端）
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9999);
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }

}
