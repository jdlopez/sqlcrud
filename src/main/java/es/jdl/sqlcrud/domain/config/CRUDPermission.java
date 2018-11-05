package es.jdl.sqlcrud.domain.config;

import java.util.List;

/**
 *
 * @author jdlopez
 */
public class CRUDPermission {
    private List<String> createRoles;
    private List<String> readRoles;
    private List<String> updateRoles;
    private List<String> deleteRoles;

    public List<String> getCreateRoles() {
        return createRoles;
    }

    public void setCreateRoles(List<String> createRoles) {
        this.createRoles = createRoles;
    }

    public List<String> getReadRoles() {
        return readRoles;
    }

    public void setReadRoles(List<String> readRoles) {
        this.readRoles = readRoles;
    }

    public List<String> getUpdateRoles() {
        return updateRoles;
    }

    public void setUpdateRoles(List<String> updateRoles) {
        this.updateRoles = updateRoles;
    }

    public List<String> getDeleteRoles() {
        return deleteRoles;
    }

    public void setDeleteRoles(List<String> deleteRoles) {
        this.deleteRoles = deleteRoles;
    }
}
