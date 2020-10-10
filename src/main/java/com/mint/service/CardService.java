package com.mint.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mint.model.CardSchemeModel;
import com.mint.model.VerifiedCardScheme;
import com.mint.repository.CardSchemeRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author Sunday Ayodele
 * @create 10/10/2020
 */

@Component
public class CardService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CardSchemeRepository cardSchemeRepository;

    @KafkaListener(groupId = "12323fxcvdfd",topics = "${mint.kafka.topic}")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List topics,
                               @Header(KafkaHeaders.OFFSET) List offsets) {
        try {
            VerifiedCardScheme cardScheme = new ObjectMapper().readValue(message, VerifiedCardScheme.class);
            CardSchemeModel schemeModel =  new CardSchemeModel();
            BeanUtils.copyProperties(cardScheme,schemeModel);
            cardSchemeRepository.save(schemeModel);
        } catch (Exception ex) {
            logger.error("Error", ex);
        }
    }

    public Page<CardSchemeModel> getSchemes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return cardSchemeRepository.findAll(pageable);
    }
}
