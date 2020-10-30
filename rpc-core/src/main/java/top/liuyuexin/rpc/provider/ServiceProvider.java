package top.liuyuexin.rpc.provider;

/**
 * @Author LiuYueXin
 * @data 2020/10/28 15:38
 *
 * 保存和提供服务实例对象
 */
public interface ServiceProvider {

    <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);

}
