package com.szymonjedrzychowski;

import java.time.Instant;
import java.util.Objects;

public class RecordData {
    private Integer songId;
    private Integer msPlayed;
    private Instant timePlayed;

    public RecordData(Integer songId, Integer msPlayed, Instant timePlayed) {
        this.songId = songId;
        this.msPlayed = msPlayed;
        this.timePlayed = timePlayed;
    }

    public RecordData() {
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getMsPlayed() {
        return msPlayed;
    }

    public void setMsPlayed(Integer msPlayed) {
        this.msPlayed = msPlayed;
    }

    public Instant getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Instant timePlayed) {
        this.timePlayed = timePlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordData that = (RecordData) o;
        return Objects.equals(songId, that.songId) && Objects.equals(msPlayed, that.msPlayed) && Objects.equals(timePlayed, that.timePlayed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, msPlayed, timePlayed);
    }

    @Override
    public String toString() {
        return "RecordData{" +
                "songId=" + songId +
                ", msPlayed=" + msPlayed +
                ", timePlayed=" + timePlayed +
                '}';
    }
}
