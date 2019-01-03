package com.example.demo.web.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;

@RestController
@RequestMapping("/api/wake-up-server")
@CrossOrigin("http://localhost:3000")
public class WakeUpServerController {

	@GetMapping
	public void wakeUp(){
	}

}
