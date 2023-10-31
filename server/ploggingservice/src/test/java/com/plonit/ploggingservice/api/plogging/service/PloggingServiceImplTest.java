package com.plonit.ploggingservice.api.plogging.service;

import com.plonit.ploggingservice.api.plogging.controller.response.KakaoAddressResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import javax.ws.rs.core.HttpHeaders;
import java.util.Map;


// 서비스
@DisplayName("플로깅 서비스 테스트")
@SpringBootTest
public class PloggingServiceImplTest {

    @Test
    @DisplayName("카카오 연결 test")
    public void kakaoTest() {

        // webClient 기본 설정
        WebClient webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2/local/geo/coord2address.json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK 0dfc0762e8bf4854c806dbee4b1808d5")
                .build();

        KakaoAddressResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("x", 127.423084873712)
                        .queryParam("y", 37.0789561558879)
                        .queryParam("input_cord", "WGS84")
                        .build())
                .retrieve()
                .bodyToMono(KakaoAddressResponse.class)
                .block();

        // 결과 확인
        System.out.println(response.toString());
        System.out.println(response.getDocuments()[0].getAddress().getAddress_name());
    }
    
    // 메소드 : 등록, 조회, 삭제
    @Nested
    @DisplayName("플로깅 시작시 초기 설정 저장")
    class saveStartPlogging {
        
        // 성공, 실패
        @Test
        @DisplayName("#성공")
        public void success() {
            
            
        }
        
        @Nested
        @DisplayName("#실패")
        class fail {
            @Test
            @DisplayName("#크루핑 id가 없을 때")
            public void notFoundCrewpingId() {

            }
        }
    }
    
    @Nested
    @DisplayName("플로깅 완료 후 기록 저장")
    class saveEndPlogging {
        
        @Test
        @DisplayName("#성공")
        public void success() {
            
        }
        
        @Nested
        @DisplayName("#실패")
        class fail {
            
            @Test
            @DisplayName("#해당하는 플로깅 id가 없을 때")
            public void noPloggingId() {
                
            }
        }
    }
    
    @Nested
    @DisplayName("플로깅 기록 일별 조회")
    class findPloggingLogByDay {
        
        @Test
        @DisplayName("#성공")
        public void success() {
            
        }
        
        @Nested
        @DisplayName("#실패")
        class fail {
            
            @Test
            @DisplayName("#시작날짜가 마지막 날짜보다 클 때")
            public void biggerThanEndDay() {
                
            }
        }
    }
    
    @Nested
    @DisplayName("플로깅 기록 상세 조회")
    class findPloggingLogDetail {
        
        @Test
        @DisplayName("#성공")
        public void success() {
            
        }
        
        @Nested
        @DisplayName("#실패")
        class fail {
            
            @Test
            @DisplayName("#크루핑 id가 없을 때")
            public void noCrewpingId() {
                
            }
            
        }
    }

    @Nested
    @DisplayName("플로깅 도움 요청 저장")
    class savePloggingHelp {

        @Test
        @DisplayName("#성공")
        public void success() {

        }

        @Nested
        @DisplayName("#실패")
        class fail {

            @Test
            @DisplayName("#크루핑 id가 없을 때")
            public void noCrewpingId() {

            }
        }
    }

    @Nested
    @DisplayName("플로깅 도움 요청 지역별 조회")
    class findPloggingHelp {

        @Test
        @DisplayName("#성공")
        public void success() {

        }

        @Nested
        @DisplayName("#실패")
        class fail {

            @Test
            @DisplayName("#위도,경도에 맞는 지역이 없을 때")
            public void noRegion() {

            }
        }
    }

    @Nested
    @DisplayName("플로깅 중간에 이미지 전송")
    class savePloggingImage {

        @Test
        @DisplayName("#성공")
        public void success() {

        }

        @Nested
        @DisplayName("#실패")
        class fail {

            @Test
            @DisplayName("#해당하는 플로깅 id가 없을 때")
            public void noPloggingId() {
                
            }
        }
    }

    @Nested
    @DisplayName("주변에 있는 유저 조회")
    class findPloggingUsers {

        @Test
        @DisplayName("#성공")
        public void success() {

        }

        @Nested
        @DisplayName("#실패")
        class fail {

            @Test
            @DisplayName("#위도,경도에 맞는 지역이 없을 때")
            public void noRegion() {

            }
        }
    }
}
