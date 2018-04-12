package com.belling.base.enums;

/**
 * @author lzw on 2017/10/24.
 */
public enum SysEnum {
    SUCCESS(0,"SUCCESS"),

    ERROR(1,"ERROR");

    private int type;

    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    SysEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
