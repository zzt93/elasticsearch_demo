package cn.superid.search.entities.user.warehouse;

import cn.superid.search.entities.PagedQuery;
import cn.superid.search.entities.ScrollQuery;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class MaterialQuery extends PagedQuery {

  private String[] tags;
  private Integer materialType;
  private Integer publicType;
  private Long warehouseId;
  private Long affairId;

  private Long allianceId;

  private ScrollQuery scrollQuery;

  public MaterialQuery() {
  }

  /**
   * No scrollId, the first query
   */
  public MaterialQuery(String query, PageRequest pageRequest, Long allianceId) {
    setPageRequest(pageRequest);
    setQuery(query);
    this.allianceId = allianceId;
    scrollQuery = null;
  }

  public MaterialQuery(String scrollId) {
    scrollQuery = new ScrollQuery(scrollId);
  }

  public MaterialQuery(String[] tags, PageRequest pageRequest, Long allianceId) {
    setPageRequest(pageRequest);
    this.tags = tags;
    this.allianceId = allianceId;
    scrollQuery = null;
  }

  public MaterialQuery(String query, String[] tags, PageRequest pageRequest, Long allianceId) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.tags = tags;
    this.allianceId = allianceId;
    scrollQuery = null;
  }

  public ScrollQuery getScrollQuery() {
    return scrollQuery;
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

  public String[] getTags() {
    return tags;
  }

  @Override
  public String toString() {
    return "MaterialQuery{" +
        "tags=" + tags +
        ", materialType=" + materialType +
        ", publicType=" + publicType +
        ", warehouseId=" + warehouseId +
        ", affairId=" + affairId +
        ", allianceId=" + allianceId +
        ", scrollQuery=" + scrollQuery +
        ", pagedQuery=" + super.toString() +
        '}';
  }
}
