package co.valenciaapps.controlador;

import co.valenciaapps.dto.GeneralDto;
import co.valenciaapps.comun.Utilidad;
import co.valenciaapps.facades.ProductoFacade;
import co.valenciaapps.services.ValenciaAppsException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author esneider
 */
@Named(value = "productoControlador")
@ViewScoped
public class ProductoControlador implements Serializable {

    @EJB
    private ProductoFacade productoFacade;

    private List<GeneralDto> listaProductos;
    private GeneralDto dtoProducto;

    /**
     * Creates a new instance of ProductoControlador
     */
    public ProductoControlador() {
    }

    @PostConstruct
    public void postConstruct() {
        dtoProducto = new GeneralDto();
        listaProductos = productoFacade.getProductos();
    }

    public void redireccionarURL(String URL) {
        Utilidad.redireccionar(URL);
    }

    public void crearProducto() {
        try {
            validarRegistro();
            productoFacade.registrar(dtoProducto);
            postConstruct();
        } catch (ValenciaAppsException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error."));
        }
    }

    public void validarRegistro() {
        if (dtoProducto.getCantidad() == 0) {
            throw new ValenciaAppsException("La cantidad debe ser mayor a 0.");
        }

        if (dtoProducto.getPrecio().intValue() == 0) {
            throw new ValenciaAppsException("El precio debe ser mayor a 0.");
        }
    }

    public List<GeneralDto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<GeneralDto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public GeneralDto getDtoProducto() {
        return dtoProducto;
    }

    public void setDtoProducto(GeneralDto dtoProducto) {
        this.dtoProducto = dtoProducto;
    }
}
