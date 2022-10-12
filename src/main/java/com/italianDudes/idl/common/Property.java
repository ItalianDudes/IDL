package com.italianDudes.idl.common;

import java.io.Serializable;

public final class Property implements Serializable {

    //Attributes
    private final String key;
    private final String value;

    //Constructors
    public Property(String key, String value){
        this.key = key;
        this.value = value;
    }

    //Methods
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;

        Property property = (Property) o;

        if (!getKey().equals(property.getKey())) return false;
        return getValue().equals(property.getValue());
    }
    @Override
    public int hashCode() {
        int result = getKey().hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }

    @Override
    public String toString() {
        if(key.equals("#"))
            return key+value;
        else
            return key+"="+value;
    }
}
