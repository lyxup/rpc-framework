package top.liuyuexin.test;

import top.liuyuexin.rpc.api.ByeService;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.transport.RpcClient;
import top.liuyuexin.rpc.transport.RpcClientProxy;
import top.liuyuexin.rpc.api.HelloObject;
import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.transport.netty.client.NettyClient;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:42
 *
 * 测试Netty消费者
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.PROTOBUF_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(13, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }

}
