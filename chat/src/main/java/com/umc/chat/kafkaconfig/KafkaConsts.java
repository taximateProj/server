package com.umc.chat.kafkaconfig;

// Kafka 관련 상수를 정의하는 클래스입니다.
public class KafkaConsts {
    // Kafka에서 사용할 토픽 이름을 정의합니다.
    public static final String CHAT_TOPIC = "kafka.chat";
    public static final String NOTIFICATION_TOPIC = "kafka.notification";
    public static final String CORE_TOPIC = "kafka.core";
    // Kafka 소비자 그룹 ID를 정의합니다.
    public static final String CHAT_GROUP_ID = "chat-group";
    public static final String NOTIFICATION_GROUP_ID = "notification-group";
    public static final String CORE_GROUP_ID = "core-notification-group";

    // Kafka 브로커의 주소를 정의합니다.
    public static final String KAFKA_BROKER = "localhost:9092";
}

/**
 * Kafka 설정에 사용되는 상수를 정의
 * 주요 상수:
 *
 * 1. KAFKA_TOPIC:
 *    - Kafka에서 사용할 토픽 이름을 나타냅니다.
 *    - 예: "kafka.chat"
 *
 * 2. GROUP_ID:
 *    - Kafka 소비자 그룹 ID를 나타냅니다.
 *    - 예: "tt"
 *
 * 3. KAFKA_BROKER:
 *    - Kafka 브로커의 주소를 나타냅니다.
 *    - 예: "localhost:9092"
 *
 * 상호작용 요소:
 *
 * - KAFKA_TOPIC: 메시지를 게시하거나 구독할 때 사용됩니다.
 * - GROUP_ID: Kafka 컨슈머가 동일한 그룹에 속해 있음을 나타내며, 이를 통해 메시지 로드를 분산 처리합니다.
 * - KAFKA_BROKER: Kafka 브로커와 연결하기 위한 주소로 사용됩니다.
 *
 */
