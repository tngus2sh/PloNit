package com.plonit.plonitservice.api.rank.service;

import com.plonit.plonitservice.common.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.batch.api.chunk.ItemReader;
import java.io.Serializable;
import java.util.Iterator;

public class RedisItemReader implements ItemReader<String> {

    @Autowired
    private RedisUtils redisUtils;

    private Iterator<String > dataIterator;

    public RedisItemReader() {
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Object readItem() throws Exception {
        if (dataIterator == null) {
            // Redis에서 불러오기
        }
        return null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
