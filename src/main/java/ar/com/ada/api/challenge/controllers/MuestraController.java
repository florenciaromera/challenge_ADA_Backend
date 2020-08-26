package ar.com.ada.api.challenge.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.models.response.AnomaliaResponse;
import ar.com.ada.api.challenge.models.response.GenericResponse;
import ar.com.ada.api.challenge.models.response.MuestraMinimaResponse;
import ar.com.ada.api.challenge.models.response.MuestraResponse;
import ar.com.ada.api.challenge.services.BoyaService;
import ar.com.ada.api.challenge.services.MuestraService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class MuestraController {

    @Autowired
    MuestraService muestraService;

    @Autowired
    BoyaService boyaService;
    
    
    @PostMapping(value="/muestras")
    public ResponseEntity<GenericResponse> registrarMuestra(@RequestBody Muestra muestra) {
        
        GenericResponse gR = new GenericResponse();
        boolean muestraRegistrada = muestraService.crearNuevaMuestra(muestra);

        if (muestraRegistrada) {
          gR.id = muestra.getMuestraId();
          gR.message = muestra.getBoya().getColorLuz();
            
            return ResponseEntity.ok(gR);
        } else {

            gR.isOk = false;
            gR.message = "No se pudo crear la boya!";

            return ResponseEntity.badRequest().body(gR);
        }
        
    }

    @GetMapping("/muestras/boyas/{idBoya}")
    public ResponseEntity<List<Muestra>> obtenerMuestrasPorIdBoya(@PathVariable Integer idBoya) {
        List<Muestra> listaMuestras = boyaService.buscarPorId(idBoya).getMuestras();

        return ResponseEntity.ok(listaMuestras);

    }

    @DeleteMapping("/muestras/{id}")
    public ResponseEntity<GenericResponse> bajaEmpleado(@PathVariable int id) {
        Muestra muestra = muestraService.buscarPorId(id);
        if (muestra == null) {
            return ResponseEntity.notFound().build();
        }
       muestra.getBoya().setColorLuz("AZUL");

        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = muestra.getMuestraId();
        gR.message = "La muestra fue eliminada exitosamente";
        return ResponseEntity.ok(gR);
    }

    @GetMapping("/muestras/colores/{color}")
    public ResponseEntity<List<MuestraResponse>> obtenerMuestrasPorColor(@PathVariable String color) {
        List<Muestra> listaMuestras = boyaService.buscarPorColor(color).getMuestras();
 
List<MuestraResponse> listaMR = new ArrayList<MuestraResponse>();
for (Muestra m : listaMuestras) {
    MuestraResponse mR = new MuestraResponse();
    mR.boyaId = m.getBoya().getBoyaId();
    mR.horario = m.getHorarioMuestra();
    mR.altura = m.getAltura();
    listaMR.add(mR);
}
return ResponseEntity.ok(listaMR);
    }

@GetMapping("/muestras/minima/{idBoya}")
ResponseEntity<MuestraMinimaResponse> mostrarMuestaMinima(@PathVariable Integer idBoya){
    MuestraMinimaResponse mMR = new MuestraMinimaResponse();
    Muestra muestraMinima = muestraService.buscarMuestraMinima(idBoya);

    if(muestraMinima != null){
        mMR.color = muestraMinima.getBoya().getColorLuz();
        mMR.alturaNivelDelMarMinima= muestraMinima.getAltura();
        mMR.horario = muestraMinima.getHorarioMuestra();
        return ResponseEntity.ok(mMR);
    }else 
    return ResponseEntity.badRequest().build();


}

// @GetMapping("/muestras/anomalias/{idBoya}")
// ResponseEntity<AnomaliaResponse> alertaDeAnomalia(@PathVariable Integer idBoya){
//     AnomaliaResponse aRes = new AnomaliaResponse();
//     List<Muestra> muestras = boyaService.buscarPorId(idBoya).getMuestras();
//     muestras = muestraService.buscarAnomalia();
// }


}









    
