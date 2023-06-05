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
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void getRecordsSong() throws Exception {
        when(main
                .getRecords(1, null))
                .thenReturn(List.of(records.get(0), records.get(1)));
        mockMvc.perform(get("/api/v1/records?songId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getRecordsArtist() throws Exception {
        when(main
                .getRecords(null, 1))
                .thenReturn(records);
        mockMvc.perform(get("/api/v1/records?artistId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void getRecords() throws Exception {
        when(main
                .getRecords(null, null))
                .thenReturn(records);
        mockMvc.perform(get("/api/v1/records"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void getSongCountDailySong() throws Exception {
        List<SongData> songData = List.of(new SongData(2020, 3, 9, 1, (long) 100000, (long) 1), new SongData(2020, 3, 14, 30, (long) 50000, (long) 1));
        when(main
                .getSongCount(2020, 3, null, 1))
                .thenReturn(songData);
        mockMvc.perform(get("/api/v1/songData?year=2020&month=3&songId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void getSongCountDailyArtist() throws Exception {
        List<SongData> songData = List.of(new SongData(2020, 3, 9, 1, (long) 100000, (long) 1), new SongData(2020, 3, 14, 30, (long) 50000, (long) 1));
        when(main
                .getSongCount(2020, 3, 1, null))
                .thenReturn(songData);
        mockMvc.perform(get("/api/v1/songData?year=2020&month=3&artistId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void getSongCountDaily() throws Exception {
        List<SongData> songData = List.of(new SongData(2020, 3, 9, 1, (long) 100000, (long) 1), new SongData(2020, 3, 14, 30, (long) 50000, (long) 1));
        when(main
                .getSongCount(2020, 3, null, null))
                .thenReturn(songData);
        mockMvc.perform(get("/api/v1/songData?year=2020&month=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void getSongCountWeekly() throws Exception {
        List<SongData> songData = List.of(new SongData(2020, 3, 9, (long) 100000, (long) 1), new SongData(2020, 3, 14, 30, (long) 50000, (long) 1), new SongData(2020, 4, 14, (long) 300000, (long) 1));
        when(main
                .getSongCount(2020, null, null, null))
                .thenReturn(songData);
        mockMvc.perform(get("/api/v1/songData?year=2020"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

    }

    @Test
    void getSongCountMonthly() throws Exception {
        List<SongData> songData = List.of(new SongData(2020, 3, (long) 150000, (long) 2), new SongData(2020, 4, (long) 300000, (long) 1));
        when(main
                .getSongCount(null, null, null, null))
                .thenReturn(songData);
        mockMvc.perform(get("/api/v1/songData"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }
}