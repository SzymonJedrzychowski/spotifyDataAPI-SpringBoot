package com.szymonjedrzychowski;

import java.util.Objects;

public class SongInformation {
    private Integer songId;
    private String title;
    private String artist;

    public SongInformation(Integer songId, String title, String artist) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongInformation that = (SongInformation) o;
        return Objects.equals(songId, that.songId) && Objects.equals(title, that.title) && Objects.equals(artist, that.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, title, artist);
    }

    @Override
    public String toString() {
        return "SongInformation{" +
                "songId=" + songId +
                ", title='" + title + '\'' +
                ", artist=" + artist +
                '}';
    }
}
