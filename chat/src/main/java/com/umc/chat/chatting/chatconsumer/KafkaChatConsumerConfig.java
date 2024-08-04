package com.umc.chat.chatting.chatconsumer;

import com.umc.chat.domain.dto.ChatMessageDto;
import com.umc.chat.kafkaconfig.KafkaConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j // Lombok을 사용하여 로깅 기능을 추가합니다.
@EnableKafka // Kafka 리스너를 활성화합니다.
@Configuration // Spring 설정 파일임을 나타냅니다.
public class KafkaChatConsumerConfig {

    // Kafka 리스너 컨테이너 팩토리를 생성하는 빈입니다.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> kafkaChatListenerContainerFactory() {
        log.info("kafkaListenerContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chatConsumerFactory());
        return factory;
    }

    // Kafka 컨슈머 팩토리를 생성하는 빈입니다.
    @Bean
    public ConsumerFactory<String, ChatMessageDto> chatConsumerFactory() {
        log.info("consumerFactory");
        JsonDeserializer<ChatMessageDto> deserializer = new JsonDeserializer<>(ChatMessageDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        // HashMap을 사용하여 Kafka 설정을 구성합니다.
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConsts.KAFKA_BROKER); // Kafka 브로커 주소 설정
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // 키 디시리얼라이저 설정
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer); // 값 디시리얼라이저 설정
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // 오프셋 리셋 설정
        config.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConsts.CHAT_GROUP_ID); // 그룹 ID 설정

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }
}

/**
 * Kafka 컨슈머 설정을 담당
 * 주요 역할:
 *
 * - Kafka 컨슈머 팩토리 및 리스너 컨테이너 팩토리를 설정하여 Kafka 메시지를 소비할 수 있도록 구성
 *
 * 주요 메서드:
 *
 * 1. kafkaListenerContainerFactory():
 *    - ConcurrentKafkaListenerContainerFactory 빈을 생성
 *    - 이 팩토리는 Kafka 리스너 컨테이너를 생성하는 데 사용
 *
 * 2. consumerFactory():
 *    - ConsumerFactory 빈을 생성
 *    - 이 팩토리는 KafkaConsumer를 생성하는 데 사용
 *    - JsonDeserializer를 사용하여 ChatMessage 객체를 디시리얼라이즈
 *
 * 설정 요소:
 *
 * - ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG: Kafka 브로커 주소를 설정
 * - ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG: 키 디시리얼라이저 클래스를 설정
 * - ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG: 값 디시리얼라이저 클래스를 설정
 * - ConsumerConfig.AUTO_OFFSET_RESET_CONFIG: 오프셋 리셋 설정을 "latest"로 설정
 * - ConsumerConfig.GROUP_ID_CONFIG: Kafka 컨슈머 그룹 ID를 설정
 *
 * 이 클래스는 Kafka와 상호작용하는 컨슈머를 설정하여, 애플리케이션이 Kafka 토픽에서 메시지를 소비할 수 있도록 구성
 */
