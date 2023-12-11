package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.service.member.MemberCommandService;
import com.memory.beautifulbride.service.member.MemberReadOnlyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class ConsumerController {
    private MemberCommandService memberCommandService;
    private MemberReadOnlyService memberReadOnlyService;
}
