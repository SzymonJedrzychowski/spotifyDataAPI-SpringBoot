package com.szymonjedrzychowski;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Artist {
    @Id
    @SequenceGenerator(
            name = "artist_id_sequence",
            sequenceName = "artist_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "artist_id_sequence"
    )
    @Column(name="artist_id", nullable = false)
    private Integer artistId;
    private String name;
    @OneToMany(mappedBy = "artist")
    @JsonIgnore
    private Set<Song> songSet;

    public Artist(Integer artistId, String name, Set<Song> songSet) {
        this.artistId = artistId;
        this.name = name;
        this.songSet = songSet;
    }

    public Artist() {
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Song> getSongSet() {
        return songSet;
    }

    public void setSongSet(Set<Song> songSet) {
        this.songSet = songSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(artistId, artist.artistId) && Objects.equals(name, artist.name) && Objects.equals(songSet, artist.songSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, name, songSet);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistID=" + artistId +
                ", name='" + name + '\'' +
                ", songList=" + songSet +
                '}';
    }
}
