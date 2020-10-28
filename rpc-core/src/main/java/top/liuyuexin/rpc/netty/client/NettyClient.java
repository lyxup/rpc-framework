package top.liuyuexin.rpc.netty.client;

import com.esotericsoftware.kryo.Kryo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.liuyuexin.rpc.RpcClient;
import top.liuyuexin.rpc.codec.CommonDecoder;
import top.liuyuexin.rpc.codec.CommonEncoder;
import top.liuyuexin.rpc.entity.RpcRequest;
import top.liuyuexin.rpc.entity.RpcResponse;
import top.liuyuexin.rpc.enumeration.RpcError;
import top.liuyuexin.rpc.exception.RpcException;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.serializer.KryoSerializer;
import top.liuyuexin.rpc.util.RpcMessageChecker;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 17:36
 *
 * NIO方式消费侧客户端类
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    private CommonSerializer serializer;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR.SO_KEEPALIVE, true);
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {

        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        AtomicReference<Object> result = new AtomicReference<>(null);

        try {
            Channel channel = ChannelProvider.get(new InetSocketAddress(host, port), serializer);
            if(channel.isActive()) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(rpcRequest, rpcResponse);
                result.set(rpcResponse.getData());
            } else {
                System.exit(0);
            }

        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生: ", e);
        }
        return result.get();



    }

}
