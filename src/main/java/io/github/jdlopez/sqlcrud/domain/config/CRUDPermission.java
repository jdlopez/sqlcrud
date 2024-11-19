package io.github.jdlopez.sqlcrud.domain.config;

import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class CRUDPermission {
    private List<String> createRoles;
    private List<String> readRoles;
    private List<String> updateRoles;
    private List<String> deleteRoles;

}
