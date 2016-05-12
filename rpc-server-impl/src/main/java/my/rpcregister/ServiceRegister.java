/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

package my.rpcregister;

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
            createNode(zk, data);
        }
    }

    private void createNode(ZooKeeper zk, String data) {

        try {
            byte[] bytes = data.getBytes();
            zk.create(Constant.ZK_REGISTRY_PATH, "RegisterRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ({} => {})", path, data);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
            latch.await();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("", e);
        }
        return zk;
    }
}
