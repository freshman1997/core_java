package com.yuan.cn.proxy.interfaces;

import java.lang.reflect.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public final class SQLQuery {
    private static Map<String, String> map = new HashMap<>();

    public static <T> T query(Class<T> type,Method method, String sql, Object[] args) {
        Field[] fields = type.getDeclaredFields();
        List<String> list = new ArrayList<>();
        getSetters(fields, list, "set");
        assert sql != null;
        if (sql.startsWith("select")) {
            try {
                // 这个list是获取属性的setter方法名称的集合
                return parseSelect(list, type, sql, args, method);
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            return parseInsertAndDeleteAndUpdate(args, sql, method);
        }
        return null;
    }

    private static void getSetters(Field[] fields, List<String> list, String set) {
        for (Field field : fields) {
            String s = field.getName().substring(0, 1).toUpperCase();
            String result = set + s + field.getName().substring(1);
            list.add(result);
        }
    }

    private static <T> T parseInsertAndDeleteAndUpdate(Object[] args, String sql, Method method) {
        Field[] fields = args[0].getClass().getDeclaredFields();
        List<String> getMethods = new ArrayList<>();
        getSetters(fields, getMethods, "set");
        Method[] declaredMethods = args[0].getClass().getDeclaredMethods();
        if (args[0] == null)
            throw new RuntimeException("传入的目标对象为空");
        if (sql.startsWith("insert")) {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = JDBCConnectionUtil.getConnection().prepareStatement(sql);
            } catch (SQLException ignored) {
            }
            int i = 1;
            for (Method declaredMethod : declaredMethods) {
                // 如果是getter方法
                if (declaredMethod.getName().startsWith("get")) {
                    try {
                        String simpleName = declaredMethod.invoke(args[0]).getClass().getSimpleName();
                        if (simpleName.equals("String")) {
                            preparedStatement.setString(i, declaredMethod.invoke(args[0]) + "");
                            i++;
                        }
                        if (simpleName.equals("Integer")) {
                            preparedStatement.setInt(i, (int) declaredMethod.invoke(args[0]));
                            i++;
                        }
                        if (simpleName.equals("Double")) {
                            preparedStatement.setDouble(i, (double) declaredMethod.invoke(args[0]));
                            i++;
                        }
                    } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                System.out.println(sql);
                Integer integer = preparedStatement.executeUpdate();
                return (T) integer;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        if (sql.startsWith("update")) {
            PreparedStatement preparedStatement = null;
            System.out.println("preparing sql ===> \t"+sql);
            if (sql.contains("#")) {
                System.out.println("\t\t\t\tparameters ===>");
                for (Method declaredMethod : declaredMethods) {
                    if (declaredMethod.getName().startsWith("get")) {
                        for (int i = 0; i < sql.length(); i++) {
                            if (sql.charAt(i) == '#') {
                                StringBuilder target = new StringBuilder();
                                int c = i;
                                while (sql.charAt(c) != ',' && sql.charAt(c) != ' ' && c < sql.length()-1) {
                                    target.append(sql.charAt(c));
                                    c ++;
                                    if(c == sql.length()-1)
                                        target.append(sql.charAt(c));
                                }
                                try {
                                    System.out.print("\t\t\t\t\t\t\t\t"+target.toString().substring(1)+" = ");
                                    String s = "get" + target.toString().substring(1, 2).toUpperCase() + target.toString().substring(2);
                                    for (Method method1 : declaredMethods) {
                                        if (method1.getName().equals(s)) {
                                            if(method1.invoke(args[0]).getClass().getSimpleName().equals("String")){
                                                sql = sql.replace(target.toString(), "'"+method1.invoke(args[0]) + "'");
                                                System.out.print(method1.invoke(args[0]));
                                            }else{
                                                sql = sql.replace(target.toString(), method1.invoke(args[0]) + "");
                                                System.out.print(method1.invoke(args[0]));
                                            }
                                        }
                                    }
                                    System.out.println();
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }
            }
            try {
                preparedStatement = JDBCConnectionUtil.getConnection().prepareStatement(sql);
                Integer t = preparedStatement.executeUpdate();
                return (T)t;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        if(sql.startsWith("delete")){
            System.out.println("preparing sql for query ===> \t"+sql);
            PreparedStatement preparedStatement = null;
            sql = getString(args, sql, method);
            try {
                System.out.println("\t\t\t\t\t\t\t\texecuting query ===>\t "+sql);
                preparedStatement = JDBCConnectionUtil.getConnection().prepareStatement(sql);
                Integer t = preparedStatement.executeUpdate();
                return (T)t;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    private static String getString(Object[] args, String sql, Method method) {
        int i = 0;
        for (Parameter parameter : method.getParameters()) {
            if (parameter.isAnnotationPresent(Param.class)) {
                sql = AnnotationUtil.forParam(parameter.getAnnotations(), sql, args, i);
                i++;
            }
        }
        return sql;
    }

    private static <T> T parseSelect(List<String> list, Class<T> type, String sql, Object[] args, Method method1) throws SQLException, IllegalAccessException {
        if(sql.startsWith("select")) {
            if(args == null){
                Type genericReturnType = method1.getGenericReturnType();
                System.out.println("preparing sql ===> \t"+sql);
                Collection<Object> list1 = null;
                if(type.getSimpleName().equals("List") ||type.getSimpleName().equals("Set") )
                {
                    switch (type.getSimpleName()){
                        case "List":
                            list1 = new ArrayList<>();
                            break;
                        case "Set":
                            list1 = new HashSet<>();
                            break;
                    }

                    Class clazz = null;
                    Object o = null;
                    if(genericReturnType instanceof ParameterizedType){
                        ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
                        Type[] typeArguments = parameterizedType.getActualTypeArguments();
                        clazz = (Class) typeArguments[0];
                    }
                    try {
                        assert clazz != null;
                        o = clazz.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }

                    getForBatch(list, o, sql, list1);
                    return (T) list1;
                }
            }
            else if (args.length == 1) {
                sql = getString(args, sql);
            } else {
                sql = getString(args, sql, method1);
            }
        }
        System.out.println("formatted sql completed. ======>  " + sql);
        T t = getT(list, type, sql);
        return t;
    }

    private static <T> T getT(Collection<String> list, Class<T> type, String sql) throws SQLException {

        Statement statement;
        ResultSet resultSet = null;
        T t = null;
        try {
            statement = JDBCConnectionUtil.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            t = type.newInstance();
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        if (t == null) {
            throw new RuntimeException("创建实体类对象失败！");
        }
        FillMap(t);
        if (resultSet.next()) {
            forProperties(list, type, resultSet, t);
        }
        return t;
    }
    private static void getForBatch(Collection<String> list, Object type, String sql, Collection<Object> list1 ) throws SQLException {
        Statement statement;
        ResultSet resultSet = null;
        Object t = null;
        Field[] fields = type.getClass().getDeclaredFields();
        for (Field field : fields) {
            String s = field.getName().substring(0, 1).toUpperCase();
            String result = "set" + s + field.getName().substring(1);
            list.add(result);
        }
        try {
            statement = JDBCConnectionUtil.getConnection().createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert resultSet != null;
        while (resultSet.next()) {
            try {
                t = type.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (t == null) {
                throw new RuntimeException("创建实体类对象失败！");
            }
            FillMap(t);
            forBatchProperties(list, resultSet, t);
            list1.add(t);
        }
    }
    private static void forBatchProperties(Collection<String> list, ResultSet resultSet, Object t) {
        for (String s : list) {
            // 这里都到的方法是单个或者多个查询的最终的实体的方法
            for (Method method : t.getClass().getDeclaredMethods()) {
                setProperties(resultSet, t, s, method);
            }
        }
    }

    private static void setProperties(ResultSet resultSet, Object t, String s, Method method) {

        if (method.getName().startsWith("set") && s.equals(method.getName())) {

            Class<?>[] parameterTypes = method.getParameterTypes();
            // 获得去掉set并且把之后的第一个字母小写后的名称，也就是字段的原始名称
            String s1 = method.getName().substring(3);
            s1 = s1.substring(0, 1).toLowerCase() + s1.substring(1);


            // 如果遍历完字段和对应的方法上其中一个有注解，那么根据setter方法的名称取出对应的数据库表的字段的名称，然后取出数据组装
            if(map != null && map.size() != 0){
                if(map.get(s1) != null)
                    s1 = map.get(s1);
            }
            // 这里是组装参数的
            try {
                // setter 方法的参数类型的简单名称
                switch (parameterTypes[0].getSimpleName()) {
                    case "int":
                    case "Integer":
                        method.invoke(t, resultSet.getInt(s1));
                        break;
                    case "float":
                    case "Float":
                        method.invoke(t, resultSet.getFloat(s1));
                        break;
                    case "double":
                    case "Double":
                        method.invoke(t, resultSet.getDouble(s1));
                        break;
                    case "short":
                    case "Short":
                        method.invoke(t, resultSet.getShort(s1));
                        break;
                    case "long":
                    case "Long":
                        method.invoke(t, resultSet.getLong(s1));
                        break;
                    case "String":
                        method.invoke(t, resultSet.getString(s1));
                        break;
                    case "Date":
                        method.invoke(t, resultSet.getDate(s1));
                        break;
                    // 遇到set或者list 的时候进行再查询组装
                    case "Set":
                        forBatchProperties(resultSet, t, s, method, "Set");
                        break;
                    case "List":
                        forBatchProperties(resultSet, t, s, method, "List");
                    break;
                }
            } catch (Exception ignore) {
                // 忽略
                // 这里如果没有查询到，那么就是sql语句没有要求查询的字段
            }
        }
    }

    private static void forBatchProperties(ResultSet resultSet, Object t, String s, Method method, String methodName) throws IllegalAccessException, InvocationTargetException {
        Collection<Object> set;
        if(methodName.equals("Set")){
             set = new HashSet<>();
        }else {
            set = new ArrayList<>();
        }
        // 获取参数上的泛型信息,可能有多个泛型，这里因为是实体类的setter方法，所以只有一个泛型
        String name = "get"+s.substring(3);
        Class clazz = null;
        Object o = null;
        for (Method method1 : t.getClass().getMethods()) {
            if(method1.getName().startsWith("get") && name.equals(method1.getName())){
                Type genericReturnType = method1.getGenericReturnType();

                if(genericReturnType instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    clazz = (Class) typeArguments[0];
                }
                try {
                    if(clazz != null) {
                        o = clazz.newInstance();
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        List<String> list1 = new ArrayList<>();
        if(o != null) {
            Field[] declaredFields = o.getClass().getDeclaredFields();

            // 如果setter上的参数上的泛型获取成功并且 已经对泛型对象创建成功
            // 那么进行获取映射信息
            getSetters(declaredFields, list1, "set");

            // 映射信息已经获取完毕
            // 进入到组装泛型对象的setter方法
            forBatchProperties(list1, resultSet, o);
            set.add(o);
            method.invoke(t, set);
        }
    }

    private static void FillMap(Object t) {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            AnnotationUtil.forMap( field, null, map);
        }
        if( map.size() == 0){
            Method[] declaredMethods = t.getClass().getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                AnnotationUtil.forMap( null, declaredMethod, map);
            }
        }
    }

    private static <T> void forProperties(Collection<String> list, Class<T> type, ResultSet resultSet, T t) {
        for (String s : list) {
            for (Method method : type.getDeclaredMethods()) {
                setProperties(resultSet, t, s, method);
            }
        }
    }

    private static String getString(Object[] args, String sql) {
        if(!sql.contains("#") && args.length == 1)
            sql = String.format(sql, args[0]);
        else
        {
            StringBuilder target = new StringBuilder();
            for(int i = 0; i < sql.length(); i++){
                if(sql.charAt(i) == '#'){
                    target.append(sql.charAt(i));
                    int c = i;
                    while (sql.charAt(c) != ' ' && c != sql.length() -1){
                        c = c + 1;
                        target.append(sql.charAt(c));

                    }
                    break;
                }
            }
            System.out.println(target.toString());
            if(args[0].getClass().getSimpleName().equals("String"))
                sql = sql.replace(target.toString(), "'"+args[0]+"'");
            else
                sql = sql.replaceAll(target.toString(), args[0]+"");

        }
        return sql;
    }
}
