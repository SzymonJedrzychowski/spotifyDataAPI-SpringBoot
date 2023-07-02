package com.szymonjedrzychowski;

import java.util.Objects;

public class SongInformation {
    private Integer songId;
    private String title;
    private Integer artistId;

    public SongInformation(Integer songId, String title, Integer artistId) {
        this.songId = songId;
        this.title = title;
        this.artistId = artistId;
    }

    public SongInformation() {
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

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongInformation that = (SongInformation) o;
        return Objects.equals(songId, that.songId) && Objects.equals(title, that.title) && Objects.equals(artistId, that.artistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, title, artistId);
    }

    @Override
    public String toString() {
        return "SongInformation{" +
                "songId=" + songId +
                ", title='" + title + '\'' +
                ", artistId=" + artistId +
                '}';
    }
}
