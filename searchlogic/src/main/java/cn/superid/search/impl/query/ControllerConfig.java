package cn.superid.search.impl.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zzt
 */
@ControllerAdvice
public class ControllerConfig {
  private static final Logger logger = LoggerFactory.getLogger(ControllerConfig.class);

  // exception handler class type to match
  @ExceptionHandler({TypeMismatchException.class, })
  public void handle(Exception e) { // the parameter class is very essential or spring will not match this handler
    logger.warn("Returning HTTP 400 Bad Request", e);
  }
}
