package com.plonit.plonitservice.api.rank.service;

import javax.batch.api.chunk.ItemProcessor;

public class RedisItemProcessor implements ItemProcessor<String, Ranking> {
    @Override
    public Object processItem(Object item) throws Exception {
        return null;
    }
}
