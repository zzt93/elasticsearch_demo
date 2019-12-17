package cn.superid.search.impl.mysql.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role_permission")
public class RolePermission {
    private Long id;
    private Long identityId;
    private Long affairId;

    private List<Short> permissionCategory;

    private Long roleId;
    private Long allianceId;
    private boolean useIdentity;

}