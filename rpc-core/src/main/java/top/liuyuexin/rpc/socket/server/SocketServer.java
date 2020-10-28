package top.liuyuexin.rpc.socket.server;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:38
 *
 * Socket方式远程方法调用的提供者（服务端）
 */

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import top.liuyuexin.rpc.RequestHandler;
        import top.liuyuexin.rpc.RpcServer;
        import top.liuyuexin.rpc.enumeration.RpcError;
        import top.liuyuexin.rpc.exception.RpcException;
        import top.liuyuexin.rpc.registry.ServiceRegistry;
        import top.liuyuexin.rpc.serializer.CommonSerializer;
        import top.liuyuexin.rpc.util.ThreadPoolFactory;

        import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.concurrent.ExecutorService;


public class SocketServer implements RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);


    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private CommonSerializer serializer;
    private final ServiceRegistry serviceRegistry;

    public SocketServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
    }

    @Override
    public void start ( int port){
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动……");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
