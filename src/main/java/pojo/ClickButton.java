package pojo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * 二级菜单项或者
 * 不含二级菜单项的一级菜单项
 *
 * @auther ZhengTianle
 * @Date: 18-7-15
 */
public class ClickButton extends Button{
    private String type = Menu.CLICK;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
