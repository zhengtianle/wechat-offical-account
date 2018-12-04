package pojo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * 总菜单项的封装
 * 也就是显示在底部菜单(最多有三个)
 * -->一级菜单
 *
 * @auther ZhengTianle
 * @Date: 18-7-15
 */
public class Menu {

    public final static String CLICK = "click"; // click菜单
    public final static String VIEW = "view"; // url菜单

    Button[] button;

    public Button[] getButton() {
        return button;
    }

    public void setButton(Button[] button) {
        this.button = button;
    }
}
