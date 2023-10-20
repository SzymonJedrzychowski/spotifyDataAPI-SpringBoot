package com.szymonjedrzychowski;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Query("SELECT new com.szymonjedrzychowski.RecordData(song.songId, msPlayed, timePlayed) " +
            "FROM Record u " +
            "WHERE timePlayed >= ?1 AND timePlayed <= ?2 AND song.songId = ?3")
    List<RecordData> findBySong(Instant startDate, Instant endDate, Integer songId);

    @Query("SELECT new com.szymonjedrzychowski.RecordData(song.songId, msPlayed, timePlayed) " +
            "FROM Record u " +
            "WHERE timePlayed >= ?1 AND timePlayed <= ?2 AND song.artist = ?3")
    List<RecordData> findByArtist(Instant startDate, Instant endDate, String artist);

    @Query("SELECT new com.szymonjedrzychowski.RecordData(song.songId, msPlayed, timePlayed) " +
            "FROM Record u " +
            "WHERE timePlayed >= ?1 AND timePlayed <= ?2")
    List<RecordData> findByDate(Instant startDate, Instant endDate);

    @Query("SELECT new com.szymonjedrzychowski.RecordData(song.songId, msPlayed, timePlayed) " +
            "FROM Record u " +
            "ORDER BY timePlayed ASC " +
            "LIMIT 1")
    RecordData findFirstEntry();

    @Query("SELECT new com.szymonjedrzychowski.RecordData(song.songId, msPlayed, timePlayed) " +
            "FROM Record u " +
            "ORDER BY timePlayed DESC " +
            "LIMIT 1")
    RecordData findLastEntry();

    @Query("SELECT new com.szymonjedrzychowski.SongData(song.songId, sum(msPlayed) as msPlayed, count(*) as count) " +
            "FROM Record u " +
            "WHERE timePlayed >= ?1 AND timePlayed <= ?2 " +
            "GROUP BY song.songId " +
            "ORDER BY msPlayed DESC")
    List<SongData> findTopSongs(Instant startDate, Instant endDate);

    @Query("SELECT new com.szymonjedrzychowski.SongData(song.songId, sum(msPlayed) as msPlayed, count(*) as count) " +
            "FROM Record u " +
            "WHERE timePlayed >= ?1 AND timePlayed <= ?2 AND song.artist = ?3 " +
            "GROUP BY song.songId " +
            "ORDER BY msPlayed DESC")
    List<SongData> findTopSongsByArtist(Instant startDate, Instant endDate, String artist);

    @Query("SELECT new com.szymonjedrzychowski.ArtistData(song.artist, sum(msPlayed) as msPlayed, count(*) as count) " +
            "FROM Record u " +
            "WHERE timePlayed >= ?1 AND timePlayed <= ?2 " +
            "GROUP BY song.artist " +
            "ORDER BY msPlayed DESC")
    List<ArtistData> findTopArtists(Instant startDate, Instant endDate);
}
