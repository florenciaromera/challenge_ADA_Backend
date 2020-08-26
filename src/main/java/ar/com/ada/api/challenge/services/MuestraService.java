package ar.com.ada.api.challenge.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.repos.MuestraRepo;

@Service
public class MuestraService {
    @Autowired
    MuestraRepo muestraRepo;

    public boolean crearNuevaMuestra(Muestra muestra) {
        if (muestraRepo.existsById(muestra.getMuestraId()))
            return false;
        else
            grabarMuestra(muestra);
        return true;

    }

    public void grabarMuestra(Muestra muestra) {
        if (muestra.getAltura() > 50 || muestra.getAltura() < -50) {
            muestra.getBoya().setColorLuz("AMARILLO");
            muestraRepo.save(muestra);
        }
        if (muestra.getAltura() < 100 || muestra.getAltura() < -100) {
            muestra.getBoya().setColorLuz("ROJO");
            muestraRepo.save(muestra);

        } else {
            muestra.getBoya().setColorLuz("VERDE");
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

    public Muestra buscarMuestraMinima(Integer id) {
        if (buscarPorId(id) != null) {
            return muestraRepo.muestraAlturaMinima();
        } else
            return null;
    }

    // public List<Muestra> buscarAnomalia(List<Muestra> muestras) {
    //     // TODO: Hacer query en el repo para buscar las diferencias entre las muestras
    // }

}