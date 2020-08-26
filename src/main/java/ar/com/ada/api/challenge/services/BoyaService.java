package ar.com.ada.api.challenge.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.repos.BoyaRepository;
import ch.qos.logback.core.joran.conditional.ElseAction;

@Service
public class BoyaService {

    @Autowired
    BoyaRepository boyaRepo;

    public boolean crearNuevaBoya(Boya boya) {
        if (boyaRepo.existsById(boya.getBoyaId()))
            return false;
        else
            boyaRepo.save(boya);
        return true;

    }

    public Boya crearNuevaBoya(Double longitud, Double latitud) {

        Boya boya = new Boya();
        boya.setLatitudInstalacion(latitud);
        boya.setLongitudInstalacion(longitud);

        boolean boyaCreada = crearNuevaBoya(boya);
        if (boyaCreada)
            return boya;
        else
            return null;

    }

    public List<Boya> obtenerBoyas() {
        return boyaRepo.findAll();

    }

    public Boya buscarPorId(Integer id) {
        Optional<Boya> opBoya = boyaRepo.findById(id);
        
        if(opBoya.isPresent())
        return opBoya.get();
        else
        return null;

    }
    public Boya buscarPorColor(String color){
       return boyaRepo.findByColorLuz(color);
    }

    public Boya actualizarBoya(Boya boya){
        return boyaRepo.save(boya);
        
    }

}