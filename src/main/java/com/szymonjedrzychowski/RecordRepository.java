package com.szymonjedrzychowski;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    List<Record> findBySongSongId(Integer songId);

    List<Record> findBySongArtistArtistId(Integer artistId);

    @Query("SELECT new com.szymonjedrzychowski.SongData(FUNCTION('YEAR', u.timePlayed) as year, FUNCTION('MONTH', u.timePlayed) as Month, sum(msPlayed) as timePlayed, count(*) as count) " +
            "from Record u " +
            "where u.timePlayed > 30000 " +
            "group by FUNCTION('YEAR', u.timePlayed), FUNCTION('MONTH', u.timePlayed)")
    List<SongData> findSongCountMonthly();

    @Query("SELECT new com.szymonjedrzychowski.SongData(FUNCTION('YEAR', u.timePlayed) as year, FUNCTION('MONTH', u.timePlayed) as month, FUNCTION('WEEK', u.timePlayed) as week, FUNCTION('DAY', u.timePlayed) as day, sum(msPlayed) as timePlayed, count(*) as count) " +
            "from Record u " +
            "where u.msPlayed > 30000 and FUNCTION('YEAR', u.timePlayed) = ?1 and FUNCTION('MONTH', u.timePlayed) = ?2 " +
            "group by FUNCTION('YEAR', u.timePlayed), FUNCTION('MONTH', u.timePlayed), FUNCTION('WEEK', u.timePlayed), FUNCTION('DAY', u.timePlayed)")
    List<SongData> findSongCountDaily(Integer year, Integer month);

    @Query("SELECT new com.szymonjedrzychowski.SongData(FUNCTION('YEAR', u.timePlayed) as year, FUNCTION('MONTH', u.timePlayed) as month, FUNCTION('WEEK', u.timePlayed) as week, FUNCTION('DAY', u.timePlayed) as day, sum(msPlayed) as timePlayed, count(*) as count) " +
            "from Record u " +
            "join u.song s " +
            "join s.artist a " +
            "where u.msPlayed > 30000 and FUNCTION('YEAR', u.timePlayed) = ?1 and FUNCTION('MONTH', u.timePlayed) = ?2 and a.artistId = ?3 " +
            "group by FUNCTION('YEAR', u.timePlayed), FUNCTION('MONTH', u.timePlayed), FUNCTION('WEEK', u.timePlayed), FUNCTION('DAY', u.timePlayed)")
    List<SongData> findSongCountDailyByAuthor(Integer year, Integer month, Integer artistId);

    @Query("SELECT new com.szymonjedrzychowski.SongData(FUNCTION('YEAR', u.timePlayed) as year, FUNCTION('MONTH', u.timePlayed) as month, FUNCTION('WEEK', u.timePlayed) as week, FUNCTION('DAY', u.timePlayed) as day, sum(msPlayed) as timePlayed, count(*) as count) " +
            "from Record u " +
            "join u.song s " +
            "join s.artist a " +
            "where u.msPlayed > 30000 and FUNCTION('YEAR', u.timePlayed) = ?1 and FUNCTION('MONTH', u.timePlayed) = ?2 and s.songId = ?3 " +
            "group by FUNCTION('YEAR', u.timePlayed), FUNCTION('MONTH', u.timePlayed), FUNCTION('WEEK', u.timePlayed), FUNCTION('DAY', u.timePlayed)")
    List<SongData> findSongCountDailyBySong(Integer year, Integer month, Integer songId);

    @Query("SELECT new com.szymonjedrzychowski.SongData(FUNCTION('YEAR', u.timePlayed) as year, FUNCTION('MONTH', u.timePlayed) as month, FUNCTION('WEEK', u.timePlayed) as week, sum(msPlayed) as timePlayed, count(*) as count) " +
            "from Record u " +
            "where u.msPlayed > 30000 and FUNCTION('YEAR', u.timePlayed) = ?1 " +
            "group by FUNCTION('YEAR', u.timePlayed), FUNCTION('MONTH', u.timePlayed), FUNCTION('WEEK', u.timePlayed)")
    List<SongData> findSongCountWeekly(Integer year);
}
