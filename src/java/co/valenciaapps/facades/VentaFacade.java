/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.valenciaapps.facades;

import co.valenciaapps.dto.GeneralDto;
import co.valenciaapps.entidades.Cliente;
import co.valenciaapps.entidades.DetalleVenta;
import co.valenciaapps.entidades.Producto;
import co.valenciaapps.entidades.Venta;
import co.valenciaapps.services.ValenciaAppsException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author esneider
 */
@Stateless
public class VentaFacade extends AbstractFacade<Venta> {

    @PersistenceContext(unitName = "valenciappsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VentaFacade() {
        super(Venta.class);
    }

    public void generarRegistros(List<GeneralDto> compra) {
        for (GeneralDto dto : compra) {
            final Venta venta = registrarVenta(dto.getIdCliente(), dto.getIdProducto(), dto.getCantidad());
            registrarDetalleVenta(venta, dto.getSubTotal(), dto.getImpuesto(), dto.getTotal());
        }
    }

    public void registrarDetalleVenta(Venta venta, BigDecimal subTotal, BigDecimal impuetsto, BigDecimal total) {
        DetalleVenta dv = new DetalleVenta();
        dv.setIdVenta(venta);
        dv.setImpuesto(impuetsto);
        dv.setDescuento(new BigDecimal(0));
        dv.setSubtotal(subTotal);
        dv.setTotal(total);
        em.persist(dv);
    }

    public Venta registrarVenta(long cliente, long producto, int cantidad) {
        try {
            Venta venta = new Venta();
            venta.setIdCliente(new Cliente(cliente));
            venta.setIdProducto(new Producto(producto));
            create(venta);
            descontarProducto(producto, cantidad);
            return venta;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValenciaAppsException("Ocurrio un error registrando la venta.");
        }
    }

    public void descontarProducto(long producto, int cantidad) {
        Producto pro = buscarPorId(Producto.class, producto);
        pro.setCantidad(pro.getCantidad() - cantidad);
        em.merge(pro);
    }

}
