package sqlsesssion;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DaoProxy {

    //这个类是Dao类对象的代理


    //设计一个方法，帮我获取一个代理对象
    //  返回值：代理的对象并不确定（可能是TeacherDao，也可能是其它...Dao），所以返回值为object
    //  参数：得知道获取的是哪个类的代理
    public static Object getInstance(Class c){
        //Java提供了一个现成的类帮我们管理
        //  第一个参数：类加载器（为了生成代理对象，要通过反射去加载）
        //  第二个参数：Class类型的数组，这个数组中传入需要生成代理的类，即告诉我要给谁代理
        //  第三个参数：方法，即告诉我要代理你干什么事
        //      MethodProxy是一个接口，得实现它
        return Proxy.newProxyInstance(c.getClassLoader(),new Class[]{c},new MethodProxy());

    }

    //内部类：
    private static class MethodProxy implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //参数：代理对象，代理方法对象，方法中传进来的参数

            if(Object.class.equals(method.getDeclaringClass())){//传进来的Dao不是接口，是正常类
                return method.invoke(this,args);
            }else {
                //代理需要干活了
                //  1.读取SQL语句
                //  2.告诉SqlSessionFactory去执行
                SQL sql = method.getAnnotation(SQL.class);
                return execute(method,sql,args);
            }
        }

        //负责判断到底该告诉SqlSessionFactory去执行哪个具体的操作
        private Object execute(Method method,SQL sql,Object[] args) throws Exception {
            if(sql != null){
                switch (sql.type()){
                    case QUERY:
                        return SqlSessionFactory.selectOne(sql.sql(),args,sql.resultType());
                    case UPDATE:
                    case DELETE:
                        return SqlSessionFactory.update(sql.sql(),args);
                    case IESERT:
                        return SqlSessionFactory.insert(sql.sql(),args);
                }
            }
            return null;
        }

    }

}
