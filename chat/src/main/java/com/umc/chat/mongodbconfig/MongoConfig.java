package com.umc.chat.mongodbconfig;

import com.mongodb.client.MongoClient;  // MongoDB 클라이언트를 임포트
import com.mongodb.client.MongoClients;  // MongoDB 클라이언트 팩토리 메서드를 임포트
import org.springframework.beans.factory.annotation.Value;  // Spring에서 설정 값을 주입받기 위해 임포트
import org.springframework.context.annotation.Bean;  // Spring Bean을 정의하기 위해 임포트
import org.springframework.context.annotation.Configuration;  // Spring 설정 클래스를 정의하기 위해 임포트
import org.springframework.data.mongodb.core.MongoTemplate;  // MongoTemplate을 사용하기 위해 임포트

@Configuration  // 이 클래스가 Spring 설정 클래스임을 나타냄
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")  // application.yml 파일에서 MongoDB URI 값을 주입받음
    private String mongoUri;

    @Bean  // MongoClient를 Spring Bean으로 등록
    public MongoClient mongoClient() {
        // MongoClient를 생성하여 반환
        return MongoClients.create(mongoUri);
    }

    @Bean  // MongoTemplate을 Spring Bean으로 등록
    public MongoTemplate mongoTemplate() {
        // MongoClient와 데이터베이스 이름을 사용하여 MongoTemplate을 생성
        return new MongoTemplate(mongoClient(), "chatdb");
    }
}
