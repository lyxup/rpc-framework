package top.liuyuexin.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.liuyuexin.rpc.annotation.Service;
import top.liuyuexin.rpc.api.HelloObject;
import top.liuyuexin.rpc.api.HelloService;

/**
 * @Author LiuYueXin
 * @data 2020/9/13 20:41
 */
@Service
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "Hello!world!";
    }
}
