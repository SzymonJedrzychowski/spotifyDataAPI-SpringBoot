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

import static org.assertj.core.api.Assertions.assertThat;

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
    void findBySong() {
        List<Record> expected = recordRepository.findBySong(Date.valueOf("2020-03-01"), Date.valueOf("2020-03-31"), 1);

        assertThat(expected).hasSize(2);
    }

    @Test
    void findByArtist() {
        List<Record> expected = recordRepository.findByArtist(Date.valueOf("2020-03-01"), Date.valueOf("2020-04-30"), 1);

        assertThat(expected).hasSize(3);
    }

    @Test
    void findByDate() {
        List<Record> expected = recordRepository.findByDate(Date.valueOf("2020-03-01"), Date.valueOf("2020-03-10"));

        assertThat(expected).hasSize(1);
    }

    @Test
    void findTopByOrderByTimePlayedAsc() {
        Record expected = recordRepository.findTopByOrderByTimePlayedAsc();

        assertThat(expected.getRecordId()).isEqualTo(1);
    }

    @Test
    void findTopByOrderByTimePlayedDesc() {
        Record expected = recordRepository.findTopByOrderByTimePlayedDesc();

        assertThat(expected.getRecordId()).isEqualTo(3);
    }

    @Test
    void findTopSongs() {
        List<SongData> expected = recordRepository.findTopSongs(Date.valueOf("2020-03-01"), Date.valueOf("2020-03-31"));

        assertThat(expected.get(0).getTimePlayed()).isEqualTo(150000);
    }

    @Test
    void findTopArtists() {
        List<ArtistData> expected = recordRepository.findTopArtists(Date.valueOf("2020-03-01"), Date.valueOf("2020-03-31"));

        assertThat(expected.get(0).getTimePlayed()).isEqualTo(150000);
    }

}