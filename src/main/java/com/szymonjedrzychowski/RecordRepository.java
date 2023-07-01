package com.szymonjedrzychowski;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Query("SELECT u " +
            "from Record u " +
            "where u.timePlayed >= ?1 and u.timePlayed <= ?2 and u.song.songId = ?3")
    List<Record> findBySong(Date startDate, Date endDate, Integer songId);

    @Query("SELECT u " +
            "from Record u " +
            "where u.timePlayed >= ?1 and u.timePlayed <= ?2 and u.song.artist.artistId = ?3")
    List<Record> findByArtist(Date startDate, Date endDate, Integer artistId);

    @Query("SELECT u " +
            "from Record u " +
            "where u.timePlayed >= ?1 and u.timePlayed <= ?2")
    List<Record> findByDate(Date startDate, Date endDate);

    Record findTopByOrderByTimePlayedAsc();

    Record findTopByOrderByTimePlayedDesc();

    @Query("SELECT new com.szymonjedrzychowski.SongData(song.title, song.artist.name, sum(msPlayed) as timePlayed, count(*) as count) " +
            "from Record u " +
            "where u.timePlayed >= ?1 and u.timePlayed <= ?2 " +
            "group by song.songId " +
            "order by timePlayed DESC")
    List<SongData> findTopSongs(Date startDate, Date endDate);

    @Query("SELECT new com.szymonjedrzychowski.ArtistData(song.artist.name, sum(msPlayed) as timePlayed, count(*) as count) " +
            "from Record u " +
            "where u.timePlayed >= ?1 and u.timePlayed <= ?2 " +
            "group by song.artist.artistId " +
            "order by timePlayed DESC")
    List<ArtistData> findTopArtists(Date startDate, Date endDate);
}
