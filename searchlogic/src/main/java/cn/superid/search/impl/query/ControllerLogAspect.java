package cn.superid.search.impl.query;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Aspect
@Component
public class ControllerLogAspect {
  private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

  @Before("execution(* QueryController.*(..))")
  public void logServiceAccess(JoinPoint joinPoint) {
    logger.info("{}", Arrays.toString(joinPoint.getArgs()));
  }

}
