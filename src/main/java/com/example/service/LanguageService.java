package com.example.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LanguageService {

    @PostConstruct
    public void init() {
        System.out.println("LanguageService bean created!");
    }

    private final Map<Long, String> userLanguages = new HashMap<>();

    public void setLanguage(Long userId, String languageCode) {
        userLanguages.put(userId, languageCode);
    }

    public String getLanguage(Long userId) {
        return userLanguages.getOrDefault(userId, "en");
    }

}