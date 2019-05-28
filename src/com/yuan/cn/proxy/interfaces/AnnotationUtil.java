package com.yuan.cn.proxy.interfaces;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public final class AnnotationUtil {

    static String forSql(Annotation[] annotations){
        String resql = null;
        for (Annotation annotation : annotations) {
            if(annotation.annotationType().equals(SQL.class))
            {
                SQL sql = (SQL) annotation;
                if(sql.value().equals(""))
                    resql = sql.sql();
                resql = sql.value();
            }
        }
        return resql;
    }
    static String forParam(Annotation[] annotations, String sql, Object[] args, int i){

        for (Annotation annotation : annotations) {
            if(annotation.annotationType().equals(Param.class)){

                String value = ((Param) annotation).value();
                    if(args[i].getClass().getSimpleName().equals("String")){
                        sql = sql.replaceAll("#"+value,"'"+args[i]+"'");
                    }else {
                        sql = sql.replaceAll("#"+value,args[i]+"");
                }
            }
        }
        return sql;
    }
    static void forMap(Field field, Method declaredMethod, Map<String, String> map){
        if(field != null){
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().equals(Column.class)) {
                    String name = ((Column) annotation).name();
                    if(!name.equals(""))
                        map.put(field.getName(), name);
                }
            }
        }
        if(declaredMethod != null){
            Annotation[] annotations = declaredMethod.getAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().equals(Column.class)) {
                    String name = ((Column) annotation).name();
                    if(!name.equals("") && declaredMethod.getName().startsWith("set"))
                    {
                        String methodName = declaredMethod.getName().substring(3);
                        methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
                        System.out.println(methodName);
                        map.put(methodName, name);
                    }
                }
            }
        }
    }
}
