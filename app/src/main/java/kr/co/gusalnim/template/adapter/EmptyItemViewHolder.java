package kr.co.gusalnim.template.adapter;

import android.view.View;
import android.widget.TextView;

import com.scglab.common.listadapter.ItemRenderer;

import kr.co.gusalnim.template.R;


public class EmptyItemViewHolder extends ItemRenderer<EmptyItem> {

    private TextView txtLabel;

    public EmptyItemViewHolder(View view) {
        super(view);
        txtLabel = (TextView) view.findViewById(R.id.txtLabel);
    }

    @Override
    protected void onBind(EmptyItem model) {
        EmptyItem emptyItem = (EmptyItem) model;

        txtLabel.setText(emptyItem.getTxtRes());
    }
}