package com.szymonjedrzychowski;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Main.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest {

    List<Artist> artists;
    List<Song> songs;
    List<Record> records;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArtistRepository artistRepository;
    @MockBean
    private RecordRepository recordRepository;
    @MockBean
    private SongRepository songRepository;

    @MockBean
    private Main main;

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

        artists = List.of(artist);
        songs = List.of(song, song2);
        records = List.of(record1, record2, record3);
    }

    @Test
    void getArtists() throws Exception {
        when(main
                .getArtists())
                .thenReturn(artists);
        mockMvc.perform(get("/api/v1/artists"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getSongs() throws Exception {
        when(main
                .getSongs())
                .thenReturn(songs);
        mockMvc.perform(get("/api/v1/songs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getTopSongs() throws Exception {
        List<SongData> expected = new ArrayList<>();
        expected.add(new SongData("tittle2", "artist", (long) 300000, (long) 1));
        expected.add(new SongData("tittle", "artist", (long) 150000, (long) 2));

        when(main
                .getTopSongs(Date.valueOf("2020-03-01"), Date.valueOf("2020-04-30")))
                .thenReturn(expected);
        mockMvc.perform(get("/api/v1/topSongs?startDate=1.03.2020&endDate=30.04.2020"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getTopArtists() throws Exception {
        List<ArtistData> expected = new ArrayList<>();
        expected.add(new ArtistData("artist", (long) 450000, (long) 3));

        when(main
                .getTopArtists(Date.valueOf("2020-03-01"), Date.valueOf("2020-04-30")))
                .thenReturn(expected);
        mockMvc.perform(get("/api/v1/topArtists?startDate=1.03.2020&endDate=30.04.2020"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getRecordsSong() throws Exception {
        when(main
                .getRecords(Date.valueOf("2020-03-01"), Date.valueOf("2020-04-30"), 1, null))
                .thenReturn(List.of(records.get(0), records.get(1)));
        mockMvc.perform(get("/api/v1/records?startDate=1.03.2020&endDate=30.04.2020&songId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getRecordsArtist() throws Exception {
        when(main
                .getRecords(Date.valueOf("2020-03-01"), Date.valueOf("2020-04-30"), null, 1))
                .thenReturn(records);
        mockMvc.perform(get("/api/v1/records?startDate=1.03.2020&endDate=30.04.2020&artistId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void getRecords() throws Exception {
        when(main
                .getRecords(Date.valueOf("2020-03-01"), Date.valueOf("2020-04-30"), null, null))
                .thenReturn(records);
        mockMvc.perform(get("/api/v1/records?startDate=1.03.2020&endDate=30.04.2020"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }
}