# rpc-framework
动手实现一个简单的rpc框架

## 介绍
RPC(Remote Procedure Call)框架可以让客户端直接调用服务端的方法就像调用本地方法一样简单。rpc-framework是一款基于Netty+Kryo+Nacos实现的RPC框架。网络传输实现了基于Java原生Socket与Netty版本，并实现了多种序列化方式和负载均衡算法。

## 架构
一个简单的RPC框架的设计思路：![image](https://github.com/lyxup/rpc-framework/blob/master/images/rpc-architure.png)
服务提供端Server向注册中心注册服务，服务消费者Client通过注册中心拿到服务相关信息，然后再通过网络请求服务端提供Server。

通常情况下，RPC框架不仅要提供服务发现功能，还要提供负载均衡等功能。![image](https://github.com/lyxup/rpc-framework/blob/master/images/rpc-architure-detail.png)

## 特性

- 实现了基于 Java 原生 Socket 传输与 Netty 传输两种网络传输方式
- 实现了四种序列化算法，Json 方式、Kryo 算法、Hessian 算法与 Google Protobuf 方式（默认采用 Kryo方式序列化）
- 实现了两种负载均衡算法：随机算法与轮转算法
- 使用 Nacos 作为注册中心，管理服务提供者信息
消费端如采用 Netty 方式，会复用 Channel 避免多次连接
- 如消费端和提供者都采用 Netty 方式，会采用 Netty 的心跳机制，保证连接
- 实现自定义的通信协议
- 服务提供侧自动注册服务

## 项目模块

- rpc-api：通用服务接口
- rpc-common：存放一些实体对象，枚举类，工具类等
- rpc-core：rpc框架的核心实现
- test-client：测试客户端
- test-server：测试服务端

## 传输协议
调用参数与返回值的传输采用了如下 RF 协议（ rpc-Framework 首字母）以防止粘包：
```
+---------------+---------------+-----------------+-------------+
|  Magic Number |  Package Type | Serializer Type | Data Length |
|    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
+---------------+---------------+-----------------+-------------+
|                          Data Bytes                           |
|                   Length: ${Data Length}                      |
+---------------------------------------------------------------+
```
| 字段            | 解释                                                         |
| :-------------- | :----------------------------------------------------------- |
| Magic Number    | 魔数，表识一个 MRF 协议包，0xCAFEBABE                        |
| Package Type    | 包类型，标明这是一个调用请求还是调用响应                     |
| Serializer Type | 序列化器类型，标明这个包的数据的序列化方式                   |
| Data Length     | 数据字节的长度                                               |
| Data Bytes      | 传输的对象，通常是一个`RpcRequest`或`RpcClient`对象，取决于`Package Type`字段，对象的序列化方式取决于`Serializer Type`字段。 |

## 运行项目
项目启动前确保Nacos运行在本地8848端口。
### 定义调用接口
```java
package top.liuyuexin.rpc.api;

public interface HelloService {

    String hello(HelloObject object);

}
```

### 服务提供者
```java
package top.liuyuexin.test;

import top.liuyuexin.rpc.annotation.ServiceScan;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.transport.RpcServer;
import top.liuyuexin.rpc.transport.netty.server.NettyServer;

@ServiceScan
public class NettyTestServer {

    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }

}
```

### 服务消费者进行远程过程调用
```java
package top.liuyuexin.test;

import top.liuyuexin.rpc.api.ByeService;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.transport.RpcClient;
import top.liuyuexin.rpc.transport.RpcClientProxy;
import top.liuyuexin.rpc.api.HelloObject;
import top.liuyuexin.rpc.api.HelloService;
import top.liuyuexin.rpc.transport.netty.client.NettyClient;


public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.KRYO_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        String res = helloService.hello("Hello!World!");
        System.out.println(res);
    }

}
```

客户端选用Netty 的传输方式，序列化方式采用 Kryo 方式，负载均衡策略指定为轮转方式。

首先启动服务提供者，再启动消费者，在消费侧会输出Hello!World!
