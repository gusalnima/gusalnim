package kr.co.gusalnim.template.adapter.menu;

import com.scglab.common.listadapter.FlexAdapter;

@FlexAdapter.Item
public class MenuSearchItem {
    private String query;
    private String title;
    private String idName;


    public MenuSearchItem(String title, String idName, String query) {
        this.title = title;
        this.query = query;
        this.idName = idName;
    }

    public String getTitle() {
        return title;
    }

    public String getQuery() {
        return query;
    }

    public String getIdName() {
        return idName;
    }

}
