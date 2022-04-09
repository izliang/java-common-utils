package list2tree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Cop-Projects
 * @description:
 * @author: izliang
 * @create: 2020-12-03 21:16
 **/
public class List2TreeUtils {

    public static <T> List<T> toTree(List<T> data) throws NoSuchFieldException, IllegalAccessException {

        if(data == null || data.size() == 0){
            throw new NullPointerException("数据为空");
        }

        List<T> result = new ArrayList<>();
        // 首先获取ToTree注解的类，然后获取Id注解和ParentId注解
        T t = data.get(0);
        Class tClass = t.getClass();
        List2Tree list2Tree = (List2Tree) tClass.getAnnotation(List2Tree.class);
        // 拿到分隔符
        String rootFlag  = list2Tree.rootFlag();
        if(null == rootFlag || "".equals(rootFlag)){
            throw new NoSuchFieldException("需要提供注解 @List2Tree");
        }

        Field idField = null;
        Field parentIdField = null;
        Field childrensField = null;

        // 现在开始拿Id字段和parentId字段以及childrens字段
        Field[] fields =  tClass.getDeclaredFields();
        for (Field field:fields) {
            Annotation idAnnotation = field.getAnnotation(List2TreeId.class);
            if(null != idAnnotation){
                idField = field;
            }
            Annotation parentIdAnnotation = field.getAnnotation(List2TreeParentId.class);
            if(null != parentIdAnnotation){
                parentIdField = field;
            }
            Annotation childrensAnnotation = field.getAnnotation(List2TreeChildrens.class);
            if(null != childrensAnnotation){
                childrensField = field;
            }
        }

        //如果用户没有标记注解那么抛出异常
        if(null == idField){
            throw new NoSuchFieldException("需要指定@List2TreeId注解");
        }

        if(null == parentIdField){
            throw new NoSuchFieldException("需要指定@List2TreeParentId注解");
        }

        if(null == childrensField){
            throw new NoSuchFieldException("需要指定@List2TreeChildrens注解");
        }
        result =  buildTree(data, idField.getName(), parentIdField.getName(),childrensField.getName(),rootFlag);


        return result;
    }


    private static <T> List<T> buildTree(List<T> cateGories,String idFieldName,String parentIdFieldName,String childrensFieldName,String rootFlag)
            throws IllegalAccessException, NoSuchFieldException {
        List<T> cateGoryList = new ArrayList<>();

        for (T cateGory:cateGories) {

            Field field =  cateGory.getClass().getDeclaredField(parentIdFieldName);
            field.setAccessible(true);
            if(rootFlag.equals(field.get(cateGory).toString())){
                cateGoryList.add(
                        findChildren(cateGory,cateGories,idFieldName,parentIdFieldName,childrensFieldName)
                );
            }
        }
        return cateGoryList;
    }

    private static <T> T findChildren(
            T cateGory,
            List<T> cateGories,
            String idFieldName,
            String parentIdFieldName,
            String childrensFieldName
    ) throws NoSuchFieldException, IllegalAccessException {

        for (T cateGory1:cateGories) {
            Field idField = cateGory.getClass().getDeclaredField(idFieldName);
            Field parentField = cateGory1.getClass().getDeclaredField(parentIdFieldName);
            idField.setAccessible(true);
            parentField.setAccessible(true);
            if(idField.get(cateGory).toString().equals(parentField.get(cateGory1).toString())){
                Field childrensField = cateGory.getClass().getDeclaredField(childrensFieldName);
                childrensField.setAccessible(true);
                if(childrensField.get(cateGory) == null){
                    // 给对象设置new的属性
                    childrensField.set(cateGory,new ArrayList<>());
                }
                List<T> childrens = (List<T>) childrensField.get(cateGory);
                childrens.add(findChildren(cateGory1,cateGories,idFieldName,parentIdFieldName,childrensFieldName));

                childrensField.set(cateGory,childrens);
            }
        }
        return cateGory;
    }

    /**
     * 树转列表
     * @param data
     * @param <T>
     * @return
     */
    public static <T> List<T> treeToList(List<T> data) throws NoSuchFieldException, IllegalAccessException {
        List<T> result = new ArrayList<>();

        // 首先获取ToTree注解的类，然后获取Id注解和ParentId注解
        T t = data.get(0);
        Class tClass = t.getClass();
        List2Tree list2Tree = (List2Tree) tClass.getAnnotation(List2Tree.class);
        // 拿到分隔符
        String rootFlag  = list2Tree.rootFlag();
        if(null == rootFlag || "".equals(rootFlag)){
            throw new NoSuchFieldException("需要提供注解 @List2Tree");
        }

        Field childrensField = null;

        Field[] fields =  tClass.getDeclaredFields();
        for (Field field:fields) {
            Annotation childrensAnnotation = field.getAnnotation(List2TreeChildrens.class);
            if(null != childrensAnnotation){
                childrensField = field;
            }
        }

        if(null == childrensField){
            throw new NoSuchFieldException("需要指定@List2TreeChildrens注解");
        }

        for (int i = 0; i < data.size(); i++) {
            result = (toList(data.get(i),result,childrensField.getName()));
        }


        return result;
    }

    private static <T> List<T> toList(T t,List<T> data,String childrensFieldName) throws NoSuchFieldException, IllegalAccessException {

        Field childrensField = t.getClass().getDeclaredField(childrensFieldName);
        childrensField.setAccessible(true);
        List<T> cs = (List<T>) childrensField.get(t);
        if(cs == null || cs.size() == 0){
            childrensField.set(t,new ArrayList<>());
            data.add(t);
        }else {
            childrensField.set(t,new ArrayList<>());
            data.add(t);
            for (T t1:cs) {
                toList(t1,data,childrensFieldName);
            }
        }
        return data;
    }
}
