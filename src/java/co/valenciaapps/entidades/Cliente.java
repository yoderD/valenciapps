package co.valenciaapps.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author esneider
 */
@Entity
@Table(name = "clientes")
@NamedQueries({
    @NamedQuery(name = "Cliente.findUsuario", query = "SELECT c FROM Cliente c WHERE c.nombreCliente = :nombre AND c.contrasenia = :pass ")
})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private long idCliente;

    @Basic(optional = false)
    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @Basic(optional = false)
    @Column(name = "contrasenia")
    private String contrasenia;

    public Cliente() {
    }

    public Cliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
