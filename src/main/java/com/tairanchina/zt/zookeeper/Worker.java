package com.tairanchina.zt.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public class Worker implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(Worker.class);
    ZooKeeper zooKeeper;
    String hostPort;
    String status;
    String name;

    private Random random = new Random(this.hashCode());

    String serverId = Integer.toHexString(random.nextInt());

    public Worker(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }

    @Override
    public void process(WatchedEvent event) {
        logger.info(event.toString() + ", " + hostPort);
    }

    void register() {
        zooKeeper.create("/workers/worker-" + serverId, "Idel".getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, createWorkerCallback, null);
    }

    AsyncCallback.StringCallback createWorkerCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    register();
                    break;
                case OK:
                    logger.info("Registered successfully: " + serverId);
                    break;
                case NODEEXISTS:
                    logger.warn("Already registered: " + serverId);
                    break;
                default:
                    logger.error("Something went wrong: " + KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };

    AsyncCallback.StatCallback statUpdateCallback = new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    updateStatus((String) ctx);
                    return;
            }
        }
    };

    synchronized private void updateStatus(String status) {
        if (status == this.status) {
            zooKeeper.setData("/workers/" + name, status.getBytes(), -1, statUpdateCallback, status);
        }
    }

    public void setStatus(String status) {
        this.status = status;
        updateStatus(status);
    }

    public static void main(String[] args) throws Exception {
        String hostPort = "localhost:2181,localhost:2181,localhost:2183";

        Worker worker = new Worker(hostPort);

        worker.startZK();

        worker.register();

        Thread.sleep(30000);

    }
}
