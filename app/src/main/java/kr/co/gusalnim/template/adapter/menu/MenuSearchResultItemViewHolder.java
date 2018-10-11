package kr.co.gusalnim.template.adapter.menu;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.scglab.common.listadapter.ItemRenderer;

import org.apache.commons.lang3.StringUtils;

import kr.co.gusalnim.template.R;

public class MenuSearchResultItemViewHolder extends ItemRenderer<MenuSearchItem> {

    private TextView txtTitle;
    private Context context;
    public MenuSearchResultItemViewHolder(View view) {
        super(view);
        context = itemView.getContext();
    }

    @Override
    protected void onBind(MenuSearchItem item) {
        if(null!=item.getQuery() && null != item.getTitle() ){
            Log.i("gusalnim","item.getTitle() : " + item.getTitle());
            SpannableStringBuilder builder = new SpannableStringBuilder(item.getTitle());

            String str = StringUtils.deleteWhitespace(item.getTitle());
            String str2 = StringUtils.deleteWhitespace(item.getQuery());
            int a = str.indexOf(item.getQuery());
            int b = str2.length();
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.colorPrimary)), a, a + b, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            txtTitle.setText("");
            txtTitle.append(builder);
        } else {
            txtTitle.setText(item.getTitle());
        }
    }

    @Override
    protected void onAttachedRenderer() {
        addChildViewClickListener(txtTitle);
    }

    @Override
    protected void onDetachedRenderer() {
        removeChildViewClickListener(txtTitle);
    }
}
