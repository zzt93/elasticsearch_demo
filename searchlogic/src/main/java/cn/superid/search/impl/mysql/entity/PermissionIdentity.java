package cn.superid.search.impl.mysql.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role_permission")
public class PermissionIdentity {
    private Long id;
    private String name;
    private Long allianceId;
    private List<Short> permissionCategory;
    private Boolean defaultIdentity;
    private Boolean superIdentity;
    private Boolean highIdentity;
    private Short permissionIdentityType;

}