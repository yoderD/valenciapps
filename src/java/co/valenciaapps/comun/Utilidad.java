package co.valenciaapps.comun;

import co.valenciaapps.entidades.Cliente;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author esneider
 */
public class Utilidad {

    public static void redireccionar(String URL) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            ctx.getExternalContext().redirect(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long clienteSessionId() {
        HttpSession varailableHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        final Cliente cleinteSession = (Cliente) varailableHttp.getAttribute("clienteSession");
        return cleinteSession.getIdCliente();
    }

    public static boolean clienteSessionValidarNombre() {
        HttpSession varailableHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        final Cliente cleinteSession = (Cliente) varailableHttp.getAttribute("clienteSession");
        String nombre = cleinteSession.getNombreCliente();
        return (nombre.equals("BRAYAN") || nombre.equals("JULIETH"));
    }

    public static String clienteSessionNombre() {
        HttpSession varailableHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        final String cleinteSession = (String) varailableHttp.getAttribute("usuarioLogeado");
        return cleinteSession;
    }

    public static void crearSesion(Cliente cliente) {
        HttpSession variableHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        variableHttp.removeAttribute("clienteSession");
        variableHttp.removeAttribute("usuarioLogeado");
        variableHttp.setAttribute("clienteSession", cliente);
        variableHttp.setAttribute("usuarioLogeado", cliente.getNombreCliente());
    }

}
