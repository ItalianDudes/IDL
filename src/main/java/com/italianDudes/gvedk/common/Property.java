package com.italianDudes.gvedk.common;

public final class Property {

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
    public boolean equals(Object obj) {
        if(!(obj instanceof Property))
            return false;
        Property property = (Property) obj;
        return property.key.equals(this.key) && property.value.equals(this.value);
    }
    @Override
    public String toString() {
        if(key.equals("#"))
            return key+value;
        else
            return key+"="+value;
    }
}
