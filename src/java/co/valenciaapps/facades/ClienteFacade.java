/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.valenciaapps.facades;

import co.valenciaapps.dto.GeneralDto;
import co.valenciaapps.entidades.Cliente;
import co.valenciaapps.services.ValenciaAppsException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author esneider
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "valenciappsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    public void registrar(GeneralDto o) {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombreCliente(o.getNombreRegistro().toUpperCase());
            cliente.setContrasenia("123");
            create(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValenciaAppsException("Ocurrio un error registrado cliente.");
        }
    }

    public List getClientes() {
        try {
            List<Cliente> clientes = findAll();
            List<GeneralDto> dtos = new ArrayList<>();
            for (Cliente cliente : clientes) {
                dtos.add(construirClienteDto(cliente));
            }
            return (List) dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    public GeneralDto construirClienteDto(Cliente cliente) {
        GeneralDto cdto = new GeneralDto();
        cdto.setIdEntidad(cliente.getIdCliente());
        cdto.setNombreRegistro(cliente.getNombreCliente());
        return cdto;
    }

    public void actualizar(GeneralDto o) {
        Cliente cliente = find(o.getIdEntidad());
        cliente.setNombreCliente(o.getNombreRegistro().toUpperCase());
        em.merge(cliente);
    }

    public void eliminar(GeneralDto o) {
        Cliente cliente = find(o.getIdEntidad());
        remove(cliente);
    }

    public Cliente login(String usaurio, String pass) {
        final TypedQuery<Cliente> q = em.createNamedQuery("Cliente.findUsuario", Cliente.class);
        q.setParameter("nombre", usaurio);
        q.setParameter("pass", pass);
        return q.getResultList().size() > 0 ? q.getResultList().get(0) : null;
    }
}
