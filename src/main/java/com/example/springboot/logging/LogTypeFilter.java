package com.example.springboot.logging;

import java.util.Map;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

public class LogTypeFilter extends AbstractMatcherFilter<ILoggingEvent> {

    String MDCKey;
    String MDCValue;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }

        Map<String, String> mdcMap = event.getMDCPropertyMap();
        String logType = mdcMap.getOrDefault(MDCKey, "");
        if (logType.equals(MDCValue)) {
            return onMatch;
        } else {
            return onMismatch;
        }
    }

    public void setMDCKey(String MDCKey) {
        this.MDCKey = MDCKey;
    }

    public void setMDCValue(String MDCValue) {
        this.MDCValue = MDCValue;
    }

    public void start() {
        if (this.MDCKey != null && this.MDCValue != null) {
            super.start();
        }
    }

}