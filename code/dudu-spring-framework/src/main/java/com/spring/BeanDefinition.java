package com.spring;

/**
 * @Description BeanDefinition
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
public class BeanDefinition {

    private Class type;

    private String scope;

    private boolean isLazy;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }
}