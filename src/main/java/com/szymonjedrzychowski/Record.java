package com.szymonjedrzychowski;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
public class Record {
    @Id
    @SequenceGenerator(
            name = "record_id_sequence",
            sequenceName = "record_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "record_id_sequence"
    )
    @Column(name = "record_id", nullable = false)
    private Integer recordId;
    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;
    @Column(name = "ms_played", nullable = false)
    private Integer msPlayed;
    @Column(name = "time_played", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timePlayed;

    public Record(Integer recordId, Song song, Integer msPlayed, Date timePlayed) {
        this.recordId = recordId;
        this.song = song;
        this.msPlayed = msPlayed;
        this.timePlayed = timePlayed;
    }

    public Record() {
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Integer getMsPlayed() {
        return msPlayed;
    }

    public void setMsPlayed(Integer msPlayed) {
        this.msPlayed = msPlayed;
    }

    public Date getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Date timePlayed) {
        this.timePlayed = timePlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(recordId, record.recordId) && Objects.equals(song, record.song) && Objects.equals(msPlayed, record.msPlayed) && Objects.equals(timePlayed, record.timePlayed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, song, msPlayed, timePlayed);
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordID=" + recordId +
                ", song=" + song +
                ", msPlayed=" + msPlayed +
                ", timePlayed='" + timePlayed + '\'' +
                '}';
    }
}
