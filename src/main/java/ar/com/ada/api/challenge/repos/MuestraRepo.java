package ar.com.ada.api.challenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.challenge.entities.Muestra;
@Repository
public interface MuestraRepo extends JpaRepository<Muestra,Integer> {
    
    //  @Query("Select m From muestra where m.altura = MIN(altura)")
    // Muestra muestraAlturaMinima();
}