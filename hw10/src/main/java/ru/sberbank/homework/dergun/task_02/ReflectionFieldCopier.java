package ru.sberbank.homework.dergun.task_02;

import ru.sberbank.homework.common.BeanFieldCopier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionFieldCopier implements BeanFieldCopier {
    private void fillMapMethods(Map<String, Map<String, Method>> map, Object object, String startWith) {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith(startWith)) {
                Map<String, Method> mapTypeMethod = new HashMap<>();
                String contain = method.getName().replace(startWith, "");
                if (map.get(contain) != null) {
                    mapTypeMethod = map.get(contain);
                }
                if (startWith.equals("get") && method.getParameterTypes().length == 0) {
                    mapTypeMethod.put(method.getReturnType().getName(), method);
                } else if (startWith.equals("set") && method.getParameterTypes().length == 1) {
                    mapTypeMethod.put(method.getParameterTypes()[0].getName(), method);
                }
                map.put(method.getName().replace(startWith, ""), mapTypeMethod);
            }
        }
    }


    @Override
    public void copy(Object from, Object to) {

        Map<String, Map<String, Method>> mapMethodsFrom = new HashMap<>();
        fillMapMethods(mapMethodsFrom, from, "get");
        Map<String, Map<String, Method>> mapMethodsTo = new HashMap<>();
        fillMapMethods(mapMethodsTo, to, "set");
        for (String key : mapMethodsFrom.keySet()) {
            mapMethodsFrom.get(key).keySet().stream()
                    .filter(type -> mapMethodsTo.get(key) != null && mapMethodsTo.get(key).get(type) != null)
                    .forEach(type -> {
                        Method methodFrom = mapMethodsFrom.get(key).get(type);
                        Method methodTo = mapMethodsTo.get(key).get(type);
                        try {
                                methodTo.invoke(to, methodFrom.invoke(from));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}