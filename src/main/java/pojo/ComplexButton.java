package pojo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * 包含有二级菜单项的一级菜单
 *
 * @auther ZhengTianle
 * @Date: 18-7-15
 */
public class ComplexButton extends Button{

    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
