package top.liuyuexin.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @Author LiuYueXin
 * @data 2020/10/29 13:42
 *
 * 服务发现接口
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务实体
     */
    InetSocketAddress lookupService(String serviceName);

}
