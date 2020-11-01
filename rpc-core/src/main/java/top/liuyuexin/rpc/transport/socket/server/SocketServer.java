package top.liuyuexin.rpc.transport.socket.server;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:38
 *
 * Socket方式远程方法调用的提供者（服务端）
 */

import top.liuyuexin.rpc.handler.RequestHandler;
import top.liuyuexin.rpc.hook.ShutdownHook;
import top.liuyuexin.rpc.provider.ServiceProviderImpl;
import top.liuyuexin.rpc.registry.NacosServiceRegistry;
import top.liuyuexin.rpc.transport.AbstractRpcServer;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.factory.ThreadPoolFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketServer extends AbstractRpcServer {
    private final ExecutorService threadPool;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();


    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动……");
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }
}
