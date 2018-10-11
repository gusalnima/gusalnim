package kr.co.gusalnim.template;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.scglab.common.listadapter.FlexAdapter;
import com.scglab.common.listadapter.OnItemClickEventHandler;
import com.scglab.common.listadapter.RendererFactory;
import com.scglab.common.listadapter.TypeStore;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import kr.co.gusalnim.template.adapter.EmptyItemViewHolder;
import kr.co.gusalnim.template.adapter.menu.MenuJson;
import kr.co.gusalnim.template.adapter.menu.MenuSearchItem;
import kr.co.gusalnim.template.adapter.menu.MenuSearchItemViewHolder;
import kr.co.gusalnim.template.adapter.menu.MenuSearchResultItemViewHolder;
import kr.co.gusalnim.template.adapter.menu.SearchItem;
import kr.co.gusalnim.template.net.dao.Menus;
import kr.co.gusalnim.template.util.MessageHelper;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    public static final String LOCAL_BROADCAST_SEARCH_KEYWORD = "SearchKeyword";
    public static final AtomicBoolean isEmptyQuery = new AtomicBoolean(false);

    private FlexAdapter flexAdapter;
    private RecyclerView recyclerView;
    private TextView searchTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initTitle();

        recyclerView = findViewById(R.id.recyclerView);
        searchTxt = findViewById(R.id.searchTxt);

        loadMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LOCAL_BROADCAST_SEARCH_KEYWORD);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void initTitle() {
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("검색");
        findViewById(R.id.btnBack).setOnClickListener(this);
    }

    private void loadMenu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RendererFactory rendererFactory = new RendererFactory();
        rendererFactory.put(MenuSearchItemViewHolder.class,R.layout.adapter_menu_search_item);
        rendererFactory.put(MenuSearchResultItemViewHolder.class,R.layout.adapter_menu_search_result_item);
        rendererFactory.put(EmptyItemViewHolder.class, R.layout.adapter_empty);
        flexAdapter = new FlexAdapter(rendererFactory);
        flexAdapter.setOnItemClickEventHandler(onItemClickEventHandler);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(flexAdapter);
        flexAdapter.addItem(new SearchItem("검색어를 입력해주세요."));
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case LOCAL_BROADCAST_SEARCH_KEYWORD:
                    String query = intent.getStringExtra(intent.getAction());
                    if (null == query) onEmptyQuery();
                    else search(query);
                    break;
            }
        }
    };

    private synchronized void onEmptyQuery() {
        //SelectedList.getInstance().searchClear();
        flexAdapter.removeItems(1, flexAdapter.getItemCount() - 1);
        //loadMainDivision();
    }


    private void onSearch(List<MenuSearchItem> item) {
        if (null == item || item.size() == 0) {
            MessageHelper.toast(this, R.string.empty_search);
            onEmptyQuery();
            return;
        }
        flexAdapter.removeItems(1, flexAdapter.getItemCount() - 1);

        for(MenuSearchItem s : item){
            Log.i("gusalnim"," : " + s.getTitle()+ " / " + s.getIdName() + " / " + s.getQuery() );
            flexAdapter.addItem(new MenuSearchItem(s.getTitle(),s.getIdName(),s.getQuery()));
        }
    }

    private List<MenuSearchItem> menuSearchItems = new ArrayList<>();
    private List<Menus.OneDep> oneMenu;
    private Resources resources;

    private void search(String query) {

        if (isEmptyQuery.get()) return;
        resources = getResources();
        MenuJson menuJson = new MenuJson();
        oneMenu = menuJson.initMenu(this);
        //final String[] strings = context.getResources().getStringArray(R.array.schedule_menu_list);
        //List<Menus.OneDep.TwoDep> twoDepAdd = new ArrayList<>();
        /*twoDepAdd.add(new Menus.OneDep.TwoDep(strings[0],null,"min0"));
        twoDepAdd.add(new Menus.OneDep.TwoDep(strings[1],null,"min1"));
        twoDepAdd.add(new Menus.OneDep.TwoDep(strings[2],null,"min2"));
        twoDepAdd.add(new Menus.OneDep.TwoDep(strings[4],null,"min4"));
        twoDepAdd.add(new Menus.OneDep.TwoDep(strings[5],null,"min5"));

        oneMenu.add(new Menus.OneDep("방문예약","#8ED7EF","menu_ic_charge","common_arr_btm_blue",twoDepAdd));*/

        //oneMenu.get(0).setTwoDep(twoDepAdd);


        menuSearchItems.clear();
        for (Menus.OneDep oneDep : oneMenu) {
            for (Menus.OneDep.TwoDep twoDep : oneDep.getTwoDep()) {

                String str = StringUtils.deleteWhitespace(twoDep.getTitle());
                String str2 = StringUtils.deleteWhitespace(query);
                if (str.contains(str2)) {
                    menuSearchItems.add(new MenuSearchItem(str,twoDep.getIdName(),str2));
                }
            }
        }

        Log.i("gusalnim","menuSearchItems : " + menuSearchItems);

        if(menuSearchItems.size() == 0) {
            onEmptyQuery();
        } else {
            onSearch(menuSearchItems);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
        }
    }

    private OnItemClickEventHandler onItemClickEventHandler = new OnItemClickEventHandler() {
        @Override
        public void onItemClick(Object item) {

        }

        @Override
        public void onItemLongClick(Object item) {

        }

        @Override
        public void onChildViewClick(Object item, int viewId) {
            MenuSearchItem menuSearchItem = TypeStore.getInstance().tryCast(MenuSearchItem.class, item);
            if(null != menuSearchItem) {
                if (viewId == R.id.txtTitle) flexAdapter.removeItem(item);
            }
        }

        @Override
        public void onChildViewLongClick(Object item, int viewId) {

        }
    };
}
