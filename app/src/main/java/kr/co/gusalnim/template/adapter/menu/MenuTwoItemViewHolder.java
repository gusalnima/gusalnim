package kr.co.gusalnim.template.adapter.menu;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.scglab.common.listadapter.ItemRenderer;

import kr.co.gusalnim.template.R;

public class MenuTwoItemViewHolder extends ItemRenderer<MenuTwoItem> {

    private View view;
    private TextView txtTitle;

    public MenuTwoItemViewHolder(View view) {
        super(view);
        this.view = view;
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    @Override
    protected void onBind(MenuTwoItem item) {
        Drawable icon = ContextCompat.getDrawable(itemView.getContext(),item.getIcon());
        txtTitle.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);
        txtTitle.setText(item.getTitle());
    }
}
