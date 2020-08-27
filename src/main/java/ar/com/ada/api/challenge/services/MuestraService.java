package ar.com.ada.api.challenge.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.models.request.MuestraReq;
import ar.com.ada.api.challenge.repos.MuestraRepo;

@Service
public class MuestraService {
    @Autowired
    MuestraRepo muestraRepo;

    @Autowired
    BoyaService boyaService;

    public Muestra crearNuevaMuestra(MuestraReq muestraReq) {
        Boya boya = boyaService.buscarPorId(muestraReq.boyaId);
        if(boya  != null){
        
        Muestra muestra = new Muestra();
        muestra.setAltura(muestraReq.altura);
        muestra.setHorarioMuestra(muestraReq.horarioMuestra);
        muestra.setLatitud(muestraReq.latitud);
        muestra.setLongitud(muestraReq.longitud);
        muestra.setMatriculaEmbarcacion(muestraReq.matriculaEmbarcacion);
        muestra.setBoya(boya);
            grabarMuestra(muestra);
            return muestra;
        }else{
            return null;
        }

        

    }

    public void grabarMuestra(Muestra muestra) {
        double alt =muestra.getAltura();
        Boya boya = muestra.getBoya();
    
        if (alt> -50  && alt< 50 ) {
            boya.setColorLuz("VERDE");
            muestraRepo.save(muestra);
        }
        if (alt> -100 && alt<100) {
            boya.setColorLuz("AMARILLO");
            muestraRepo.save(muestra);

        } else {
            boya.setColorLuz("ROJO");
            muestraRepo.save(muestra);

        }

    }

    public Muestra buscarPorId(Integer id) {
        Optional<Muestra> opMuestra = muestraRepo.findById(id);

        if (opMuestra.isPresent())
            return opMuestra.get();
        else
            return null;

    }

    public List<Muestra> buscarTodas(){
        return muestraRepo.findAll();
    }



}