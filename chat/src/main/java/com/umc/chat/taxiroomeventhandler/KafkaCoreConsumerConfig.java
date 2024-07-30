package com.umc.chat.taxiroomeventhandler;

import com.umc.common.kafkamessage.CoreMessageDto;
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
public class KafkaCoreConsumerConfig {

    // Kafka 리스너 컨테이너 팩토리를 생성하는 빈입니다.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CoreMessageDto<?>> kafkaCoreListenerContainerFactory() {
        log.info("kafkaListenerContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, CoreMessageDto<?>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(coreConsumerFactory());
        return factory;
    }

    // Kafka 컨슈머 팩토리를 생성하는 빈입니다.
    @Bean
    public ConsumerFactory<String, CoreMessageDto<?>> coreConsumerFactory() {
        log.info("consumerFactory");
        JsonDeserializer<CoreMessageDto<?>> deserializer = new JsonDeserializer<>(CoreMessageDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        // HashMap을 사용하여 Kafka 설정을 구성합니다.
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConsts.KAFKA_BROKER); // Kafka 브로커 주소 설정
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // 키 디시리얼라이저 설정
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer); // 값 디시리얼라이저 설정
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // 오프셋 리셋 설정
        config.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConsts.CORE_GROUP_ID); // 그룹 ID 설정

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }
}

