package com.szymonjedrzychowski;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    @Query("SELECT new com.szymonjedrzychowski.SongInformation(songId, title, artist) FROM Song u ORDER BY u.songId ASC")
    List<SongInformation> findAllSongs();

    @Query("SELECT DISTINCT u.artist FROM Song u")
    List<String> findAllArtists();
}
