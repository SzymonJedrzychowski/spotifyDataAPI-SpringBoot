package com.szymonjedrzychowski;

import java.util.Objects;

public class ArtistData {
    private String artistName;
    private Long timePlayed;
    private Long count;

    public ArtistData(String artistName, Long timePlayed, Long count) {
        this.artistName = artistName;
        this.timePlayed = timePlayed;
        this.count = count;
    }

    public ArtistData() {
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
        ArtistData that = (ArtistData) o;
        return Objects.equals(artistName, that.artistName) && Objects.equals(timePlayed, that.timePlayed) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistName, timePlayed, count);
    }

    @Override
    public String toString() {
        return "ArtistData{" +
                "artistName='" + artistName + '\'' +
                ", timePlayed=" + timePlayed +
                ", count=" + count +
                '}';
    }
}
