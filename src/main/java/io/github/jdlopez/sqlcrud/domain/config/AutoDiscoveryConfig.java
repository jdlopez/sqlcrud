package io.github.jdlopez.sqlcrud.domain.config;

import lombok.Data;

@Data
public class AutoDiscoveryConfig {

    private Boolean tableList;
    private String filterRegEx;

}
