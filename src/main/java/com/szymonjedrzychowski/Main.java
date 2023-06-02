package com.szymonjedrzychowski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final RecordRepository recordRepository;

    public Main(ArtistRepository artistRepository, SongRepository songRepository, RecordRepository recordRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.recordRepository = recordRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("api/v1/artists")
    public List<Artist> getArtists() {
        return artistRepository.findAll();
    }

    @GetMapping("api/v1/songs")
    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    @GetMapping("api/v1/records")
    public List<Record> getRecords(@RequestParam(required = false) Integer songId, @RequestParam(required = false) Integer artistId) {
        if (songId != null) {
            return recordRepository.findBySongSongId(songId);
        } else if (artistId != null) {
            return recordRepository.findBySongArtistArtistId(artistId);
        }
        return recordRepository.findAll();
    }

    @GetMapping("api/v1/songData")
    public List<SongData> getSongCount(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer artistId, @RequestParam(required = false) Integer songId) {
        if (year != null && month != null) {
            if (songId != null) {
                return recordRepository.findSongCountDailyBySong(year, month, songId);
            } else if (artistId != null) {
                return recordRepository.findSongCountDailyByAuthor(year, month, artistId);
            }
            return recordRepository.findSongCountDaily(year, month);
        } else if (year != null) {
            return recordRepository.findSongCountWeekly(year);
        }
        return recordRepository.findSongCountMonthly();
    }
}
