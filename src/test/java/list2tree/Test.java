package list2tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：izliang <izliang@qq.com>
 * @date ：Created in 2022/2/1 上午10:09
 * @description：
 */
public class Test {

    public static void main(String ... args) throws NoSuchFieldException, IllegalAccessException {

        List<TestModel> testModelList = Test.getExampleData();

        System.out.println("原始数组");
        Test.print(testModelList);

        // 数组转为树结构
        List<TestModel> tree = List2TreeUtils.toTree(testModelList);
        System.out.println("转为树结构");
        Test.print(tree);

        // 树结构转为数组
        List<TestModel> array = List2TreeUtils.treeToList(tree);
        System.out.println("还原为数组结构");
        Test.print(array);
    }


    private static void print(List<TestModel> testModels){
        testModels.forEach(item->{
            System.out.println(item.toString());
        });
    }

    public static List<TestModel> getExampleData(){
        List<TestModel> testModelList = new ArrayList<>(3);

        TestModel testModel1 = new TestModel();
        testModel1.setId(1L);
        testModel1.setTitle("1");
        testModel1.setPid(0L);
        testModelList.add(testModel1);

        TestModel testModel2 = new TestModel();

        testModel2.setId(2L);
        testModel2.setTitle("1-1");
        testModel2.setPid(1L);

        testModelList.add(testModel2);

        TestModel testModel3 = new TestModel();

        testModel3.setId(3L);
        testModel3.setTitle("1-2");
        testModel3.setPid(1L);

        testModelList.add(testModel3);

        return testModelList;
    }
}
