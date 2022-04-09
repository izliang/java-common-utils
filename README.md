# java-common-utils

izliang基于Java的common utils工具类

## 模块说明

### list2tree模块说明

#### 模块说明

此模块是基于注解和反射实现的List结构转Tree结构的模块。通过在实体类绑定注解，然后调用静态方法，反射转换成树形结构，也可以将树形结构转为列表结构。

#### 注解说明

| 注解                | 说明                                                         |
| ------------------- | ------------------------------------------------------------ |
| @List2Tree          | 实体类上标注，此注解包含了根节点的父id，默认为0              |
| @List2TreeId        | 字段上的注解，此注解定义了字段为id字段，是进行父id识别的字段。 |
| @List2TreeParentId  | 字段上的注解，此注解定义了字段为父id字段，模块会自动根据此值寻找@List2TreeId标记字段。 |
| @List2TreeChildrens | List类型字段上的注解，此注解定义了最终存放子节点的容器。     |

#### 方法说明

##### List2TreeUtils.toTree(listModels)方法

- 1.作用：将list转变为tree结构
- 2.参数必须是具有定义注解的list类型实体类。

##### List2TreeUtils.treeToList(listModels)方法

- 1.作用：将tree结构转变为list结构
- 2.参数必须是具有定义注解的list类型实体类。

#### 实例

##### 实体类

```

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: izliang-mavem-utils
 * @description: 子数据实例
 * @author: izliang
 * @create: 2020-12-06 23:43
 **/
@List2Tree
public class ListModel implements Cloneable{

    @List2TreeId
    private int id;

    private String title;

    @List2TreeParentId
    private int parentId;

    @List2TreeChildrens
    private List<ListModel> childrens;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<ListModel> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<ListModel> childrens) {
        this.childrens = childrens;
    }

    @Override
    public String toString() {
        return "ListModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", parentId=" + parentId +
                ", childrens=" + childrens +
                '}';
    }
}
```

##### 调用

```

import java.util.ArrayList;
import java.util.List;

class List2TreeUtilsTest {

    public static void main(String[] args) throws CloneNotSupportedException, NoSuchFieldException, IllegalAccessException {

        List<ListModel> listModels = new ArrayList<>();

        ListModel listModel1 = new ListModel();
        listModel1.setId(1);
        listModel1.setTitle("分类1");
        listModel1.setParentId(0);

        listModels.add(listModel1);

        ListModel listModel2 = new ListModel();
        listModel2.setId(2);
        listModel2.setTitle("分类2");
        listModel2.setParentId(0);
        listModels.add(listModel2);

        ListModel listModel11 = new ListModel();
        listModel11.setId(3);
        listModel11.setTitle("分类1-1");
        listModel11.setParentId(1);
        listModels.add(listModel11);

        ListModel listModel21 = new ListModel();
        listModel21.setId(4);
        listModel21.setTitle("分类2-1");
        listModel21.setParentId(2);
        listModels.add(listModel21);

        List<ListModel> listModels1 =  List2TreeUtils.toTree(listModels);

        System.out.println(listModels1.toString());

        List<ListModel> listModels2 = List2TreeUtils.treeToList(listModels1);

        for (ListModel listModel:listModels2) {
            System.out.println(listModel.toString());
        }
    }
}
```