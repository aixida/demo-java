package util;

import java.util.HashMap;

//目的是为了管理对象的产生
//对象的控制权交给当前类来负责    IOC控制反转
//生命周期托管的方式实现了对象的单例
public class MySpring {

    //属性  为了存储所有被管理的对象
    private static HashMap<String, Object> beanBox = new HashMap<>();

    //设计一个方法  获取任何一个类的对象
    //返回值（泛型）
    public static <T>T getBean(String className){
        T obj = null;
        try {

            //1.直接从beanBox中获取
            obj = (T)beanBox.get(className);
            //2.判断,若obj为空，则之前没有创建这个对象
            if(obj == null){
                Class c = Class.forName(className);
                obj = (T)c.newInstance();
                beanBox.put(className,obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
