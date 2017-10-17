package cn.superid.search.impl.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author zzt
 */
@EnableWebMvc
@ControllerAdvice
public class ControllerConfig {
  private static final Logger logger = LoggerFactory.getLogger(ControllerConfig.class);

  // exception handler class type to match
  @ExceptionHandler({TypeMismatchException.class, HttpMessageNotReadableException.class,
      HttpMessageConversionException.class})
  public void handle(Exception e) { // the parameter class is very essential or spring will not match this handler
    logger.warn("Returning HTTP 400 Bad Request", e);
  }
}
