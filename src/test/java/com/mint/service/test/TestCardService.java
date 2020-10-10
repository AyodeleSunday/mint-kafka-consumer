package com.mint.service.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mint.model.VerifiedCardScheme;
import com.mint.service.CardService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Sunday Ayodele
 * @create 10/10/2020
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCardService {
    @Autowired
    CardService cardService;

    @Test
    public void testMessageListening() throws Exception {
        VerifiedCardScheme cardScheme = new VerifiedCardScheme();
        cardScheme.setBank("FINANCIERA FINANGENTE, S.A.");
        cardScheme.setScheme("mastercard");
        cardScheme.setType("credit");
        String payload = new ObjectMapper().writeValueAsString(cardScheme);
        cardService.processMessage(payload, null, null, null);
        Page resp=cardService.getSchemes(0,5);
        Assert.assertTrue(resp.getTotalElements()>0);
    }
}
