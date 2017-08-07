package cn.superid.search.impl.save;

import static org.junit.Assert.assertEquals;

import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.impl.entities.VO2PO;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import org.junit.Test;

/**
 * @author zzt
 */
public class VO2POTest {

  @Test
  public void toPOClazz() throws Exception {
    // given & when
    Class aClass = VO2PO.toPOClazz(AffairVO.class);
    // then
    assertEquals(aClass, AffairPO.class);
  }

}