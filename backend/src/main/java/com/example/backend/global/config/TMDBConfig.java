package com.example.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // 설정 클래스임을 나타냄 (스프링이 해당 클래스를 구성 설정으로 인식)
public class TMDBConfig {

    @Bean // 스프링 빈으로 등록하여 전역에서 주입 가능하도록 설정
    public RestTemplate restTemplate() {
        return new RestTemplate(); // 외부 API 호출을 위한 RestTemplate 객체 생성
    }
}
