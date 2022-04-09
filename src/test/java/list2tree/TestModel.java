package list2tree;

import java.util.List;

/**
 * @author ：izliang <izliang@qq.com>
 * @date ：Created in 2022/2/1 上午10:10
 * @description：
 */

@List2Tree
public class TestModel {

    @List2TreeId
    private Long id;

    private String title;

    @List2TreeParentId
    private Long pid;

    @List2TreeChildrens
    private List<TestModel> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<TestModel> getChildren() {
        return children;
    }

    public void setChildren(List<TestModel> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pid=" + pid +
                ", children=" + (children == null ? "" : children.toString()) +
                '}';
    }
}
