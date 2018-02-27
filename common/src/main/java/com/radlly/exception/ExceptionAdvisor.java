package com.radlly.exception;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.dao.DuplicateKeyException;



public class ExceptionAdvisor implements ThrowsAdvice {
	
	public void afterThrowing(RuntimeException rx) {

		if(rx instanceof DuplicateKeyException)
		{
//			IdWorker.updateSTARTNUM(node);
//			System.out.println("=======================================");
		}
		
    }

    /**
     * 对未知异常的处理.
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
        System.out.println("*************************************");
        System.out.println("Error happened in class: " + target.getClass().getName());
        System.out.println("Error happened in method: " + method.getName());

        for (int i = 0; i < args.length; i++) {
            System.out.println("arg[" + i + "]: " + args[i]);
        }

        System.out.println("Exception class: " + ex.getClass().getName());
        System.out.println("*************************************");
    }

//    /**
//     * 对NumberFormatException异常的处理
//     */
//    public void afterThrowing(Method method, Object[] args, Object target, NumberFormatException ex) throws Throwable {
//        System.out.println("*************************************");
//        System.out.println("Error happened in class: " + target.getClass().getName());
//        System.out.println("Error happened in method: " + method.getName());
//
//        for (int i = 0; i < args.length; i++) {
//            System.out.println("args[" + i + "]: " + args[i]);
//        }
//
//        System.out.println("Exception class: " + ex.getClass().getName());
//        System.out.println("*************************************");
//    }

}
