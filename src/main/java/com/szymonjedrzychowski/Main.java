package com.szymonjedrzychowski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class Main {
    private final SongRepository songRepository;
    private final RecordRepository recordRepository;

    public Main(SongRepository songRepository, RecordRepository recordRepository) {
        this.songRepository = songRepository;
        this.recordRepository = recordRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/artists")
    public List<String> getArtists() {
        return songRepository.findAllArtists();
    }

    @GetMapping("/songs")
    public List<SongInformation> getSongs() {
        return songRepository.findAllSongs();
    }

    @GetMapping("/topArtists")
    public List<ArtistData> getTopArtists(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate) {
        return recordRepository.findTopArtists(startDate, endDate);
    }

    @GetMapping("/topSongs")
    public List<SongData> getTopSongs(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate, @RequestParam(required = false) String artist) {
        if (artist != null) {
            return recordRepository.findTopSongsByArtist(startDate, endDate, artist);
        }
        return recordRepository.findTopSongs(startDate, endDate);
    }

    @GetMapping("/edgeRecords")
    public List<RecordData> getEdgeRecords() {
        return Arrays.asList(recordRepository.findFirstEntry(), recordRepository.findLastEntry());
    }

    @GetMapping("/records")
    public List<RecordData> getRecords(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate, @RequestParam(required = false) Integer songId, @RequestParam(required = false) String artist) {
        if (songId != null) {
            return recordRepository.findBySong(startDate, endDate, songId);
        } else if (artist != null) {
            return recordRepository.findByArtist(startDate, endDate, artist);
        }
        return recordRepository.findByDate(startDate, endDate);
    }
}
