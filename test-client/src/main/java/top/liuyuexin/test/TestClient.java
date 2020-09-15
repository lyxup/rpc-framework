package top.liuyuexin.test;

import top.liuyuexin.rpc.api.HelloObject;
import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.client.RpcClientProxy;

/**
 * @Author LiuYueXin
 * @data 2020/9/14 16:34
 *
 * 测试消费者（客户端）
 */
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
