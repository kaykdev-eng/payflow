package com.payflow.payflow.pix;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pix-keys")
public class PixKeyController {
    private final PixKeyService service;

    public PixKeyController(PixKeyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PixKeyRegisterResponse> create(@RequestBody @Valid PixKeyRegisterRequest request) {
        PixKey pixKey = service.register(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pixKey.getId())
                .toUri();

        return ResponseEntity.created(uri).body(PixKeyRegisterResponse.from(pixKey));
    }

}
