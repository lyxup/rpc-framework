package top.liuyuexin.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.liuyuexin.rpc.registry.ServiceRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author LiuYueXin
 * @data 2020/9/14 16:04
 *
 * 远程方法调用的提供者（服务端)
 */
public class RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCK_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPoll;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;

    public RpcServer(ServiceRegistry serviceRegistry){
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCK_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPoll = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void start(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动.....");
            Socket socket;
            while ((socket = serverSocket.accept()) != null){
                logger.info("消费者连接：{}：{}", socket.getInetAddress(), socket.getPort());
                threadPoll.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
            threadPoll.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生：", e);
        }
    }

}
