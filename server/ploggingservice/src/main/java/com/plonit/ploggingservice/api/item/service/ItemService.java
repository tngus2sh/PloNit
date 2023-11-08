package com.plonit.ploggingservice.api.item.service;

import com.plonit.ploggingservice.api.item.controller.response.FindTrashcanRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ItemService {
    List<FindTrashcanRes> findTrashcan(Double latitude, Double longitude);
}
