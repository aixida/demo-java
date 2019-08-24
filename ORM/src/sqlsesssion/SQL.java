package sqlsesssion;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//1.自己创建一个注解类型
//2.通过元注解来描述这个自定义注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQL {

    String sql();
    SQLEnums type();
    Class resultType() default Object.class;

}
