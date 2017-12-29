package cn.superid.search.entities.user.warehouse;

import cn.superid.search.entities.PagedQuery;
import cn.superid.search.entities.ScrollQuery;
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

  public MaterialQuery(List<TagVO> tags, PageRequest pageRequest, Long allianceId) {
    setPageRequest(pageRequest);
    this.tags = tags;
    this.allianceId = allianceId;
    scrollQuery = null;
  }

  public MaterialQuery(String query, List<TagVO> tags, PageRequest pageRequest, Long allianceId) {
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

  public List<TagVO> getTags() {
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
        ", " + super.toString() +
        '}';
  }
}
