package top.liuyuexin.rpc.transport.netty.server;

        import io.netty.channel.ChannelFuture;
        import io.netty.channel.ChannelFutureListener;
        import io.netty.channel.ChannelHandlerContext;
        import io.netty.channel.SimpleChannelInboundHandler;
        import io.netty.util.ReferenceCountUtil;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import top.liuyuexin.rpc.entity.RpcRequest;
        import top.liuyuexin.rpc.entity.RpcResponse;
        import top.liuyuexin.rpc.factory.SingletonFactory;
        import top.liuyuexin.rpc.handler.RequestHandler;
        import top.liuyuexin.rpc.factory.ThreadPoolFactory;

        import java.util.concurrent.ExecutorService;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 17:46
 *
 * Netty中处理RpcRequest的Handler
 */

public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static final String THREAD_NAME_PREFIX = "netty-server-handler";
    private final ExecutorService threadPool;
    private final RequestHandler requestHandler;

    public NettyServerHandler() {
        this.requestHandler = SingletonFactory.getInstance(RequestHandler.class);
        this.threadPool = ThreadPoolFactory.createDefaultThreadPool(THREAD_NAME_PREFIX);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        threadPool.execute(() -> {
            try {
                logger.info("服务器接收到请求: {}", msg);
                Object result = requestHandler.handle(msg);
                ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result, msg.getRequestId()));
                future.addListener(ChannelFutureListener.CLOSE);
            } finally {
                ReferenceCountUtil.release(msg);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }

}