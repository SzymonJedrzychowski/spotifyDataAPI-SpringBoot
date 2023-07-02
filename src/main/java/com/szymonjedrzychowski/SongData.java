package com.szymonjedrzychowski;

import java.util.Objects;

public class SongData {
    private Integer SongId;
    private Long msPlayed;
    private Long count;

    public SongData(Integer songId, Long msPlayed, Long count) {
        SongId = songId;
        this.msPlayed = msPlayed;
        this.count = count;
    }

    public SongData() {
    }

    public Integer getSongId() {
        return SongId;
    }

    public void setSongId(Integer songId) {
        SongId = songId;
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
        SongData songData = (SongData) o;
        return Objects.equals(SongId, songData.SongId) && Objects.equals(msPlayed, songData.msPlayed) && Objects.equals(count, songData.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(SongId, msPlayed, count);
    }

    @Override
    public String toString() {
        return "SongData{" +
                "SongId=" + SongId +
                ", msPlayed=" + msPlayed +
                ", count=" + count +
                '}';
    }
}
