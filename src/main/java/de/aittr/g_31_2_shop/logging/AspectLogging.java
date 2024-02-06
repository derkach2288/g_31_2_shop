package de.aittr.g_31_2_shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {

    private Logger logger = LoggerFactory.getLogger(AspectLogging.class);


    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getAllActiveProducts(..))")
    public void getProducts() {
    }

    @Before("getProducts()")
    public void beforeGetProducts() {
        logger.info("Вызван метод getAllActiveProducts.");
    }


    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.restoreById(int))")
    public void restoreProduct() {
    }

    @After("restoreProduct()")
    public void afterRestoreProduct(JoinPoint joinPoint) {
//        joinPoint.getSignature() для дз
        Object[] args = joinPoint.getArgs();
        logger.info("Вызван метод restoreById с идентификатором {}.", args[0]);
    }

    //    public void testAdvice(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        StringBuilder builder = new StringBuilder("Вызван метод с параметрами: ");
//        for (Object arg : args) {
//            builder.append(arg).append(", ");
//        }
//        // Вызван метод с параметрами: 1, Petya, 4.56, cat,
//        builder.setLength(builder.length() - 2);
//        builder.append(".");
//        logger.info(builder.toString());
//    }


    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getActiveProductById(int))")
    public void getActiveProductById() {
    }

    @AfterReturning(
            pointcut = "getActiveProductById()",
            returning = "result"
    )
    public void afterReturningProduct(JoinPoint joinPoint, Object result) {
        Object id = joinPoint.getArgs()[0];
        logger.info("Метод getActiveProductById вызыван с параметром {} и успешно вернул рузультат: {}", id, result);
    }

    @AfterThrowing(
            pointcut = "getActiveProductById()",
            throwing = "e"
    )
    public void trowingExceptionWhileReturningProduct(JoinPoint joinPoint, Exception e) {
        Object id = joinPoint.getArgs()[0];
        logger.warn("Метод getActiveProductById вызыван с параметром {} и выбросил ошибку: {}", id, e.getMessage());
    }


    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getActiveProductCount(..))")
    public void getActiveProductCount() {
    }

    @Around("getActiveProductCount()")
    public Object aroundGettingProductCount(ProceedingJoinPoint joinPoint) {
        // код, выполняющийся до исходного меода
        logger.info("Вызван метод getActiveProductCount.");
        // засечем время
        long start = System.currentTimeMillis();

        Object result; // чтоб можно было пользоваться переменной за границами try/catch
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        // код, выполняющийся после исходного меода
        // вычисляем сколько времени прошло с момента старта
        long time = System.currentTimeMillis() - start;
        logger.info("Метод getActiveProductCount отработал за {} миллисекунд с результатом {}", time, result);

//        logger.info("Подменяем действительный результат своим значением - 500");
//        return 500;
        return result;
    }

//        2. При помощи АОП сделать логирование всех методов сервиса продуктов.
//        Для задания Pointcut использовать JpaProductService.*(..). В лог должно выводиться:
//        А. Какой метод и с какими параметрами вызван.
//        Б. Какой метод завершил работу.

//    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.*(..))")
//    public void logMethodExecution() {
//    }
//
//    @Before("logMethodExecution()")
//    public void logBeforeMethodExecution(JoinPoint joinPoint) {
//        String nameMethod = joinPoint.getSignature().getName();
//        Object[] args = joinPoint.getArgs();
//
//        if (args.length > 0) {
//            logger.info("Вызван метод {} с параметрами: {}", nameMethod, args);
//        } else {
//            logger.info("Вызван метод {}", nameMethod);
//        }
//    }
//
//    @After("logMethodExecution()")
//    public void logAfterMethodExecution(JoinPoint joinPoint) {
//        String nameMethod = joinPoint.getSignature().getName();
//        logger.info("Метод {} завершил работу", nameMethod);
//    }


//        3. При помощи АОП сделать логирование всех сервисов. В лог должно выводиться:
//        А. Какой метод какого класса и с какими параметрами вызван.
//        Б. Какой метод какого класса завершил работу.
//        В. Какой метод какого класса успешно вернул результат.
//        Г. Какой метод какого класса вызвал ошибку.

    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa..*.*(..))")
    public void jpaServiceMethods() {}


    @Before("jpaServiceMethods()")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        String nameMethod = joinPoint.getSignature().getName();
        String nameClass = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            logger.info("** Вызван метод {} класса {} с параметрами {}", nameMethod, nameClass, args);
        } else {
            logger.info("** Вызван метод {} класса {}", nameMethod, nameClass);

        }
    }

    @After("jpaServiceMethods()")
    public void logAfterMethodCompletion(JoinPoint joinPoint) {
        String nameMethod = joinPoint.getSignature().getName();
        String nameClass = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            logger.info("** Завершил работу метод {} класса {} с параметрами {}", nameMethod, nameClass, args);
        } else {
            logger.info("** Завершил работу метод {} класса {}", nameMethod, nameClass);
        }
    }

    @AfterReturning(pointcut = "jpaServiceMethods()", returning = "result")
    public void logAfterMethodReturn(JoinPoint joinPoint, Object result) {
        String nameMethod = joinPoint.getSignature().getName();
        String nameClass = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            logger.info("** Метод {} класса {} с параметрами {} вернул результат {}", nameMethod, nameClass, args, result);
        } else {
            logger.info("** Метод {} класса {} вернул результат {}", nameMethod, nameClass, result);
        }
    }

    @AfterThrowing(pointcut = "jpaServiceMethods()", throwing = "e")
    public void logAfterMethodThrowing(JoinPoint joinPoint, Exception e) {
        String nameMethod = joinPoint.getSignature().getName();
        String nameClass = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            logger.info("** Метод {} класса {} с параметрами {} вернул ошибку {}", nameMethod, nameClass, args, e.getMessage());
        } else {
            logger.info("** Метод {} класса {} вернул ошибку {}", nameMethod, nameClass, e.getMessage());
        }
    }


//    @AfterThrowing(
//            pointcut = "getActiveProductById()",
//            throwing = "e"
//    )
//    public void trowingExceptionWhileReturningProduct(JoinPoint joinPoint, Exception e) {
//        Object id = joinPoint.getArgs()[0];
//        logger.warn("Метод getActiveProductById вызыван с параметром {} и выбросил ошибку: {}", id, e.getMessage());
//    }


}
