package com.zhuduan.rabbitmq.controller;

import com.zhuduan.rabbitmq.DirectSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * sender controller
 *
 * @author Haifeng.Zhu
 * created at 5/13/19
 */
@RestController
@RequestMapping("sender")
public class SenderController {
    
    @Autowired
    private DirectSender directSender;

    @RequestMapping(value = "/direct", method = RequestMethod.GET)
    public String sendDirectMsg(@RequestParam("msg") String msg){
        directSender.sendMessage(msg);
        return "done";
    }
}
