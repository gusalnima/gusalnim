package kr.co.gusalnim.template;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.scglab.common.listadapter.FlexAdapter;
import com.scglab.common.listadapter.OnItemClickEventHandler;
import com.scglab.common.listadapter.RendererFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.co.gusalnim.template.adapter.menu.IDepth;
import kr.co.gusalnim.template.adapter.menu.MenuJson;
import kr.co.gusalnim.template.adapter.menu.MenuOneItem;
import kr.co.gusalnim.template.adapter.menu.MenuOneItemViewHolder;
import kr.co.gusalnim.template.adapter.menu.MenuTwoItem;
import kr.co.gusalnim.template.adapter.menu.MenuTwoItemViewHolder;
import kr.co.gusalnim.template.fcm.FCMHelper;
import kr.co.gusalnim.template.net.dao.Menus;
import kr.co.gusalnim.template.util.MessageHelper;
import kr.co.gusalnim.template.util.PhoneNumberGetHelper;

public class MainActivity extends BaseActivity {

    private long backPressedTime;

    private RecyclerView menuRecycler;
    private FlexAdapter flexAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList itemList = new ArrayList<>();
    private ArrayList itemListSub = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fcm token 가져오기
        FCMHelper.init(this);

        initUi();

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }
        backPressedTime = System.currentTimeMillis();
        MessageHelper.toast(this, R.string.common_exit);
    }

    private void initUi() {
        menuRecycler = findViewById(R.id.menuRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        menuRecycler.setLayoutManager(linearLayoutManager);

        RendererFactory rendererFactory = new RendererFactory();
        rendererFactory.put(MenuOneItemViewHolder.class, R.layout.adapter_menu_one_item);
        rendererFactory.put(MenuTwoItemViewHolder.class, R.layout.adapter_menu_two_item);

        flexAdapter = new FlexAdapter(rendererFactory);
        flexAdapter.setOnItemClickEventHandler(onItemClickEventHandler);
        menuRecycler.setAdapter(flexAdapter);

        loadOneMenu();

    }

    private MenuOneItem oldMenuOneItem;
    private OnItemClickEventHandler onItemClickEventHandler = new OnItemClickEventHandler() {
        @Override
        public void onItemClick(Object o) {
            if (o instanceof MenuOneItem) {
                MenuOneItem menuOneItem = (MenuOneItem) o;
                if (null != oldMenuOneItem && oldMenuOneItem != menuOneItem) {
                    removeAllChildren(oldMenuOneItem);
                }
                if (menuOneItem.isLoaded()) {
                    removeAllChildren(menuOneItem);
                } else {
                    loadTwoMenu(menuOneItem);
                    oldMenuOneItem = menuOneItem;
                }
            } else if(o instanceof MenuTwoItem) {
                MenuTwoItem menuTwoItem = (MenuTwoItem) o;
                if ("popupPermissionAlert".equals(menuTwoItem.getIdName())) {
                    startActivity(new Intent(MainActivity.this,PopupPermissionAlertActivity.class));
                }
            }
        }

        @Override
        public void onItemLongClick(Object o) {
            Log.i("gusalnim","onItemLongClick");
        }

        @Override
        public void onChildViewClick(Object o, int i) {
            Log.i("gusalnim","onChildViewClick");
        }

        @Override
        public void onChildViewLongClick(Object o, int i) {
            Log.i("gusalnim","onChildViewLongClick");
        }
    };

    private void removeAllChildren(MenuOneItem model) {
        model.setArr(false);
        model.setLoaded(false);

        final int depth = model.getDepth() + 1;
        final int size = flexAdapter.getItemCount();
        int position = flexAdapter.getItemPosition(model);
        final int startPosition = position;

        IDepth child;
        do {
            position++;
            if (position >= size) break;
            child = (IDepth) flexAdapter.getItem(position);
        } while (depth <= child.getDepth());

        flexAdapter.removeItems(startPosition + 1, position - 1);
        flexAdapter.notifyItemChanged(startPosition);

    }

    private Resources resources;
    private List<Menus.OneDep> oneMenu;
    private void loadOneMenu() {
        resources = this.getResources();
        MenuJson menuJson = new MenuJson();
        oneMenu = menuJson.initMenu(this);
        int i = 0;
        boolean isLast = false;
        for (Menus.OneDep oneDep : oneMenu) {
            i++;
            if (oneMenu.size() == i) {
                isLast = true;
            }
            final int resourceId = resources.getIdentifier(oneDep.getIcon(), "drawable", this.getPackageName());
            final int arrId = resources.getIdentifier(oneDep.getArr(), "drawable", this.getPackageName());
            Collections.reverse(oneDep.getTwoDep());
            itemList.add(new MenuOneItem(resourceId, oneDep.getTitle(), oneDep.getColor(), arrId, oneDep.getTwoDep(), isLast, 0));
            Log.i("gusalnim","oneDep.getTitle() : " + oneDep.getTitle());
        }
        flexAdapter.setModels(itemList);

    }

    private void loadTwoMenu(MenuOneItem menuOneItem) {
        menuOneItem.setArr(true);
        List<Menus.OneDep.TwoDep> twoMenu = menuOneItem.getTwoDep();
        int position = flexAdapter.getItemPosition(menuOneItem);
        Log.i("gusalnim","position : " + position);
        linearLayoutManager.scrollToPositionWithOffset(position - 1, 0);
        if (position == -1) return;
        else position++;
        menuOneItem.setLoaded(true);
        for (Menus.OneDep.TwoDep twoDep : twoMenu) {
            final int resourceId = resources.getIdentifier(twoDep.getIcon(), "drawable", this.getPackageName());
            Log.i("gusalnim","position1 : " + position);
            flexAdapter.addItem(position, new MenuTwoItem(twoDep.getTitle(), resourceId, twoDep.getIdName(), 1));
        }
        //flexAdapter.notifyItemChanged(position - 1);
    }

}
