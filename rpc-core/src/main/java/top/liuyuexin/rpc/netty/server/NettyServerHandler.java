package top.liuyuexin.rpc.netty.server;

        import io.netty.channel.ChannelFuture;
        import io.netty.channel.ChannelFutureListener;
        import io.netty.channel.ChannelHandlerContext;
        import io.netty.channel.SimpleChannelInboundHandler;
        import io.netty.util.ReferenceCountUtil;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import top.liuyuexin.rpc.RequestHandler;
        import top.liuyuexin.rpc.entity.RpcRequest;
        import top.liuyuexin.rpc.entity.RpcResponse;
        import top.liuyuexin.rpc.registry.DefaultServiceRegistry;
        import top.liuyuexin.rpc.registry.ServiceRegistry;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 17:46
 *
 * Netty中处理RpcReque的Handler
 */

public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;

    static {
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("服务器接收到请求: {}", msg);
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(msg, service);
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result, msg.getRequestId()));
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }

}