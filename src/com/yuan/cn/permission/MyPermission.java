package com.yuan.cn.permission;

import java.security.Permission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MyPermission extends Permission {
    private String action;
    public MyPermission(String text,String action){
        super(text);
        this.action = action;
    }
    @Override
    public boolean implies(Permission permission) {
        System.out.println(permission.getName());
        if(!(permission instanceof MyPermission))
        {
            return false;
        }
        MyPermission p =(MyPermission) permission;
        if(action.equals("insert"))
        {
            return p.action.equals("insert")&&getName().indexOf(p.getName()) >=0;
        }else if(action.equals("avoid"))
        {
            if(p.action.equals("avoid")){return p.badWorkSet().containsAll(badWorkSet());}
            else if(p.action.equals("insert"))
            {
                for(String b : badWorkSet())
                {
                    if(p.getName().indexOf(b)>=0)
                    {
                        return false;
                    }
                }
                return true;
            }else
            {
                return false;
            }

        }else {return false;}
    }

    @Override
    public boolean equals(Object other) {
        if(other == null)
        {
            return false;
        }
        if(!getClass().equals(other.getClass()))
        {
            return false;
        }
        MyPermission p =(MyPermission)other;
        if(!Objects.equals(action,p.action))
        {
            return false;
        }
        if("insert".equals(action))
        {
            return Objects.equals(getName(),p.getName());
        }else if("avoid".equals(action))
        {
            return badWorkSet().equals(p.badWorkSet());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),action);
    }

    @Override
    public String getActions() {
        return action;
    }
    public Set<String> badWorkSet()
    {
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(getName().split(",")));
        return set;
    }
}
