package kr.co.gusalnim.template.adapter.menu;

import com.scglab.common.listadapter.FlexAdapter;
import com.scglab.common.listadapter.filter.Queryable;

@FlexAdapter.Item
public class SearchItem implements Queryable {

    private String hint;

    public SearchItem(String hint) {
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    @Override
    public boolean onQuery(CharSequence charSequence) {
        return true;
    }
}