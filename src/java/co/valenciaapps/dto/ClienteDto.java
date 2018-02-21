/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.valenciaapps.dto;

import java.io.Serializable;

/**
 *
 * @author esneider
 */
public class ClienteDto implements Serializable {

    private long idEntidad;
    private String nombreRegistro;

    public ClienteDto() {
    }

    public ClienteDto(long idEntidad, String nombreRegistro) {
        this.idEntidad = idEntidad;
        this.nombreRegistro = nombreRegistro;
    }

    public long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getNombreRegistro() {
        return nombreRegistro;
    }

    public void setNombreRegistro(String nombreRegistro) {
        this.nombreRegistro = nombreRegistro;
    }

}
