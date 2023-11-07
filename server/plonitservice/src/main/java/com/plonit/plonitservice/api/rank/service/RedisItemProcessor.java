package com.plonit.plonitservice.api.rank.service;

import com.plonit.plonitservice.domain.rank.Ranking;

import javax.batch.api.chunk.ItemProcessor;

public class RedisItemProcessor implements ItemProcessor<String, Ranking> {
    @Override
    public Object processItem(Object item) throws Exception {
        return null;
    }
}
