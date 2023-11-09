package com.plonit.plonitservice.common.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CrewItemReader implements ItemReader<String> {
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
