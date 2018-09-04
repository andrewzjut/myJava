package com.tairanchina.zt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

//https://blog.csdn.net/cuiyaoqiang/article/details/51361083/
public class NIOSServer {
    private static final int BUF_SIZE = 1024;
    private int port = 8080;
    private CharsetDecoder decoder = Charset.forName("utf-8").newDecoder();
    private ByteBuffer sentBuffer = ByteBuffer.allocate(BUF_SIZE);
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(BUF_SIZE);
    private Map<String, SocketChannel> clientsMap = new HashMap();
    private Selector selector;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.CHINA);


    public NIOSServer() {
        try {
            init();
            listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        /*
         *启动服务器端，配置为非阻塞，绑定端口，注册accept事件
         *ACCEPT事件：当服务端收到客户端连接请求时，触发该事件
         */

        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        ServerSocket serverSocket = server.socket();

        serverSocket.bind(new InetSocketAddress(port));
        selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start on port:" + port);
    }

    private void listen() {
        while (true) {
            try {
                selector.select();//返回值为本次触发的事件数
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    handle(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void handle(SelectionKey key) throws IOException {
        ServerSocketChannel server;
        SocketChannel client;
        String receiveMsg;
        int count;
        if (key.isAcceptable()) {
            /*
             * 客户端请求连接事件
             * serversocket为该客户端建立socket连接，将此socket注册READ事件，监听客户端输入
             * READ事件：当客户端发来数据，并已被服务器控制线程正确读取时，触发该事件
             */
            server = (ServerSocketChannel) key.channel();
            client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);

        } else if (key.isReadable()) {
            /*
             * READ事件，收到客户端发送数据，读取数据后继续注册监听客户端
             */
            client = (SocketChannel) key.channel();
            receiveBuffer.clear();
            count = client.read(receiveBuffer);
            if (count > 0) {
                receiveBuffer.flip();
                receiveMsg = decoder.decode(receiveBuffer.asReadOnlyBuffer()).toString();
                System.out.println(client.toString() + ":" + receiveMsg);
                sentBuffer.clear();
                sentBuffer.put((dateFormat.format(new Date()) + "服务器收到你的消息").getBytes());
                sentBuffer.flip();
                client.write(sentBuffer);
                dispatch(client, receiveMsg);
                client = (SocketChannel) key.channel();
                client.register(selector, SelectionKey.OP_READ);
            }
        }
    }

    private void dispatch(SocketChannel client, String info) throws IOException {
        Socket s = client.socket();
        String name = "[" + s.getInetAddress().toString().substring(1) + ":" + Integer.toHexString(client.hashCode()) + "]";
        if (!clientsMap.isEmpty()) {
            for (Map.Entry<String, SocketChannel> entry : clientsMap.entrySet()) {
                SocketChannel temp = entry.getValue();
                if (!client.equals(temp)) {
                    sentBuffer.clear();
                    sentBuffer.put((name + ":" + info).getBytes());
                    sentBuffer.flip();
                    //输出到通道
                    temp.write(sentBuffer);
                }
            }
        }
        clientsMap.put(name, client);
    }

    public static void main(String[] args) {
        new NIOSServer();
    }
}
