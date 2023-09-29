package com.example.demoo.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @GetMapping
    public ResponseEntity<List<Playlist>> playlists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable Long id){
        Optional<Playlist> otherPlaylist = playlistRepository.findById(id);
        if(otherPlaylist.isPresent()){
            Playlist playlist = otherPlaylist.get();
            return ResponseEntity.status(200).body(playlist);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> playlist(@RequestBody Playlist playlist) {
        playlistRepository.save(playlist);
        return ResponseEntity.status(201).body("Creado con éxito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlaylist(@PathVariable Long id, @RequestBody Playlist pl) {
        Optional<Playlist> otherPlaylist = playlistRepository.findById(id);
        if(otherPlaylist.isPresent()) {
            Playlist playlistPutting = otherPlaylist.get();
            playlistPutting.setTitle(pl.getTitle());
            playlistPutting.setSongs(pl.getSongs( ));
            playlistPutting.setCoverImage(pl.getCoverImage());

            playlistRepository.save(playlistPutting);
            return ResponseEntity.status(200).body("Actualizado con éxito");
        } else {
            return ResponseEntity.status(404).body("No encontrado");
        }

        /*
        1° VA A RETORNAR UN STRING, RECIBE COMO PARAMETRO EL PATH VARIABLE "/{id}"
            RequestBody es lo que se manda por postman, osea
         */
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        Optional<Playlist> otherPlaylist = playlistRepository.findById(id);
        if(otherPlaylist.isPresent()) {
            playlistRepository.deleteById(id);
            return ResponseEntity.status(200).body("Borrado con éxito");
        } else {
            return ResponseEntity.status(404).body("No encontrado");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchPlaylist(@PathVariable Long id, @RequestBody Playlist pl){
        Optional<Playlist> otherPlaylist = playlistRepository.findById(id);
        if(otherPlaylist.isPresent()){
            Playlist playlist_patching = otherPlaylist.get();
            if(pl.getTitle() != null){
                playlist_patching.setTitle(pl.getTitle());
            }
            if(pl.getSongs() != null){
                playlist_patching.setSongs(pl.getSongs());
            }
            if(pl.getCoverImage() != null){
                playlist_patching.setCoverImage(pl.getCoverImage());
            }
            playlistRepository.save(playlist_patching);
            return ResponseEntity.status(200).body("Actualizacion exitosa");
        } else {
            return ResponseEntity.status(404).body("No econtrado");
        }
    }

}
