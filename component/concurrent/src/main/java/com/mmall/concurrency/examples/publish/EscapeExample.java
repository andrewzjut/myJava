package com.mmall.concurrency.examples.publish;

import com.mmall.concurrency.annotations.NotRecommend;
import com.mmall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

@NotThreadSafe
@Slf4j
@NotRecommend
public class EscapeExample {
    private int thisCanBeEscape = 0;

    public EscapeExample() {
        new InnerClass();
    }

    private class InnerClass {

        public InnerClass() {
            //EscapeExample（）还在构建过程  log.info还没执行完  就可以调用EscapeExample对象的thisCanBeEscape属性
            //发生逸出
            log.info("{}", EscapeExample.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new EscapeExample();
    }
}
