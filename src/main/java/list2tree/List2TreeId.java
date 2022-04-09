package list2tree;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: list转tree的ID注解，被绑定该注解的属性将作为子节点的父节点的指向
 * @author izliang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface List2TreeId {

}
