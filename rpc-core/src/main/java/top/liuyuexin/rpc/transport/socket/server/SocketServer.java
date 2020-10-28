package top.liuyuexin.rpc.transport.socket.server;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:38
 *
 * Socket方式远程方法调用的提供者（服务端）
 */

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import top.liuyuexin.rpc.handler.RequestHandler;
        import top.liuyuexin.rpc.provider.ServiceProvider;
        import top.liuyuexin.rpc.provider.ServiceProviderImpl;
        import top.liuyuexin.rpc.registry.NacosServiceRegistry;
        import top.liuyuexin.rpc.transport.RpcServer;
        import top.liuyuexin.rpc.enumeration.RpcError;
        import top.liuyuexin.rpc.exception.RpcException;
        import top.liuyuexin.rpc.registry.ServiceRegistry;
        import top.liuyuexin.rpc.serializer.CommonSerializer;
        import top.liuyuexin.rpc.util.ThreadPoolFactory;

        import java.io.IOException;
        import java.net.InetSocketAddress;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.concurrent.ExecutorService;


public class SocketServer implements RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);


    private final ExecutorService threadPool;;
    private final String host;
    private final int port;
    private RequestHandler requestHandler = new RequestHandler();
    private CommonSerializer serializer;

    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public SocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    @Override
    public void start() {
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
