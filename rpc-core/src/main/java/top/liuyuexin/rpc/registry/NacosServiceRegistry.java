package top.liuyuexin.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.liuyuexin.rpc.enumeration.RpcError;
import top.liuyuexin.rpc.exception.RpcException;
import top.liuyuexin.rpc.util.NacosUtil;

import java.net.InetSocketAddress;

/**
 * @Author LiuYueXin
 * @data 2020/10/28 15:45
 *
 * Nacos服务注册中心
 */
public class NacosServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
