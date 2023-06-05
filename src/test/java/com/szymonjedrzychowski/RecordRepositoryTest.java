package com.szymonjedrzychowski;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecordRepositoryTest {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private SongRepository songRepository;

    @BeforeAll
    void createDatabase() {
        Artist artist = new Artist(1, "artist", new HashSet<>());
        Song song = new Song(1, "tittle", artist, new HashSet<>());
        Song song2 = new Song(2, "tittle2", artist, new HashSet<>());
        Record record1 = new Record(1, song, 100000, Date.valueOf("2020-03-01"));
        Record record2 = new Record(2, song, 50000, Date.valueOf("2020-03-30"));
        Record record3 = new Record(3, song2, 300000, Date.valueOf("2020-04-01"));


        artistRepository.save(artist);
        songRepository.save(song);
        songRepository.save(song2);
        recordRepository.save(record1);
        recordRepository.save(record2);
        recordRepository.save(record3);
    }

    @Test
    void findBySongSongIdPositive() {
        List<Record> expected = recordRepository.findBySongSongId(1);

        assertThat(expected).hasSize(2);
    }

    @Test
    void findBySongSongIdNegative() {
        List<Record> expected = recordRepository.findBySongSongId(0);

        assertThat(expected).hasSize(0);
    }

    @Test
    void findBySongArtistArtistIdPositive() {
        List<Record> expected = recordRepository.findBySongArtistArtistId(1);

        assertThat(expected).hasSize(3);
    }

    @Test
    void findBySongArtistArtistIdNegative() {
        List<Record> expected = recordRepository.findBySongArtistArtistId(0);

        assertThat(expected).hasSize(0);
    }

    @Test
    void findSongCountMonthly() {
        List<SongData> expected = recordRepository.findSongCountMonthly();

        assertThat(expected).hasSize(2);
        assertThat(expected.get(0).getTimePlayed()).isEqualTo(150000);
    }

    @Test
    void findSongCountDailyPositive() {
        List<SongData> expected = recordRepository.findSongCountDaily(2020, 3);

        assertThat(expected).hasSize(2);
        assertThat(expected.get(0).getTimePlayed()).isEqualTo(100000);
    }

    @Test
    void findSongCountDailyNegative() {
        List<SongData> expected = recordRepository.findSongCountDaily(2020, 5);

        assertThat(expected).hasSize(0);
    }

    @Test
    void findSongCountDailyByAuthorPositive() {
        List<SongData> expected = recordRepository.findSongCountDailyByAuthor(2020, 3, 1);

        assertThat(expected).hasSize(2);
        assertThat(expected.get(0).getTimePlayed()).isEqualTo(100000);
    }

    @Test
    void findSongCountDailyByAuthorNegativeAuthor() {
        List<SongData> expected = recordRepository.findSongCountDailyByAuthor(2020, 3, 0);

        assertThat(expected).hasSize(0);
    }

    @Test
    void findSongCountDailyByAuthorNegativeDate() {
        List<SongData> expected = recordRepository.findSongCountDailyByAuthor(2020, 5, 1);

        assertThat(expected).hasSize(0);
    }

    @Test
    void findSongCountDailyBySongPositive() {
        List<SongData> expected = recordRepository.findSongCountDailyBySong(2020, 3, 1);

        assertThat(expected).hasSize(2);
        assertThat(expected.get(0).getTimePlayed()).isEqualTo(100000);
    }

    @Test
    void findSongCountDailyBySongNegativeSong() {
        List<SongData> expected = recordRepository.findSongCountDailyBySong(2020, 3, 0);

        assertThat(expected).hasSize(0);
    }

    @Test
    void findSongCountDailyBySongNegativeDate() {
        List<SongData> expected = recordRepository.findSongCountDailyBySong(2020, 5, 1);

        assertThat(expected).hasSize(0);
    }

    @Test
    void findSongCountWeeklyPositive() {
        List<SongData> expected = recordRepository.findSongCountWeekly(2020);

        assertThat(expected).hasSize(3);
        assertThat(expected.get(0).getTimePlayed()).isEqualTo(100000);
    }


    @Test
    void findSongCountWeeklyNegative() {
        List<SongData> expected = recordRepository.findSongCountWeekly(2021);

        assertThat(expected).hasSize(0);
    }
}