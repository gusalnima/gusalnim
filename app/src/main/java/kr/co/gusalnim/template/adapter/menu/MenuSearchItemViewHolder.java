package kr.co.gusalnim.template.adapter.menu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.scglab.common.listadapter.ItemRenderer;
import com.scglab.common.util.InputHelper;

import java.util.concurrent.atomic.AtomicBoolean;

import kr.co.gusalnim.template.SearchActivity;

public class MenuSearchItemViewHolder extends ItemRenderer<SearchItem> implements TextWatcher {
    private AtomicBoolean isEmptyQuery;
    private EditText txtSearch;
    private Button btnDelete;

    public MenuSearchItemViewHolder(View view) {
        super(view);
        isEmptyQuery = new AtomicBoolean(false);

    }

    @Override
    protected void onAttachedRenderer() {
        super.onAttachedRenderer();

        txtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputHelper.keyboardHide(itemView.getContext(), v);
                }
            }
        });
        txtSearch.addTextChangedListener(this);
    }

    @Override
    protected void onDetachedRenderer() {
        super.onDetachedRenderer();
        txtSearch.removeTextChangedListener(this);
    }


    @Override
    protected void onBind(SearchItem model) {
        txtSearch.setHint(model.getHint());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch.setText(null);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        handler.removeMessages(0);


        String query = s.toString();

        Message message = Message.obtain();
        message.what = 0;

        if (null == query || query.isEmpty()) {
            SearchActivity.isEmptyQuery.set(true);
            btnDelete.setVisibility(View.GONE);
            handler.sendMessageDelayed(message, 0);
        } else {
            SearchActivity.isEmptyQuery.set(false);
            message.obj = query;
            btnDelete.setVisibility(View.VISIBLE);
            handler.sendMessageDelayed(message, 500);
        }
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final String query = (String) msg.obj;

            Intent intent = new Intent(SearchActivity.LOCAL_BROADCAST_SEARCH_KEYWORD);
            intent.putExtra(SearchActivity.LOCAL_BROADCAST_SEARCH_KEYWORD, query);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        }
    };
}
