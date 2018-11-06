package es.jdl.sqlcrud.services;

/**
 *
 * @author jdlopez
 */
public class ConfigurationException extends Exception {

    private String configKey;

    public ConfigurationException(String key, String message, Throwable cause) {
        super(message, cause);
        this.configKey = key;
    }

    public String getConfigKey() {
        return configKey;
    }
}
