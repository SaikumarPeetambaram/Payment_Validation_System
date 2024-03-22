package com.cpt.payments.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.service.impl.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {
    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/add/{key}")
    public void addValueToList(@PathVariable String key, @RequestParam String value) {
        redisService.addValueToList(key, value);
    }

    @GetMapping("/get/{key}")
    public List<String> getAllValuesFromList(@PathVariable String key) {
        return redisService.getAllValuesFromList(key);
    }
    
    
    @PostMapping("/set")
    public void setValueInHash(@RequestParam String hashName, @RequestParam String key, @RequestParam String value) {
        redisService.setValueInHash(hashName, key, value);
    }

    @GetMapping("/get")
    public String getValueFromHash(@RequestParam String hashName, @RequestParam String key) {
        return redisService.getValueFromHash(hashName, key);
    }

    @GetMapping("/get-all")
    public Map<String, String> getAllEntriesFromHash(@RequestParam String hashName) {
        return redisService.getAllEntriesFromHash(hashName);
    }
    
    @DeleteMapping("/list/{key}/clear")
    public void clearList(@PathVariable String key) {
        redisService.clearList(key);
    }

    @DeleteMapping("/hash/{key}/clear")
    public void clearHash(@PathVariable String key) {
        redisService.clearHash(key);
    }
}
