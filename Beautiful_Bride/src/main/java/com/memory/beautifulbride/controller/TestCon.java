package com.memory.beautifulbride.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCon {
    @GetMapping("/")
    String test() {
        return "TestSucess";
    }

    @GetMapping("/admin/test1")
    String test2() {
        return "AdminSucess";
    }

    @GetMapping("/haha/admin")
    String test3() {
        return "AdminSucess3333";
    }
}
