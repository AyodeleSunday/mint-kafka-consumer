package com.mint.web;

import com.mint.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Sunday Ayodele
 * @create 10/10/2020
 */

@RestController
@RequestMapping(value = {"/card-scheme"})
public class SchemeController {
    @Autowired
    CardService cardService;
    @RequestMapping(path = "/records", method = RequestMethod.GET, produces = "application/json")
    public Page getBinHit(@RequestParam int start, @RequestParam int limit) {
        Page response = cardService.getSchemes(start, limit);
        return response;
    }
}
