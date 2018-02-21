package co.valenciaapps.controlador;

import co.valenciaapps.comun.Utilidad;
import co.valenciaapps.dto.GeneralDto;
import co.valenciaapps.facades.ClienteFacade;
import co.valenciaapps.services.ValenciaAppsException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author esneider
 */
@Named(value = "clienteControlador")
@ViewScoped
public class ClienteControlador implements Serializable {

    @EJB
    private ClienteFacade clienteFacade;

    private List<GeneralDto> listClientes;
    private String nombreCliente;
    private GeneralDto dto;

    @PostConstruct
    public void postConstruct() {
        nombreCliente = "";
        dto = new GeneralDto();
        listClientes = clienteFacade.getClientes();
    }

    public void clienteControlador(GeneralDto cliente) {
        dto = cliente;
        RequestContext.getCurrentInstance().execute("PF('dlgUsuario').show()");
    }

     public void redireccionarURL(String URL) {
        Utilidad.redireccionar(URL);
    }
    
    public void eliminar(GeneralDto cliente) {
        try {
            clienteFacade.eliminar(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cliente Eliminado Satisfactoriamente."));
            postConstruct();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearEditar() {
        try {
            if (dto.getIdEntidad() == null) {
                clienteFacade.registrar(dto);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "i", "Cliente Creado Satisfactoriamente."));
            } else {
                clienteFacade.actualizar(dto);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "i", "Cliente Actializado Satisfactoriamente."));
            }
            postConstruct();
        } catch (ValenciaAppsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "i", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "i", "Error"));
        }
    }

    public List<GeneralDto> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<GeneralDto> listClientes) {
        this.listClientes = listClientes;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public GeneralDto getDto() {
        return dto;
    }

    public void setDto(GeneralDto dto) {
        this.dto = dto;
    }

}
