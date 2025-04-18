package com.alten.shop.runtime.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Patcher<T, C> {
    public void patchObject(T result, C origin) throws IllegalAccessException {

        //GET THE COMPILED VERSION OF THE CLASS
        Class<?> resultClass = result.getClass();
        Field[] resultFields = resultClass.getDeclaredFields();
        for (Field field : resultFields) {
            //CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);
            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING OBJECT
            Object value = field.get(origin);
            if (value != null) {
                field.set(result, value);
            }
            //MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }

    }
}
