package com.example.springretry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyJob {
    private final RetryTemplate retryTemplate;
    private int retryCount = 0;

    @Retryable(
            value = {IOException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000L)
    )
    public void customRetry(boolean hasError) throws IOException {
    log.info("Attempting at {} time(s)", ++retryCount);
    if (hasError) throw new IOException();
}

    @Retryable
    public void defaultRetry(boolean hasError) {
        log.info("Attempting at {} time(s)", ++retryCount);
        if (hasError) throw new IllegalArgumentException();
    }

    @Recover
    public void recover() {
        retryCount = 0;
        log.info("Recovering");
        log.info("-----------");
    }

    public String useRetryTemplate(boolean hasError) {
        return retryTemplate.execute(
                retryCallback -> {
                    log.info("Attempting at {} time(s)", retryCallback.getRetryCount() + 1);
                    if (hasError) throw new IllegalArgumentException();
                    return "Success";
                },
                recoveryCallback -> {
                    return "Recovering";
                }
        );
    }
}
