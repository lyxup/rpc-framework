package top.liuyuexin.test;

import top.liuyuexin.rpc.RpcClient;
import top.liuyuexin.rpc.RpcClientProxy;
import top.liuyuexin.rpc.api.HelloObject;
import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.netty.client.NettyClient;
import top.liuyuexin.rpc.serializer.KryoSerializer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:42
 *
 * 测试Netty消费者
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new KryoSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(13, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);

    }

}
