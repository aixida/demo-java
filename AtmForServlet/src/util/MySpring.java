package util;

import java.util.HashMap;

public class MySpring {

    //对象的管理


    private static HashMap<String,Object> beanMap = new HashMap<>();

    public static <T>T getBean(String className){
        T obj = (T)beanMap.get(className);
        if(obj == null){
            try {
                obj = (T) Class.forName(className).newInstance();
                beanMap.put(className,obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

}
