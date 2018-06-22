package com.tairanchina.zt.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public class Master implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(Master.class);
    private ZooKeeper zooKeeper;
    private String hostPort;
    static boolean isLeader = false;
    private Random random = new Random(this.hashCode());
    private String serverId = Integer.toHexString(random.nextInt());

    AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    isLeader = true;
                    break;
                default:
                    isLeader = false;
            }
            System.out.println("I'm " + (isLeader ? "" : "not ") + " the leader");
        }
    };
    AsyncCallback.DataCallback masterCheckCallback = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case NONODE:
                    runForMaster();
                    return;
            }
        }
    };

    public Master(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() {
        try {
            zooKeeper = new ZooKeeper(hostPort, 15000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void stopZK() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    void runForMaster() {
        zooKeeper.create("/master", serverId.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallback, null);
    }

    void checkMaster() {
        zooKeeper.getData("/master", false, masterCheckCallback, null);
    }

    void bootstrap() {
        createParent("/workers", new byte[0]);
        createParent("/assign", new byte[0]);
        createParent("/tasks", new byte[0]);
        createParent("/status", new byte[0]);
    }

    void createParent(String path, byte[] data) {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createParentCallback, data);
    }

    AsyncCallback.StringCallback createParentCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    createParent(path, (byte[]) ctx);
                    break;
                case OK:
                    logger.info("Parent created");
                    break;
                case NODEEXISTS:
                    logger.warn("Parent already registerd: " + path);
                    break;
                default:
                    logger.warn("Something went wrong: " + KeeperException.create(KeeperException.Code.get(rc), path));

            }
        }
    };

    public static void main(String[] args) throws Exception {

        String hostPort = "localhost:2181,localhost:2181,localhost:2183";
        Master master = new Master(hostPort);
        master.startZK();
        master.bootstrap();//创建固定节点
        master.runForMaster();
        master.checkMaster();

        Thread.sleep(60000);
        master.stopZK();
    }
}
