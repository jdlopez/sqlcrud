package es.jdl.sqlcrud;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.ConfigurationException;
import es.jdl.sqlcrud.services.JsonConfigBuilder;
import es.jdl.sqlcrud.utils.ResourcesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestConfig {

    @Test
    public void testBuildConfig() throws ConfigurationException {
        JsonConfigBuilder confBuilder = new JsonConfigBuilder();
        CRUDConfiguration conf = confBuilder.readConfig(
                ResourcesUtil.getResourceAsStream("cruddatabase.json", null));
        Assertions.assertNotNull(conf);

    }
}
