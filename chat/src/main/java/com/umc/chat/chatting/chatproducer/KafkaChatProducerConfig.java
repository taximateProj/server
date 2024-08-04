package com.umc.chat.chatting.chatproducer;

import com.umc.chat.domain.dto.ChatMessageDto;
import com.umc.chat.kafkaconfig.KafkaConsts;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration // Spring 설정 파일임을 나타냅니다.
@EnableKafka // Kafka를 활성화합니다.
public class KafkaChatProducerConfig {

    // Kafka 프로듀서 팩토리를 생성하는 빈입니다.
    @Bean
    public ProducerFactory<String, ChatMessageDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    // Kafka 프로듀서 설정을 구성하는 빈입니다.
    @Bean
    public Map<String, Object> kafkaProducerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConsts.KAFKA_BROKER); // Kafka 브로커 주소 설정
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // 키 직렬화 클래스 설정
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // 값 직렬화 클래스 설정
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return config;
    }

    // KafkaTemplate 빈을 생성합니다.
    @Bean
    public KafkaTemplate<String, ChatMessageDto> kafkaChatTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}

/**
 * Kafka 프로듀서 설정
 * 주요 메서드:
 *
 * 1. producerFactory():
 *    - ProducerFactory 빈을 생성
 *    - 이 팩토리는 Kafka 프로듀서를 생성
 *
 * 2. kafkaProducerConfiguration():
 *    - Kafka 프로듀서 설정을 구성
 *    - Kafka 브로커 주소, 키 및 값 직렬화 클래스, 그룹 ID 등을 설정
 *
 * 3. kafkaTemplate():
 *    - KafkaTemplate 빈을 생성
 *    - 이 템플릿은 메시지를 Kafka 토픽에 전송하는 데 사용
 *
 * 설정 요소:
 *
 * - ProducerConfig.BOOTSTRAP_SERVERS_CONFIG: Kafka 브로커 주소를 설정
 * - ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG: 키 직렬화 클래스를 설정
 * - ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG: 값 직렬화 클래스를 설정
 * - ProducerConfig.GROUP_ID_CONFIG: Kafka 프로듀서 그룹 ID를 설정
 *
 * 이 클래스는 Kafka와 상호작용하는 프로듀서를 설정하여, 애플리케이션이 Kafka 토픽에 메시지를 전송할 수 있도록 구성합니다.
 */
