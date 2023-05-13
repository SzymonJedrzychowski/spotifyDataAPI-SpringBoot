package com.szymonjedrzychowski;

import java.util.Objects;

public class SongData {
    private Integer year;
    private Integer month;
    private Integer week;
    private Integer day;
    private Long timePlayed;
    private Long count;

    public SongData(Integer year, Integer month, Long timePlayed, Long count) {
        this.year = year;
        this.month = month;
        this.timePlayed = timePlayed;
        this.count = count;
    }

    public SongData(Integer year, Integer month, Integer week, Long timePlayed, Long count) {
        this.year = year;
        this.month = month;
        this.week = week;
        this.timePlayed = timePlayed;
        this.count = count;
    }

    public SongData(Integer year, Integer month, Integer week, Integer day, Long timePlayed, Long count) {
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
        this.timePlayed = timePlayed;
        this.count = count;
    }

    public SongData() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongData songData = (SongData) o;
        return Objects.equals(year, songData.year) && Objects.equals(month, songData.month) && Objects.equals(week, songData.week) && Objects.equals(day, songData.day) && Objects.equals(timePlayed, songData.timePlayed) && Objects.equals(count, songData.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, week, day, timePlayed, count);
    }

    @Override
    public String toString() {
        return "SongData{" +
                "year=" + year +
                ", month=" + month +
                ", week=" + week +
                ", day=" + day +
                ", timePlayed=" + timePlayed +
                ", count=" + count +
                '}';
    }
}
