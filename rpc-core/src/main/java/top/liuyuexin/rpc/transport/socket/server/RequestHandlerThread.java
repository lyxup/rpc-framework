package top.liuyuexin.rpc.transport.socket.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.liuyuexin.rpc.entity.RpcRequest;
import top.liuyuexin.rpc.entity.RpcResponse;
import top.liuyuexin.rpc.handler.RequestHandler;
import top.liuyuexin.rpc.registry.ServiceRegistry;
import top.liuyuexin.rpc.serializer.CommonSerializer;
import top.liuyuexin.rpc.transport.socket.util.ObjectReader;
import top.liuyuexin.rpc.transport.socket.util.ObjectWriter;

import java.io.*;
import java.net.Socket;
/**
 * @Author LiuYueXin
 * @data 2020/9/18 19:33
 *
 * 处理RpcRequest的工作线程
 */
public class RequestHandlerThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);
    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
        this.serializer = serializer;
    }
    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            String interfaceName = rpcRequest.getInterfaceName();
            Object result = requestHandler.handle(rpcRequest);
            RpcResponse<Object> response = RpcResponse.success(result, rpcRequest.getRequestId());
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }
}
