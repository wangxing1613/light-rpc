/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

package my;

/**
 * Created by wangxing on 2016/4/16.
 */

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        return "hello " + name;
    }
}
