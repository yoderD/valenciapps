package co.valenciaapps.controlador;

import co.valenciaapps.comun.Utilidad;
import co.valenciaapps.entidades.Cliente;
import co.valenciaapps.facades.ClienteFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author esneider
 */
@Named(value = "loginController")
@ViewScoped
public class LoginController implements Serializable {

    @EJB
    private ClienteFacade clienteFacade;

    private String usuario;
    private String pass;
    private boolean logeado;

    @PostConstruct
    public void postConstruct() {
        logeado = false;
        usuario = "";
        pass = "";
    }

    public void login() {
        final Cliente cliente = clienteFacade.login(usuario, pass);
        if (cliente != null) {
            logeado = true;
            //usuarioLogeado = cliente.getNombreCliente();
            Utilidad.crearSesion(cliente);
            Utilidad.redireccionar("faces/nav.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no validas."));
        }
    }

    public void logout() {
        HttpSession variableHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        variableHttp.removeAttribute("clienteSession");
        variableHttp.invalidate();
        Utilidad.redireccionar("/valenciapps");
        logeado = false;
    }

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }
}
