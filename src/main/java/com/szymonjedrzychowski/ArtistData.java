package com.szymonjedrzychowski;

import java.util.Objects;

public class ArtistData {
    private String artist;
    private Long msPlayed;
    private Long count;

    public ArtistData(String artist, Long msPlayed, Long count) {
        this.artist = artist;
        this.msPlayed = msPlayed;
        this.count = count;
    }

    public ArtistData() {
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Long getMsPlayed() {
        return msPlayed;
    }

    public void setMsPlayed(Long msPlayed) {
        this.msPlayed = msPlayed;
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
        return Objects.equals(artist, that.artist) && Objects.equals(msPlayed, that.msPlayed) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, msPlayed, count);
    }

    @Override
    public String toString() {
        return "ArtistData{" +
                "artist=" + artist +
                ", msPlayed=" + msPlayed +
                ", count=" + count +
                '}';
    }
}
