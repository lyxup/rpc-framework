package top.liuyuexin.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.liuyuexin.rpc.api.HelloObject;
import top.liuyuexin.rpc.api.HelloService;

/**
 * @Author LiuYueXin
 * @data 2020/10/29 13:57
 */
public class HelloServiceImpl2 implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl2.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "本次处理来自Socket服务";
    }

}
