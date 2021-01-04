package com.smartbics;

public enum MessageType {
    ERROR("ERROR", "Ошибка"),
    WARN("WARN", "Предупреждение"),
    DEBUG("DEBUG", "Отладка"),
    INFO("INFO", "Информация");
    private String type;
    private String desc;

    MessageType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
