package com.plonit.ploggingservice.api.item.service;

import com.plonit.ploggingservice.api.item.controller.response.FindItemRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ItemService {
    List<FindItemRes> findTrashcan(Double latitude, Double longitude);
    List<FindItemRes> findToilet(Double latitude, Double longitude);
}
