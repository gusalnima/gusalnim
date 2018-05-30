package kr.co.gusalnim.template.adapter.menu;

import com.scglab.common.listadapter.FlexAdapter;

import java.util.List;

import kr.co.gusalnim.template.net.dao.Menus;

@FlexAdapter.Item
public class MenuOneItem implements IDepth {
    private IDepth parent;
    private int depth;

    private int icon;
    private String title;
    private String color;
    private int arr;
    private boolean isLoaded;
    private boolean isArr;
    private boolean isLast;
    private List<Menus.OneDep.TwoDep> twoDep;
    public MenuOneItem(int icon, String title, String color, int arr, List<Menus.OneDep.TwoDep> twoDep, boolean isLast, int depth) {
        this.icon = icon;
        this.title = title;
        this.color = color;
        this.arr = arr;
        this.isLast = isLast;
        this.depth = depth;
        this.twoDep =twoDep;
    }


    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public boolean isLoaded() {

        return isLoaded;
    }

    public void setArr(boolean arr) {
        isArr = arr;
    }

    public boolean isArr() {

        return isArr;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLast() {
        return isLast;
    }

    public int getIcon() {
        return icon;
    }

    public String getColor() {
        return color;
    }

    public int getArr() {
        return arr;
    }

    public List<Menus.OneDep.TwoDep> getTwoDep() {
        return twoDep;
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
