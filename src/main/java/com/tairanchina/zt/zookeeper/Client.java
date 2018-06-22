package com.tairanchina.zt.zookeeper;

import org.apache.zookeeper.*;

public class Client implements Watcher {
    ZooKeeper zooKeeper;
    String hostPort;

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public Client(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws Exception {
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }

    String queueCommand(String command) throws Exception {
        String name = null;
        while (true) {
            try {
                name = zooKeeper.create("/tasks/task-", command.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
                return name;
            } catch (KeeperException.NodeExistsException e) {
                throw new Exception(name + "already appears to be running");
            } catch (KeeperException.ConnectionLossException e) {

            }
        }
    }

    public static void main(String[] args) throws Exception {
        String hostPort = "localhost:2181,localhost:2181,localhost:2183";

        Client client = new Client(hostPort);
        client.startZK();
        String name = client.queueCommand("ls");
        System.out.println("Created " + name);
    }
}
