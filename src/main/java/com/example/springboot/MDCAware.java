package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.springboot.aspects.TrackExecutionTime;

import static net.logstash.logback.marker.Markers.*;

import java.io.IOException;

@Service
public class MDCAware {
    private static final Logger log = LoggerFactory.getLogger(MDCAware.class);

    @TrackExecutionTime
    public void logNormalThread() {
        log.warn(append("threadType", "normal"), "normal thread");
    }

    @Async
    @TrackExecutionTime
    public void logAsyncThread() throws IOException {
        log.info(append("threadType", "async"), "async thread");
        throw new IOException("JsonBlaha was here", null);
    }
}
