package ar.com.ada.api.challenge.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.models.request.BoyaRequest;
import ar.com.ada.api.challenge.models.request.ModifBoyaReq;
import ar.com.ada.api.challenge.models.response.GenericResponse;
import ar.com.ada.api.challenge.services.BoyaService;

@RestController
public class BoyaController {

    @Autowired
    BoyaService boyaService;

    @PostMapping("/boyas")
    ResponseEntity<GenericResponse> crearBoya(@RequestBody Boya boya) {

        GenericResponse gR = new GenericResponse();

        Boolean boyaCreada = boyaService.crearNuevaBoya(boya);

        if (boyaCreada) {
            gR.isOk = true;
            gR.message = "Creaste la boya exitosamente.";
            return ResponseEntity.ok(gR);
        } else {

            gR.isOk = false;
            gR.message = "No se pudo crear la boya!";

            return ResponseEntity.badRequest().body(gR);
        }
    }

    @GetMapping("/boyas")
    ResponseEntity<List<Boya>> listarBoyas() {

        List<Boya> boyas = new ArrayList<>();

        boyas = boyaService.obtenerBoyas();

        return ResponseEntity.ok(boyas);

    }

    @GetMapping("/boyas/{id}")
    ResponseEntity<Boya> obtenerBoyaPorId(@PathVariable Integer id) {
        Boya boya = boyaService.buscarPorId(id);

        if (boya == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(boya);
    }

    @PutMapping("/boyas/{id}")
    ResponseEntity<GenericResponse> actualizarBoya(@PathVariable Integer id, @RequestBody ModifBoyaReq modifBoya) {
        GenericResponse gR = new GenericResponse();
        Boya boya = boyaService.buscarPorId(id);
        if (boya == null) {
            return ResponseEntity.notFound().build();
        } else {
            boya.setColorLuz(modifBoya.color);
            Boya boyaActualizada = boyaService.actualizarBoya(boya);
            gR.isOk = true;
            gR.id = boyaActualizada.getBoyaId();
            gR.message = "El color de la Luz de la Boya ha sido actualizado";
            return ResponseEntity.ok(gR);

        }
    }

}
