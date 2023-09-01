package com.example.springboot;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;




class MdcTaskDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(Runnable runnable) {
    // Right now: Web thread context !
    // (Grab the current thread MDC data)
    Map<String, String> contextMap = Optional.ofNullable(MDC.getCopyOfContextMap())
        .orElse(Collections.emptyMap());
    return () -> {
      try {
        // Right now: @Async thread context !
        // (Restore the Web thread context's MDC data)
        MDC.setContextMap(contextMap);
        runnable.run();
      } finally {
        MDC.clear();
      }
    };
  }
}
