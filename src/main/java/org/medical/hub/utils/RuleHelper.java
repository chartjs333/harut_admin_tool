package org.medical.hub.utils;

public class RuleHelper {

    private RuleHelper(){}

    public static boolean isMethodExists(Class clazz, String methodName){
        try{
            clazz.getMethod(methodName, String.class);
            return  true;
        }catch (NoSuchMethodException e){
            return false;
        }
    }
}
