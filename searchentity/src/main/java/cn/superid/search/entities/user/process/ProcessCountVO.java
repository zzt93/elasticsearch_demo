package cn.superid.search.entities.user.process;

import java.util.List;
import java.util.Map;

/**
 * Process statistics VO
 */
public class ProcessCountVO {
  Map<Long, Long> countMap;
  List<Long> ongingList;

  public ProcessCountVO() {
  }

  public ProcessCountVO(Map<Long, Long> countMap, List<Long> ongingList) {
    this.countMap = countMap;
    this.ongingList = ongingList;
  }

  public Map<Long, Long> getCountMap() {
    return countMap;
  }

  public void setCountMap(Map<Long, Long> countMap) {
    this.countMap = countMap;
  }

  public List<Long> getOngingList() {
    return ongingList;
  }

  public void setOngingList(List<Long> ongingList) {
    this.ongingList = ongingList;
  }

  @Override
  public String toString() {
    return "ProcessCountVO{" +
        "countMap=" + countMap +
        ", ongingList=" + ongingList +
        '}';
  }
}
