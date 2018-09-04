package com.tairanchina.zt.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class NIOClient {
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    /*接受数据缓冲区*/
    private static ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
    /*服务器端地址*/
    private InetSocketAddress SERVER;
    private Selector selector;
    private SocketChannel client;
    private String receiveText;
    private String sendText;
    private int count = 0;
    private Charset charset = Charset.forName("UTF-8");
    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.CHINA);

    public NIOClient() {
        SERVER = new InetSocketAddress("localhost", 8080);
        init();
    }

    private void init() {
        try {
            /*
             * 客户端向服务器端发起建立连接请求
             */
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);
            channel.connect(SERVER);
            /*
             * 轮询监听客户端上注册事件的发生
             */
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    handle(key);
                }
                keys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handle(SelectionKey key) throws IOException {
        if (key.isConnectable()) {
            /*
             * 连接建立事件，已成功连接至服务器
             */
            client = (SocketChannel) key.channel();

            if (client.isConnectionPending()) {
                client.finishConnect();
                System.out.println("connect success !");
                sendBuffer.clear();
                sendBuffer.put((sdf.format(new Date()) + " connected!").getBytes());
                sendBuffer.flip();
                client.write(sendBuffer);//发送信息至服务器
                /*
                 * 启动线程一直监听客户端输入，有信息输入则发往服务器端
                 * 因为输入流是阻塞的，所以单独线程监听
                 */
                new Thread(() -> {
                    while (true) {
                        try {
                            sendBuffer.clear();
                            InputStreamReader input = new InputStreamReader(System.in);
                            BufferedReader br = new BufferedReader(input);
                            sendText = br.readLine();
                            /*
                             * 未注册WRITE事件，因为大部分时间channel都是可以写的
                             */
                            sendBuffer.put(charset.encode(sendText.trim()));
                            sendBuffer.flip();
                            client.write(sendBuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }

                    }
                }).start();
            }
            client.register(selector, SelectionKey.OP_READ);

        } else if (key.isReadable()) {
            /*
             * 读事件触发
             * 有从服务器端发送过来的信息，读取输出到屏幕上后，继续注册读事件
             * 监听服务器端发送信息
             */
            client = (SocketChannel) key.channel();
            receiveBuffer.clear();
            count = client.read(receiveBuffer);
            if (count > 0) {
                receiveText = new String(receiveBuffer.array(), 0, count);
                System.out.println(receiveText);
                client = (SocketChannel) key.channel();
                client.register(selector, SelectionKey.OP_READ);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        new NIOClient();
    }
}
