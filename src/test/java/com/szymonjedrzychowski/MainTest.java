package com.szymonjedrzychowski;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Main.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Instant> instantList = List.of(
            Instant.parse("2020-03-01T00:00:00Z"),
            Instant.parse("2020-03-30T00:00:00Z"),
            Instant.parse("2020-03-31T00:00:00Z"),
            Instant.parse("2020-04-01T00:00:00Z"),
            Instant.parse("2020-04-30T00:00:00Z")
    );

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
    void createDatabase() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());

        Artist artist = new Artist(1, "artist", null);
        Song song = new Song(1, "title", artist, null);
        Song song2 = new Song(2, "title2", artist, null);
        Record record1 = new Record(1, song, 100000, instantList.get(0));
        Record record2 = new Record(2, song, 50000, instantList.get(1));
        Record record3 = new Record(3, song2, 300000, instantList.get(3));


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
        MvcResult jsonResult = mockMvc.perform(get("/api/v1/artists"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = jsonResult.getResponse().getContentAsString();
        Artist[] result = objectMapper.readValue(resultAsString, Artist[].class);

        assertThat(artists).isEqualTo(Arrays.stream(result).toList());
    }

    @Test
    void getSongs() throws Exception {
        List<SongInformation> expected = List.of(
                new SongInformation(1, "title", 1),
                new SongInformation(2, "title2", 1)
        );

        when(main
                .getSongs())
                .thenReturn(expected);
        MvcResult jsonResult = mockMvc.perform(get("/api/v1/songs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = jsonResult.getResponse().getContentAsString();
        SongInformation[] result = objectMapper.readValue(resultAsString, SongInformation[].class);

        assertThat(expected).isEqualTo(Arrays.stream(result).toList());
    }

    @Test
    void getTopArtists() throws Exception {
        List<ArtistData> expected = new ArrayList<>();
        expected.add(new ArtistData(1, (long) 450000, (long) 3));

        when(main
                .getTopArtists(instantList.get(0), instantList.get(4)))
                .thenReturn(expected);
        MvcResult jsonResult = mockMvc.perform(get("/api/v1/topArtists?startDate=2020-03-01T00:00:00Z&endDate=2020-04-30T00:00:00Z"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = jsonResult.getResponse().getContentAsString();
        ArtistData[] result = objectMapper.readValue(resultAsString, ArtistData[].class);

        assertThat(expected).isEqualTo(Arrays.stream(result).toList());
    }

    @Test
    void getTopSongs() throws Exception {
        List<SongData> expected = new ArrayList<>();
        expected.add(new SongData(1, (long) 300000, (long) 1));
        expected.add(new SongData(1, (long) 150000, (long) 2));

        when(main
                .getTopSongs(instantList.get(0), instantList.get(4)))
                .thenReturn(expected);
        MvcResult jsonResult = mockMvc.perform(get("/api/v1/topSongs?startDate=2020-03-01T00:00:00Z&endDate=2020-04-30T00:00:00Z"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = jsonResult.getResponse().getContentAsString();
        SongData[] result = objectMapper.readValue(resultAsString, SongData[].class);

        assertThat(expected).isEqualTo(Arrays.stream(result).toList());
    }

    @Test
    void getEdgeRecords() throws Exception {
        List<RecordData> expected = new ArrayList<>();
        expected.add(new RecordData(1, 100000, instantList.get(0)));
        expected.add(new RecordData(2, 300000, instantList.get(3)));

        when(main
                .getEdgeRecords())
                .thenReturn(expected);
        MvcResult jsonResult = mockMvc.perform(get("/api/v1/edgeRecords"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = jsonResult.getResponse().getContentAsString();
        RecordData[] result = objectMapper.readValue(resultAsString, RecordData[].class);

        assertThat(expected).isEqualTo(Arrays.stream(result).toList());
    }

    @Test
    void getRecordsSong() throws Exception {
        List<RecordData> expected = new ArrayList<>();
        expected.add(new RecordData(1, 100000, instantList.get(0)));
        expected.add(new RecordData(1, 50000, instantList.get(1)));

        when(main
                .getRecords(instantList.get(0), instantList.get(4), 1, null))
                .thenReturn(expected);
        MvcResult jsonResult = mockMvc.perform(get("/api/v1/records?startDate=2020-03-01T00:00:00Z&endDate=2020-04-30T00:00:00Z&songId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = jsonResult.getResponse().getContentAsString();
        RecordData[] result = objectMapper.readValue(resultAsString, RecordData[].class);

        assertThat(expected).isEqualTo(Arrays.stream(result).toList());
    }

    @Test
    void getRecordsArtist() throws Exception {
        List<RecordData> expected = new ArrayList<>();
        expected.add(new RecordData(1, 100000, instantList.get(0)));
        expected.add(new RecordData(1, 50000, instantList.get(1)));
        expected.add(new RecordData(2, 300000, instantList.get(3)));

        when(main
                .getRecords(instantList.get(0), instantList.get(4), null, 1))
                .thenReturn(expected);
        MvcResult jsonResult = mockMvc.perform(get("/api/v1/records?startDate=2020-03-01T00:00:00Z&endDate=2020-04-30T00:00:00Z&artistId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = jsonResult.getResponse().getContentAsString();
        RecordData[] result = objectMapper.readValue(resultAsString, RecordData[].class);

        assertThat(expected).isEqualTo(Arrays.stream(result).toList());
    }

    @Test
    void getRecords() throws Exception {
        List<RecordData> expected = new ArrayList<>();
        expected.add(new RecordData(1, 100000, instantList.get(0)));
        expected.add(new RecordData(1, 50000, instantList.get(1)));
        expected.add(new RecordData(2, 300000, instantList.get(3)));

        when(main
                .getRecords(instantList.get(0), instantList.get(4), null, null))
                .thenReturn(expected);
        MvcResult jsonResult = mockMvc.perform(get("/api/v1/records?startDate=2020-03-01T00:00:00Z&endDate=2020-04-30T00:00:00Z"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = jsonResult.getResponse().getContentAsString();
        RecordData[] result = objectMapper.readValue(resultAsString, RecordData[].class);

        assertThat(expected).isEqualTo(Arrays.stream(result).toList());
    }
}