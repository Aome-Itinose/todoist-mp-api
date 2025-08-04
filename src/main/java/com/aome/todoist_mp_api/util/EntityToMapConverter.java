package com.aome.todoist_mp_api.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityToMapConverter {
    public static Map<String, Object> toMap(Object entity) {
        Map<String, Object> map = new HashMap<>();

        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                map.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to convert entity to map", e);
            }
        }

        return map;
    }
}
