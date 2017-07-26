package cn.superid.search.impl.save;


/**
 * Created by zzt on 17/6/30.
 */
public class Suffix {

  private ThreadLocal<String> suffixes;

  public Suffix(String init) {
    suffixes = ThreadLocal.withInitial(() -> init);
  }

  public void setSuffix(String suffix) {
    suffixes.set(suffix);
  }

  @Override
  public String toString() {
    return suffixes.get();
  }
}

