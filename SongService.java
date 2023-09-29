package com.example.demoo.song;

import com.example.demoo.song.exceptions.SongNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {
    @Autowired
    static SongRepository songRepository;

     public static Song findById(Long id){
         Song song = songRepository.findById(id).orElse(null);

        if(song == null){ // SI NO ENCUENTRO A LA PERSONA
            // throw new NullPointerException("Error");  <- ARROJA UN NULO
            throw new SongNotFoundException();
            // ME VA A ARROJAR UNA EXCEPCION EN SongNotFoundException.java
            // Y ESA EXCEPCION, VA A SER UNA EXCEPCION CUALQUIERA
            // YA QUE NO SE HA COLOCADO UN MÉTODO HTTP EN LA EXCEPCION
        }
        return song;
    }
}
