package com.plonit.plonitservice.api.crew.service.impl;

import com.plonit.plonitservice.api.crew.service.DTO.SaveCrewDTO;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("크루 서비스 테스트")
class CrewServiceImplTest{

    @Autowired
    private CrewServiceImpl crewService;

    @Autowired
    private CrewRepository crewRepository;

    @Nested
    @DisplayName("플로깅 시작시 초기 설정 저장")
    class saveCrew {
        @Test
        @DisplayName("#성공")
        public void success () throws IOException {
            // given
            Long memberId = 1L;

            String fileName = "plonit"; // 파일명
            String contentType = "jpg"; // 파일타입
            String filePath = "src/test/resources/testImage/"+fileName+"."+contentType; // 파일 경로
            FileInputStream fileInputStream = new FileInputStream(filePath);

            MultipartFile image = new MockMultipartFile(
                    "images", // name
                    fileName + "." + contentType, // originalFilename
                    contentType,
                    fileInputStream
            );

            SaveCrewDTO saveCrewDTO = saveCrewDTO(memberId, "test","test", "test", image);
            // when, then
            Assertions.assertDoesNotThrow(() -> crewService.saveCrew(saveCrewDTO));
        }

        @Nested
        @DisplayName("#실패")
        class fail {
        }

    }

    private SaveCrewDTO saveCrewDTO(long memberKey, String name, String introducem, String region,
                                    MultipartFile image) {
        return SaveCrewDTO.builder()
                .memberKey(memberKey)
                .name(name)
                .crewImage(image)
                .introduce(introducem)
                .region(region)
                .build();
    }


}