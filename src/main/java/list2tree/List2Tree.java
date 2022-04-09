package list2tree;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: izliang-utils
 * @description: 转换成树的基本注解
 * @author: izliang
 * @create: 2020-12-03 21:12
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface List2Tree {

    /**
     * 转换节点是根节点的标志，如果父节点是此项，那么就是跟节点
     * @return
     */
    public String rootFlag() default "0";

}
