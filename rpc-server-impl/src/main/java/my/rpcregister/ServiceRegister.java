/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

package my.rpcregister;

import my.constant.Constant;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wangxing on 2016/4/16.
 */
public class ServiceRegister {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegister.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String registryAddress;

    public ServiceRegister(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void register(String data) {
        if(null != data) {
            ZooKeeper zk = connectServer();
            if(null == zk)
                throw new NullPointerException("zk must's be null !");
            createNode(zk, data);
        }
    }

    private void createNode(ZooKeeper zk, String data) {

        try {
            byte[] bytes = data.getBytes();

            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ({} => {})", path, data);

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, (event)-> {
                    if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                        latch.countDown();
                }
            });
            zk.create(Constant.ZK_REGISTRY_PATH, "RegisterRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            latch.await();
        } catch (IOException | InterruptedException | KeeperException e) {
            LOGGER.error("", e);
        }
        return zk;
    }
}
