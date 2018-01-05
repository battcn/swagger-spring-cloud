package com.battcn.swagger.smaple.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Levin
 * @since 2017/12/30 0030
 */
@RestController
@RequestMapping("/emails")
@Api(tags = "2.1", description = "邮件管理")
public class EmailController {

    @PostMapping
    public int add() {
        return 200;
    }


}
