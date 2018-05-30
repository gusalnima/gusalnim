package kr.co.gusalnim.template.adapter.menu;

import com.scglab.common.listadapter.FlexAdapter;

@FlexAdapter.Item
public class MenuTwoItem implements IDepth {
    private IDepth parent;

    private int depth;

    private int icon;
    private String title;
    private String idName;


    public  MenuTwoItem(String title, int icon, String idName, int depth) {
        this.icon = icon;
        this.title = title;
        this.idName = idName;
        this.depth = depth;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getIdName() {
        return idName;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public IDepth getParent() {
        return parent;
    }
}
