package com.mmall.concurrency.examples.publish;

import com.mmall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@NotThreadSafe
@Slf4j
public class PublishExample {
    private String[] states = {"a", "b", "c"};

    //可以被其他线程修改
    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        PublishExample publishExample = new PublishExample();
        log.info("{}", Arrays.toString(publishExample.getStates()));

        publishExample.getStates()[0] = "d";
        log.info("{}", Arrays.toString(publishExample.getStates()));

    }
}
