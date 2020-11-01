package top.liuyuexin.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @Author LiuYueXin
 * @data 2020/10/30 14:10
 */
public interface LoadBalancer {
    Instance select(List<Instance> instances);
}
