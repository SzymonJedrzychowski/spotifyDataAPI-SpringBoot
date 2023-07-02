package com.szymonjedrzychowski;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Song {
    @Id
    @SequenceGenerator(
            name = "song_id_sequence",
            sequenceName = "song_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "song_id_sequence"
    )
    @Column(name = "song_id", nullable = false)
    private Integer songId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @OneToMany(mappedBy = "song")
    @JsonIgnore
    private Set<Record> recordSet;

    public Song(Integer songId, String title, Artist artist, Set<Record> recordSet) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.recordSet = recordSet;
    }

    public Song() {
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Set<Record> getRecordSet() {
        return recordSet;
    }

    public void setRecordSet(Set<Record> recordSet) {
        this.recordSet = recordSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(songId, song.songId) && Objects.equals(title, song.title) && Objects.equals(artist, song.artist) && Objects.equals(recordSet, song.recordSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, title, artist, recordSet);
    }

    @Override
    public String toString() {
        return "Song{" +
                "songID=" + songId +
                ", title='" + title + '\'' +
                ", artist=" + artist +
                ", recordSet=" + recordSet +
                '}';
    }
}
