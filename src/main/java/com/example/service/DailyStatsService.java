package com.example.service;

import com.example.Entity.DailyStats;
import com.example.repository.DailyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DailyStatsService {

    @Autowired
    private DailyStatsRepository dailyStatsRepository;


    public void incrementChangeCount(String languageCode) {
        LocalDate currentDate = LocalDate.now();


        DailyStats stats = dailyStatsRepository.findByLanguageCodeAndDate(languageCode, currentDate)
                .orElse(new DailyStats(languageCode, currentDate, 0));


        System.out.println("Current change count for " + languageCode + " on " + currentDate + ": " + stats.getChangeCount());


        stats.setChangeCount(stats.getChangeCount() + 1);


        System.out.println("Updated change count for " + languageCode + " on " + currentDate + ": " + stats.getChangeCount());

        dailyStatsRepository.save(stats);
    }


    public int getChangeCountForDate(String languageCode, LocalDate date) {
        return dailyStatsRepository.findByLanguageCodeAndDate(languageCode, date)
                .map(DailyStats::getChangeCount)
                .orElse(0);
    }
}
