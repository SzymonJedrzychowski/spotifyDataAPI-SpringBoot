package com.szymonjedrzychowski;

import java.util.Objects;

public class SongData {
    private String title;
    private String artistName;
    private Long timePlayed;
    private Long count;

    public SongData(String title, String artistName, Long timePlayed, Long count) {
        this.title = title;
        this.artistName = artistName;
        this.timePlayed = timePlayed;
        this.count = count;
    }

    public SongData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
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
        return Objects.equals(title, songData.title) && Objects.equals(artistName, songData.artistName) && Objects.equals(timePlayed, songData.timePlayed) && Objects.equals(count, songData.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artistName, timePlayed, count);
    }

    @Override
    public String toString() {
        return "SongData{" +
                "title='" + title + '\'' +
                ", artistName='" + artistName + '\'' +
                ", timePlayed=" + timePlayed +
                ", count=" + count +
                '}';
    }
}
