package com.example.springboot;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	Logger log = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	MDCAware example;

	@PostMapping("/")
	public String index(@RequestParam String id) throws InterruptedException, ExecutionException {
		MDC.put("sessionId", id);
		MDC.put("sourceType", "runner");
		example.logNormalThread();
		try {
			example.logAsyncThread();
		} catch (Exception e) {
			log.error("Failed to run Async thread", e);
		}

		return "Done";
	}
}
