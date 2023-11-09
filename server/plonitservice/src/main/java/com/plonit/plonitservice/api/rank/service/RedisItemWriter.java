package com.plonit.plonitservice.api.rank.service;

import javax.batch.api.chunk.ItemWriter;
import java.io.Serializable;
import java.util.List;

public class RedisItemWriter implements ItemWriter<Ranking> {
    @Override
    public void open(Serializable checkpoint) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void writeItems(List<Object> items) throws Exception {

    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
