package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.springboot.aspects.TrackExecutionTime;

@Service
public class MDCAware {
    private static final Logger log = LoggerFactory.getLogger(MDCAware.class);

    @TrackExecutionTime
    public void logNormalThread() {
        log.info("normal thread");
    }

    @Async
    @TrackExecutionTime
    public void logAsyncThread() {
        log.info("async thread");
    }
}
