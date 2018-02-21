package co.valenciaapps.controlador;

import co.valenciaapps.entidades.Cliente;
import co.valenciaapps.facades.ClienteFacade;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

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

    public void login(ActionEvent actionEvent) {
        final Cliente cliente = clienteFacade.login(usuario, pass);
        FacesMessage fm = null;
        if (cliente != null) {
            logeado = true;
            HttpSession variableHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            variableHttp.setAttribute("cleinteSession", cliente);
        } else {
            fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no validas.");
        }
    }

    public void logot() {
        HttpSession variableHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        variableHttp.setAttribute("cleinteSession", null);
        variableHttp.invalidate();
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
