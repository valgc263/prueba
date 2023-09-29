package com.example.demoo.song;

import com.example.demoo.dto.SongSearchByNameDto;
import com.example.demoo.song.exceptions.ErrorMessage;
import com.example.demoo.song.exceptions.SongNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/{id}")
    public Song find(@PathVariable("id") Long id) {
        /* Exception ExceptionStatus:

        Optional<Song> optionalSong = songRepository.findById(id);
        if(optionalSong.isPresent()) {
            Song song = optionalSong.get();
            return ResponseEntity.status(200).body(song);
        } else {
            throw new SongNotFoundException();
        }*/

        /* Exception ResponseStatusException:

        try{
            return SongService.findById(id); // VA A SOLO BUSCAR LA CANCIÓN CON ID
        } catch(SongNotFoundException exception){ // SI NO LA ENCUENTRA, VA A ARROJAR UNA EXCEPCION
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Song not found", exception);
            // AQUÍ ES DONDE ESPECIFICA EL MÉTODO HTTP, en SERVICE SOLO SIVE PARA QUR FUNCIONE ESTA EXCEPCION
        }*/

        return SongService.findById(id);

    }

    /*@GetMapping
    public ResponseEntity<List<Song>> songs() {
        List<Song> songs = songRepository.findAll();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }*/

    @GetMapping
    public ResponseEntity<List<Song>> listByName(@RequestBody SongSearchByNameDto song){
        List<Song> personasFilteredByName = songRepository.findByName(song.getName());
        return new ResponseEntity<>(personasFilteredByName, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> song(@RequestBody Song song) {
        songRepository.save(song);
        return ResponseEntity.status(201).body("Created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSong(@PathVariable Long id, @RequestBody Song song) {
        Optional<Song> optionalSong = songRepository.findById(id);
        if(optionalSong.isPresent()) {
            Song existingSong = optionalSong.get();
            existingSong.setTitle(song.getTitle());
            existingSong.setArtist(song.getArtist());
            existingSong.setAlbum(song.getAlbum());
            existingSong.setReleaseDate(song.getReleaseDate());
            existingSong.setGenre(song.getGenre());
            existingSong.setDuration(song.getDuration());
            existingSong.setCoverImage(song.getCoverImage());
            existingSong.setSpotifyUrl(song.getSpotifyUrl());
            songRepository.save(existingSong);
            return ResponseEntity.status(200).body("Updated");
        } else {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {
        Optional<Song> optionalSong = songRepository.findById(id);
        if(optionalSong.isPresent()) {
            songRepository.deleteById(id);
            return ResponseEntity.status(200).body("Deleted");
        } else {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorMessage> conflict(SongNotFoundException exception){
        ErrorMessage error = new ErrorMessage(404,"Song not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // LA DESVENTAJA ES QUE SOLO FUNCIONA PARA UN CONTROLLER

}

