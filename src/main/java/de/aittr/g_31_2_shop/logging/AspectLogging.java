package de.aittr.g_31_2_shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {

    private Logger logger = LoggerFactory.getLogger(AspectLogging.class);


    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getAllActiveProducts(..))")
    public void getProducts(){}
    @Before("getProducts()")
    public void beforeGetProducts() {
        logger.info("Вызван метод getAllActiveProducts.");
    }



    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.restoreById(int))")
    public void restoreProduct() {}
    @After("restoreProduct()")
    public void afterRestoreProduct(JoinPoint joinPoint) {
//        joinPoint.getSignature() для дз
        Object[] args = joinPoint.getArgs();
        logger.info("Вызван метод restoreById с идентификатором {}.", args[0]);
    }

    public void testAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder("Вызван метод с параметрами: ");
        for (Object arg : args) {
            builder.append(arg).append(", ");
        }
            // Вызван метод с параметрами: 1, Petya, 4.56, cat,
        builder.setLength(builder.length() -2);
        builder.append(".");
        logger.info(builder.toString());
    }

}
