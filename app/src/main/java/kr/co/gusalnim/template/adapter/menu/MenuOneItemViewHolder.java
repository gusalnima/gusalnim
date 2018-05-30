package kr.co.gusalnim.template.adapter.menu;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scglab.common.listadapter.ItemRenderer;

import kr.co.gusalnim.template.R;
import kr.co.gusalnim.template.adapter.menu.MenuOneItem;

public class MenuOneItemViewHolder extends ItemRenderer<MenuOneItem> {

    private View bottomLine;
    private TextView txtTitle;
    private ImageView imgArr;

    private int depth;
    private int type;

    public MenuOneItemViewHolder(View view) {
        super(view);
        txtTitle = view.findViewById(R.id.txtTitle);
        imgArr = view.findViewById(R.id.imgArr);
        bottomLine = view.findViewById(R.id.bottomLine);
    }

    @Override
    protected void onBind(MenuOneItem item) {
        Drawable icon = ContextCompat.getDrawable(itemView.getContext(),item.getIcon());
        Drawable arr = ContextCompat.getDrawable(itemView.getContext(),item.getArr());
        String strColor = item.getColor();
        Log.i("gusalnim","item.getTitle() : " + item.getTitle());
        txtTitle.setTextColor(Color.parseColor(strColor));
        txtTitle.setCompoundDrawablesWithIntrinsicBounds(icon,null,null,null);
        txtTitle.setText(item.getTitle());
        imgArr.setImageDrawable(arr);
        depth = item.getDepth();
        if(item.isArr()) {
            imgArr.setRotation(180);
        } else {
            imgArr.setRotation(0);
        }
    }
}
