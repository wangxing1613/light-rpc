/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

package my;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wangxing on 2016/4/16.
 */
public class RpcBootstrap {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }
}
