/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.valenciaapps.facades;

import co.valenciaapps.dto.GeneralDto;
import co.valenciaapps.entidades.Producto;
import co.valenciaapps.services.ValenciaAppsException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author esneider
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "valenciappsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    public void registrar(GeneralDto o) {
        try {
            Producto producto = new Producto();
            producto.setNombreProducto(o.getNombreRegistro().toUpperCase());
            producto.setCantidad(o.getCantidad());
            producto.setPrecio(o.getPrecio());
            producto.setMonto(o.getPrecio().multiply(new BigDecimal(producto.getCantidad())));
            create(producto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValenciaAppsException("Ocurrio un error registrando el producto.");
        }
    }

    public List getProductos() {
        try {
            List<Producto> productos = findAll();
            List<GeneralDto> dtos = new ArrayList<>();
            for (Producto cliente : productos) {
                dtos.add(construirClienteDto(cliente));
            }
            return (List) dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    public GeneralDto construirClienteDto(Producto producto) {
        GeneralDto cdto = new GeneralDto();
        cdto.setIdEntidad(producto.getIdProducto());
        cdto.setNombreRegistro(producto.getNombreProducto());
        cdto.setCantidad(producto.getCantidad());
        cdto.setPrecio(producto.getPrecio());
        cdto.setMonto(producto.getMonto());
        return cdto;
    }

    public GeneralDto construirProductoDto(Object[] fila) {
        GeneralDto cdto = new GeneralDto();
        cdto.setIdProducto(Long.valueOf(fila[0].toString()));
        cdto.setNombreRegistro((String) fila[1]);
        cdto.setPrecio(BigDecimal.valueOf(Double.parseDouble(fila[2].toString())));
        return cdto;
    }

    public List getProductosCantidad() {
        try {
            Query q = em.createNamedQuery("Producto.findCantidad", Producto.class);
            List<Object[]> list = q.getResultList();
            List<GeneralDto> dtos = new ArrayList<>();
            for (Object[] cliente : list) {
                dtos.add(construirProductoDto(cliente));
            }
            return (List) dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

}
