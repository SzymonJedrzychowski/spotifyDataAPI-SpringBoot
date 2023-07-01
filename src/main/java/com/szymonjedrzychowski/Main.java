package com.szymonjedrzychowski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
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

    @GetMapping("/artists")
    public List<Artist> getArtists() {
        return artistRepository.findAll();
    }

    @GetMapping("/songs")
    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    @GetMapping("/topArtists")
    public List<ArtistData> getTopArtists(@RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate) {
        return recordRepository.findTopArtists(startDate, endDate);
    }

    @GetMapping("/topSongs")
    public List<SongData> getTopSongs(@RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate) {
        return recordRepository.findTopSongs(startDate, endDate);
    }

    @GetMapping("/edgeDates")
    public List<Record> getEdgeDates(){
        return Arrays.asList(recordRepository.findTopByOrderByTimePlayedAsc(), recordRepository.findTopByOrderByTimePlayedDesc());
    }

    @GetMapping("/records")
    public List<Record> getRecords(@RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate, @RequestParam(required = false) Integer songId, @RequestParam(required = false) Integer artistId) {
        if (songId != null) {
            return recordRepository.findBySong(startDate, endDate, songId);
        } else if (artistId != null) {
            return recordRepository.findByArtist(startDate, endDate, artistId);
        }
        return recordRepository.findByDate(startDate, endDate);
    }
}
