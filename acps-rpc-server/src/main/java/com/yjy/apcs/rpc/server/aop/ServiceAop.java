package com.yjy.apcs.rpc.server.aop;

import com.yjy.common.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;

/**
 * Created by Administrator on 2016/7/6.
 */
public class ServiceAop {



    ThreadLocal<Long> startTimeLocal=new ThreadLocal<>();

    private static Logger log = LoggerFactory.getLogger(ServiceAop.class);




    // 方法执行前
    public void takeSeats(JoinPoint joinPoint){

        long startTime=Long.valueOf(System.currentTimeMillis());
        startTimeLocal.set(startTime);
        String methodName = joinPoint.getSignature().getName();
        Class classez=joinPoint.getTarget().getClass();
        log.info(classez.toString().substring(28) + ":" + methodName + "() 方法开始前写监控日志---------------------- ");

        Object[] objects= joinPoint.getArgs();

        log.info(methodName+"() "+"收到调用参数");

        for (int i = 0; i <objects.length ; i++) {
            if(objects[i]!=null){
                Class classsa=objects[i].getClass();
                if(classsa.isArray()){
                    log.info(classsa.getComponentType()+"数组");
                    for(int j=0;j< Array.getLength(objects[i]);j++){
                        log.info(Array.get(objects[i],j).toString());
                    };
                }else{
                    log.info(JsonUtils.toJson(objects[i]));
                }
            }else {
                log.info("null");
            }
        }

    }




    //    方法执行后
    public void applaud(JoinPoint joinPoint,Object returning){

      //  String methodName=  joinPoint.getSignature().getName();

      //  Class classez=joinPoint.getTarget().getClass();

      //  log.info(classez.toString().substring(28) + ":" + methodName + "()  方法结束了 监控性能\r\n 返回值：" + returning);

        long endTime= Long.valueOf(System.currentTimeMillis());

        log.info("yw业务方法执行时间:"+(endTime-startTimeLocal.get()));

    }


    //异常监控
    public void afterThrowing(JoinPoint joinPoint,Exception e) throws Throwable {

        Object[] objects= joinPoint.getArgs();

        String methodName=  joinPoint.getSignature().getName();
        Class classez=joinPoint.getTarget().getClass();
        log.error(classez.toString().substring(28) + ":" + methodName + "() 方法出异常了:" + e.getMessage() + "\n"+objects[0], e);
    }
}
