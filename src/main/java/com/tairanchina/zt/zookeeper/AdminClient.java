package com.tairanchina.zt.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Date;

public class AdminClient implements Watcher {

    ZooKeeper zooKeeper;
    String hostPort;

    @Override
    public void process(WatchedEvent event) {

        System.out.println(event);
    }

    public AdminClient(String hostPort) {
        this.hostPort = hostPort;
    }

    void start() throws Exception {
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }

    void listState() throws KeeperException, InterruptedException {
        try {
            Stat stat = new Stat();
            byte masterData[] = zooKeeper.getData("/master", false, stat);
            Date startDate = new Date(stat.getCtime());
            System.out.println("Master: " + new String(masterData) + " since " + startDate);
        } catch (KeeperException.NoNodeException e) {
            System.out.println("No Master");
        }
        System.out.println("Worker: ");
        for (String w : zooKeeper.getChildren("/workers", false)) {
            byte[] data = zooKeeper.getData("/workers/" + w, false, null);
            String state = new String(data);
            System.out.println("\t" + w + ": " + state);
        }
        System.out.println("Tasks: ");
        for (String t : zooKeeper.getChildren("/assign", false)) {
            System.out.println("\t" + t);
        }
    }

    public static void main(String[] args) throws Exception {
        String hostPort = "localhost:2181,localhost:2181,localhost:2183";

        AdminClient adminClient = new AdminClient(hostPort);

        adminClient.start();
        adminClient.listState();
    }
}

