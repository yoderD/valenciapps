package co.valenciaapps.controlador;

import co.valenciaapps.comun.Utilidad;
import co.valenciaapps.dto.GeneralDto;
import co.valenciaapps.entidades.Producto;
import co.valenciaapps.facades.ProductoFacade;
import co.valenciaapps.facades.VentaFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
@Named(value = "ventaControlador")
@ViewScoped
public class VentaControlador implements Serializable {

    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private VentaFacade ventaFacade;

    private List<GeneralDto> productosDtos;
    private List<GeneralDto> listaCompraCliente;
    private GeneralDto dtoCompra;
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal precio;
    private long clienteId;
    private String usuarioLogeado;
    private boolean entro;

    /**
     * Creates a new instance of VentaControlador
     */
    public VentaControlador() {
    }

    @PostConstruct
    public void postConstruct() {
        entro = false;
        clienteId = Utilidad.clienteSessionId();
        usuarioLogeado = Utilidad.clienteSessionNombre();
        dtoCompra = new GeneralDto();
        precio = BigDecimal.ZERO;
        cantidad = 0;
        productosDtos = productoFacade.getProductosCantidad();
        listaCompraCliente = new ArrayList<>();
    }

    public void generarPrecio() {
        final Producto find = productoFacade.find(dtoCompra.getIdProducto());
        dtoCompra = new GeneralDto();
        dtoCompra.setPrecio(find.getPrecio());
        dtoCompra.setIdProducto(find.getIdProducto());
        dtoCompra.setNombreRegistro(find.getNombreProducto());
        dtoCompra.setIdCliente(clienteId);
    }

    public void generarCostos() {
        BigDecimal costo = dtoCompra.getPrecio().multiply(new BigDecimal(dtoCompra.getCantidad()));
        dtoCompra.setTotal(costo);
        BigDecimal subTotal = dtoCompra.getTotal().divide(new BigDecimal(116), 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100));
        BigDecimal iva = subTotal.multiply(new BigDecimal(0.16)).setScale(2, RoundingMode.HALF_EVEN);
        dtoCompra.setImpuesto(iva);
        dtoCompra.setSubTotal(subTotal);
        dtoCompra.setTotal(costo);
    }

    public int validarProducto() {
        int pox = 0;
        for (GeneralDto dto : listaCompraCliente) {
            if (dto.getIdProducto().equals(dtoCompra.getIdProducto())) {
                dtoCompra.setCantidad(dtoCompra.getCantidad() + dto.getCantidad());
                generarCostos();
                entro = true;
                break;
            }
            pox++;
        }
        pox = entro ? pox : 0;
        return pox;
    }

    public void generarDesceuntoInventario() {
        try {
            ventaFacade.generarRegistros(listaCompraCliente);
            postConstruct();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registros creados satisfactoriamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }

    public void agregarCompra() {
        entro = false;
        int index = validarProducto();
        if (entro) {
            listaCompraCliente.remove(listaCompraCliente.get(index));
        }
        listaCompraCliente.add(dtoCompra);
        dtoCompra = new GeneralDto();
    }

    public void redireccionarURL(String URL) {
        if (!Utilidad.clienteSessionValidarNombre()) {
            Utilidad.redireccionar(URL);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Usted no tiene permiso para acceder a este modulo."));
        }
    }

    public void URL(String URL) {
        Utilidad.redireccionar(URL);
    }

    public List<GeneralDto> getProductosDtos() {
        return productosDtos;
    }

    public void setProductosDtos(List<GeneralDto> productosDtos) {
        this.productosDtos = productosDtos;
    }

    public List<GeneralDto> getListaCompraCliente() {
        return listaCompraCliente;
    }

    public void setListaCompraCliente(List<GeneralDto> listaCompraCliente) {
        this.listaCompraCliente = listaCompraCliente;
    }

    public GeneralDto getDtoCompra() {
        return dtoCompra;
    }

    public void setDtoCompra(GeneralDto dtoCompra) {
        this.dtoCompra = dtoCompra;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(String usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }
}
