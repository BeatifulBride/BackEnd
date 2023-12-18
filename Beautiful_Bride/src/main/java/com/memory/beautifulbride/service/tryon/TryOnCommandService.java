package com.memory.beautifulbride.service.tryon;

import com.memory.beautifulbride.repository.tryon.TryOnRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@Log4j2
public class TryOnCommandService {
    private final TryOnRepository tryOnRepository;

    public ResponseEntity<String> tryonsendtofastapi(){
        return null;
    }
    public TryOnCommandService(TryOnRepository tryOnRepository) {
        this.tryOnRepository = tryOnRepository;
    }
}
