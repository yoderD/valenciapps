package co.valenciaapps.services;

/**
 *
 * @author esneider
 */
public class ValenciaAppsException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public ValenciaAppsException(String message) {
        super(message);
    }

    public ValenciaAppsException(String message, Throwable cause) {
        super(message, cause);
    }

}
