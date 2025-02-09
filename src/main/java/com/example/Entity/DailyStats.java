package com.example.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_stats")
public class DailyStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String languageCode;
    private LocalDate date;
    private int changeCount;


    public DailyStats() {}


    public DailyStats(String languageCode, LocalDate date, int changeCount) {
        this.languageCode = languageCode;
        this.date = date;
        this.changeCount = changeCount;
    }
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(int changeCount) {
        this.changeCount = changeCount;
    }
}
