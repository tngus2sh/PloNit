package com.plonit.ploggingservice.api.item.controller;

import com.plonit.ploggingservice.api.item.controller.response.FindItemRes;
import com.plonit.ploggingservice.api.item.service.ItemService;
import com.plonit.ploggingservice.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.plonit.ploggingservice.common.util.LogCurrent.*;

@Tag(name = "Item API Controller", description = "아이템 API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plogging-service/v1/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/trashcan/{latitude}/{longitude}")
    public CustomApiResponse<List<FindItemRes>> findTrashcan(@PathVariable("latitude") Double latitude, @PathVariable("longitude") Double longitude) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindTrashcan={}, {}", latitude, longitude);

        List<FindItemRes> response = itemService.findTrashcan(latitude, longitude);

        return CustomApiResponse.ok(response, "쓰레기통을 조회했습니다.");
    }

    @GetMapping("/toilet/{latitude}/{longitude}")
    public CustomApiResponse<List<FindItemRes>> findToilet(@PathVariable("latitude") Double latitude, @PathVariable("longitude") Double longitude) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindToilet={}, {}", latitude, longitude);

        List<FindItemRes> response = itemService.findToilet(latitude, longitude);

        return CustomApiResponse.ok(response, "화장실을 조회했습니다.");
    }
}
