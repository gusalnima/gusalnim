package kr.co.gusalnim.template.adapter.menu;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import kr.co.gusalnim.template.net.dao.Menus;

public class MenuJson {
    private List<Menus.OneDep> oneMenu = new LinkedList<Menus.OneDep>();
    private List<Menus.OneDep.TwoDep> list = new LinkedList<Menus.OneDep.TwoDep>();
    private JSONObject jo_inside;
    private Menus menus;
    private Context context;
    public String loadJSONFromAsset() {
        AssetManager am = context.getResources().getAssets();
        String json = null;
        try {
            InputStream is = am.open("menu.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public List<Menus.OneDep> initMenu(Context context) {
        this.context = context;
        try {
            JSONArray jObj = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i < jObj.length(); i++) {
                jo_inside = jObj.getJSONObject(i);
                JSONArray ja = jo_inside.getJSONArray("items");
                if(null != ja) {
                    int size = ja.length();
                    list = new LinkedList<Menus.OneDep.TwoDep>();
                    for (int j = 0; j < size; j++) {
                        JSONObject jo = ja.getJSONObject(j);
                        if (jo != null) {
                            list.add(new Menus.OneDep.TwoDep(jo.optString("title"), jo.optString("icon"), jo.optString("idName")));
                        }
                    }
                }
                if(jo_inside.has("idName")){
                    oneMenu.add(new Menus.OneDep(jo_inside.getString("title"), jo_inside.getString("color"), jo_inside.getString("icon"), jo_inside.getString("arr"), jo_inside.getString("idName"), list));
                } else {
                    oneMenu.add(new Menus.OneDep(jo_inside.getString("title"), jo_inside.getString("color"), jo_inside.getString("icon"), jo_inside.getString("arr"), null, list));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("gusalnim",e.getLocalizedMessage());
        }
        return oneMenu;
    }

}
