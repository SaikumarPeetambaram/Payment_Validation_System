package com.cpt.payments.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	private final RedisTemplate<String, String> redisTemplate;
	private final ListOperations<String, String> listOperations;
	private final HashOperations<String, String, String> hashOperations;

	public RedisService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.listOperations = redisTemplate.opsForList();
		this.hashOperations = redisTemplate.opsForHash();
	}

	public void addValueToList(String key, String value) {
		listOperations.rightPush(key, value);
	}

	public void addValuesToList(String key, List<String> values) {
		listOperations.rightPushAll(key, values);
	}

	public List<String> getAllValuesFromList(String key) {
		return listOperations.range(key, 0, -1);
	}

	public void setValueInHash(String hashName, String key, String value) {
		hashOperations.put(hashName, key, value);
	}

	public String getValueFromHash(String hashName, String key) {
		return hashOperations.get(hashName, key);
	}

	public Map<String, String> getAllEntriesFromHash(String hashName) {
		return hashOperations.entries(hashName);
	}

	public void setBulkValueInHash(String hashName, Map<String, String> entries) {
		hashOperations.putAll(hashName, entries);
	}

	public void clearList(String key) {
		redisTemplate.delete(key);
	}

	public void clearHash(String key) {
		redisTemplate.delete(key);
	}

}
