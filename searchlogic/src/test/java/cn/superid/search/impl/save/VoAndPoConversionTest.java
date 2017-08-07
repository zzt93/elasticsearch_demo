package cn.superid.search.impl.save;

import static org.junit.Assert.assertEquals;

import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import org.junit.Test;

/**
 * @author zzt
 */
public class VoAndPoConversionTest {

  @Test
  public void toPOClazz() throws Exception {
    // given & when
    Class aClass = VoAndPoConversion.toPOClazz(AffairVO.class);
    // then
    assertEquals(aClass, AffairPO.class);
  }

}