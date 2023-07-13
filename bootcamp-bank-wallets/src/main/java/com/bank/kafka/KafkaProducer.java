package com.bank.kafka;

import com.bank.model.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "bootcamp-email";

    public void send(Wallet wallet, String numOperation) {
        String message = "Operation exitosa para el numero de operacion  " + numOperation
                + " para el monedero con numero de celular " + wallet.getNumCellphone();
        kafkaTemplate.send(TOPIC, message);
        LOGGER.info("message: " + message);
    }
}
