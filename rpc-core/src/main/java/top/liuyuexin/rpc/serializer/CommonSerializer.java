package top.liuyuexin.rpc.serializer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 17:24
 *
 * 通用的序列化反序列化接口
 */
public interface CommonSerializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
