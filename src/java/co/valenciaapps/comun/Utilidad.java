package co.valenciaapps.comun;

import javax.faces.context.FacesContext;

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
    
}
