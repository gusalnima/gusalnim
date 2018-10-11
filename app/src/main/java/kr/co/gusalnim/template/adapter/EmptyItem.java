package kr.co.gusalnim.template.adapter;

import com.scglab.common.listadapter.FlexAdapter;

@FlexAdapter.Item
public class EmptyItem {

    private int txtRes;
    private boolean isDocument;

    public EmptyItem() { }
    public EmptyItem(int txtRes) {
        this.txtRes = txtRes;
    }
    public EmptyItem(int txtRes, boolean isDocument) {
        this.txtRes = txtRes;
        this.isDocument = isDocument;
    }

    public int getTxtRes() {
        return txtRes;
    }

    public boolean isDocument() {
        return isDocument;
    }
}
