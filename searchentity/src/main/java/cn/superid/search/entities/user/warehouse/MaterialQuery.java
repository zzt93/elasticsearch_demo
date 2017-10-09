package cn.superid.search.entities.user.warehouse;

import cn.superid.search.entities.PagedQuery;
import cn.superid.search.entities.TagVO;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class MaterialQuery extends PagedQuery {

  private List<TagVO> tags;
  private Integer materialType;
  private Integer publicType;
  private Long warehouseId;
  private Long affairId;

  private final Long allianceId;


  public MaterialQuery(String query, PageRequest pageRequest, Long allianceId) {
    this.allianceId = allianceId;
    setQuery(query);
    setPageRequest(pageRequest);
  }

  public MaterialQuery(List<TagVO> tags, PageRequest pageRequest, Long allianceId) {
    this.tags = tags;
    this.allianceId = allianceId;
    setPageRequest(pageRequest);
  }

  public Integer getMaterialType() {
    return materialType;
  }

  public void setMaterialType(Integer materialType) {
    this.materialType = materialType;
  }

  public Integer getPublicType() {
    return publicType;
  }

  public void setPublicType(Integer publicType) {
    this.publicType = publicType;
  }

  public Long getWarehouseId() {
    return warehouseId;
  }

  public void setWarehouseId(Long warehouseId) {
    this.warehouseId = warehouseId;
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public List<TagVO> getTags() {
    return tags;
  }
}
