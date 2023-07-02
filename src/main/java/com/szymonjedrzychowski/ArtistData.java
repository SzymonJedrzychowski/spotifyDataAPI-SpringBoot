package com.szymonjedrzychowski;

import java.util.Objects;

public class ArtistData {
    private Integer artistId;
    private Long msPlayed;
    private Long count;

    public ArtistData(Integer artistId, Long msPlayed, Long count) {
        this.artistId = artistId;
        this.msPlayed = msPlayed;
        this.count = count;
    }

    public ArtistData() {
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
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
        return Objects.equals(artistId, that.artistId) && Objects.equals(msPlayed, that.msPlayed) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, msPlayed, count);
    }

    @Override
    public String toString() {
        return "ArtistData{" +
                "artistId=" + artistId +
                ", msPlayed=" + msPlayed +
                ", count=" + count +
                '}';
    }
}
