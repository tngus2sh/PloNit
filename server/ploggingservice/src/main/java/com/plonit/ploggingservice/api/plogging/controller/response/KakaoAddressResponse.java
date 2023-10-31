package com.plonit.ploggingservice.api.plogging.controller.response;

import lombok.*;

@Data
@RequiredArgsConstructor
public class KakaoAddressResponse {
    private Meta meta;
    private Document[] documents;

    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    public static class Meta {
        private int total_count;
    }

    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    public static class Document {
        private RoadAddress road_address;
        private Address address;
    }

    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    public static class RoadAddress {
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String road_name;
        private String underground_yn;
        private int main_building_no;
        private int sub_building_no;
        private String building_name;
        private int zone_no;
    }

    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    public static class Address {
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String mountain_yn;
        private int main_address_no;
        private int sub_address_no;
        private String zip_code;
    }
}
