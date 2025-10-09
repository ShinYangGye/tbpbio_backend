package com.meta.controller.api;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.meta.advice.exception.CAuthenticationEntryPointException;
import com.meta.dto.ResponseMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public ResponseMessageDto entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessdenied")
    public ResponseMessageDto accessdeniedException() {
        throw new AccessDeniedException("");
    }
}
