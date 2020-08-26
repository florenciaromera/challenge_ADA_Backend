package ar.com.ada.api.challenge.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="muestra")
public class Muestra {

    //Atributos
    @Id
    @Column(name="muestra_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer muestraId;
    @ManyToOne
    @JoinColumn(name ="boya_id", referencedColumnName ="boya_id")
    private Boya boya;
    @Column(name="horario_muestra")
    private Date horarioMuestra;
    @Column(name="matricula_embarcacion")
    private String matriculaEmbarcacion;
    private double longitud;
    private double latitud;
    private double altura;


    //Getters y Setters
 /**
     * @return the muestraId
     */
    public Integer getMuestraId() {
        return muestraId;
    }

    /**
     * @param muestraId the muestraId to set
     */
    public void setMuestraId(Integer muestraId) {
        this.muestraId = muestraId;
    }

    /**
     * @return the horaMuestra
     */
    public Date getHorarioMuestra() {
        return horarioMuestra;
    }

    /**
     * @param horarioMuestra the horaMuestra to set
     */
    public void setHorarioMuestra(Date horarioMuestra) {
        this.horarioMuestra = horarioMuestra;
    }

    /**
     * @return the matricula
     */
    public String getMatriculaEmbarcacion() {
        return matriculaEmbarcacion;
    }

    /**
     * @param matriculaEmbarcacion the matricula to set
     */
    public void setMatriculaEmbarcacion(String matriculaEmbarcacion) {
        this.matriculaEmbarcacion = matriculaEmbarcacion;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the msnm
     */
    public double getAltura() {
        return altura;
    }

    /**
     * @param altura the msnm to set
     */
    public void setAltura(double altura) {
        this.altura = altura;
    }

    /**
     * @return the boya
     */
    public Boya getBoya() {
        return boya;
    }

    /**
     * @param boya the boya to set
     */
    public void setBoya(Boya boya) {
        this.boya = boya;
        this.boya.getMuestras().add(this);
    }
    
}