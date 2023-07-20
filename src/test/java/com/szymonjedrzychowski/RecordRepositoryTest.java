package com.szymonjedrzychowski;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecordRepositoryTest {

    private List<Instant> instantList = List.of(
            Instant.parse("2020-03-01T00:00:00Z"),
            Instant.parse("2020-03-30T00:00:00Z"),
            Instant.parse("2020-03-31T00:00:00Z"),
            Instant.parse("2020-04-01T00:00:00Z"),
            Instant.parse("2020-04-30T00:00:00Z")
    );

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private SongRepository songRepository;

    @BeforeAll
    void createDatabase() throws Exception {
        Artist artist = new Artist(1, "artist", new HashSet<>());
        Song song = new Song(1, "title", artist, new HashSet<>());
        Song song2 = new Song(2, "title2", artist, new HashSet<>());
        Record record1 = new Record(1, song, 100000, instantList.get(0));
        Record record2 = new Record(2, song, 50000, instantList.get(1));
        Record record3 = new Record(3, song2, 300000, instantList.get(3));


        artistRepository.save(artist);
        songRepository.save(song);
        songRepository.save(song2);
        recordRepository.save(record1);
        recordRepository.save(record2);
        recordRepository.save(record3);
    }

    @Test
    void findBySong() throws Exception {
        List<RecordData> result = recordRepository.findBySong(instantList.get(0), instantList.get(2), 1);

        assertThat(List.of(
                new RecordData(1, 100000, instantList.get(0)),
                new RecordData(1, 50000, instantList.get(1)))
        ).isEqualTo(result);
    }

    @Test
    void findByArtist() throws Exception {
        List<RecordData> result = recordRepository.findByArtist(instantList.get(0), instantList.get(4), 1);

        assertThat(List.of(
                new RecordData(1, 100000, instantList.get(0)),
                new RecordData(1, 50000, instantList.get(1)),
                new RecordData(2, 300000, instantList.get(3))
        )).isEqualTo(result);
    }

    @Test
    void findByDate() throws Exception {
        List<RecordData> result = recordRepository.findByDate(instantList.get(0), Instant.parse("2020-03-10T00:00:00Z"));

        assertThat(List.of(
                new RecordData(1, 100000, instantList.get(0))
        )).isEqualTo(result);
    }

    @Test
    void findFirstEntry() throws Exception {
        RecordData result = recordRepository.findFirstEntry();

        assertThat(
                new RecordData(1, 100000, instantList.get(0))
        ).isEqualTo(result);
    }

    @Test
    void findLastEntry() throws Exception {
        RecordData result = recordRepository.findLastEntry();

        assertThat(
                new RecordData(2, 300000, instantList.get(3))
        ).isEqualTo(result);
    }

    @Test
    void findTopSongs() throws Exception {
        List<SongData> result = recordRepository.findTopSongs(instantList.get(0), instantList.get(2));

        assertThat(result).isEqualTo(List.of(
                new SongData(1, (long) 150000, (long) 2)
        ));
    }

    @Test
    void findTopSongsByArtist() throws Exception {
        List<SongData> result = recordRepository.findTopSongsByArtist(instantList.get(0), instantList.get(2), 1);

        assertThat(result).isEqualTo(List.of(
                new SongData(1, (long) 150000, (long) 2)
        ));
    }

    @Test
    void findTopArtists() throws Exception {
        List<ArtistData> result = recordRepository.findTopArtists(instantList.get(0), instantList.get(2));

        assertThat(result).isEqualTo(List.of(
                new ArtistData(1, (long) 150000, (long) 2)
        ));
    }

}