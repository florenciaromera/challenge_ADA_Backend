package ar.com.ada.api.challenge.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.models.response.AnomaliaResponse;
import ar.com.ada.api.challenge.models.response.GenericResponse;
import ar.com.ada.api.challenge.models.response.MuestraMinimaResponse;
import ar.com.ada.api.challenge.models.response.MuestraResponse;
import ar.com.ada.api.challenge.models.request.MuestraReq;
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
    public ResponseEntity<GenericResponse> registrarMuestra(@RequestBody MuestraReq muestra) {
        
        GenericResponse gR = new GenericResponse();

        Muestra muestraRegistrada = muestraService.crearNuevaMuestra(muestra);

        if (muestraRegistrada != null) {
          gR.id = muestraRegistrada.getMuestraId();
          gR.message = muestraRegistrada.getBoya().getColorLuz();
            
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
    public ResponseEntity<GenericResponse> resetearColor(@PathVariable int id) {
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
        
        List<Muestra> listaMuestras = muestraService.buscarTodas();
 
List<MuestraResponse> listaMR = new ArrayList<>();
for (Muestra m : listaMuestras) {
    if(m.getBoya().getColorLuz().equalsIgnoreCase(color)){
        MuestraResponse mR = new MuestraResponse();
        mR.boyaId = m.getBoya().getBoyaId();
        mR.horario = m.getHorarioMuestra();
        mR.altura = m.getAltura();
        listaMR.add(mR);
    }
   
}
return ResponseEntity.ok(listaMR);
    }

 @GetMapping("/muestras/minima/{idBoya}")
 ResponseEntity<MuestraMinimaResponse> mostrarMuestaMinima(@PathVariable Integer idBoya){
     MuestraMinimaResponse mMR = new MuestraMinimaResponse();

     double muestraMinima = 0;
     Muestra m = new Muestra();
     for(Muestra muestra : boyaService.buscarPorId(idBoya).getMuestras()){
         if(muestra.getAltura() < muestraMinima){
             m = muestra;
             muestraMinima = muestra.getAltura();

             mMR.color = m.getBoya().getColorLuz();
             mMR.alturaNivelDelMarMinima = m.getAltura();
             mMR.horario = m.getHorarioMuestra();
         }

     }
     return ResponseEntity.ok(mMR);
}

@GetMapping("/api/muestras/anomalias/{idBoya}")
public ResponseEntity<?> alertaDeAnomalias(@PathVariable Integer idBoya) {
    List<Muestra> muestras = boyaService.buscarPorId(idBoya).getMuestras();
    AnomaliaResponse anomalia = new AnomaliaResponse();
    for (int i = 1; i < muestras.size(); i++) {
        Muestra m = new Muestra();
        m = muestras.get(i);
        long horaI = m.getHorarioMuestra().getTime();
        double alturaI = m.getAltura();
        for (int j = 0; j < muestras.size() - 1; j++) {
            Muestra m2 = new Muestra();
            m2 = muestras.get(j);
            long horaF = m2.getHorarioMuestra().getTime();
            double alturaF = m2.getAltura();

            long rTiempo = Math.abs(horaF) - Math.abs(horaI);
            double alt1 = Math.abs(alturaI);
            double alt2= Math.abs(alturaF);
            double altResta = Math.abs(alturaF)-Math.abs(alturaI);
            
            if ((rTiempo >= 600000) && (alt1>=200 && alt2>=200)) {

                anomalia.horarioInicioAnomalia = m.getHorarioMuestra();
                anomalia.horarioInicioFinAnomalia = m2.getHorarioMuestra();
                anomalia.alturaNivelDelMarActual = m2.getAltura();
                anomalia.tipoAlerta = "ALERTA DE KAIJU";
            }
            if(altResta >=500){
                anomalia.horarioInicioAnomalia = m.getHorarioMuestra();
                anomalia.horarioInicioFinAnomalia = m2.getHorarioMuestra();
                anomalia.alturaNivelDelMarActual = m2.getAltura();
                anomalia.tipoAlerta = "ALERTA DE IMPACTO";
            }
        }

    }
    return ResponseEntity.ok(anomalia);
}

}





    
