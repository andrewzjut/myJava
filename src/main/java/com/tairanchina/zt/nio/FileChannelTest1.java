package com.tairanchina.zt.nio;

import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest1 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile inFile = new RandomAccessFile("/Users/zhangtong/IdeaProjects/zhangtong/myJava/src/main/java/com/tairanchina/zt/nio/a.txt", "r");
        FileChannel inChannel = inFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(5);


        FileOutputStream outFile = new FileOutputStream("/Users/zhangtong/IdeaProjects/zhangtong/myJava/src/main/java/com/tairanchina/zt/nio/b.txt");
        FileChannel outChannel = outFile.getChannel();

        int bytesRead = inChannel.read(buffer);
        while (bytesRead != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
            bytesRead = inChannel.read(buffer);
        }

        inFile.close();
        outFile.close();

    }
}
