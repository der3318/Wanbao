package com.g7.wanbao;

import java.util.ArrayList;
import java.util.List;

import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.io.FileOperator;
import com.g7.wanbao.local.Data;
import com.g7.wanbao.object.FavoriteListAdapter;
import com.g7.wanbao.object.Item;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;

public class FavoriteHelper implements SectionHelper {
	
//	adapter
	FavoriteListAdapter adapter;
	List<Item> listItems;
	List<Boolean> listChecks;
	ListView listViewItems;
	Button btn_delete, btn_addToCart;
	CheckBox cb_selectAll;

	public void setup(final Activity _rootActivity, View _rootView) {
//		find view
		Typeface ldfont = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/lingdian.ttf");
		Typeface wqfont = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/wqmicrohei.ttf");
		listViewItems = (ListView) _rootView.findViewById(R.id.favorite_lv_items);
		listItems = new ArrayList<Item>();
		listChecks = new ArrayList<Boolean>();
		adapter = new FavoriteListAdapter(_rootActivity, listItems, listChecks);
		listViewItems.setAdapter(adapter);
		btn_delete = (Button) _rootView.findViewById(R.id.favorite_btn_delete);
		btn_delete.setTypeface(ldfont);
		btn_addToCart = (Button) _rootView.findViewById(R.id.favorite_btn_addToCart);
		btn_addToCart.setTypeface(ldfont);
		cb_selectAll = (CheckBox) _rootView.findViewById(R.id.cart_cb_selectAll);
		cb_selectAll.setTypeface(wqfont);
//		set listener
		btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String buffer = new FileOperator().read(_rootActivity.getApplicationInfo().dataDir + "/fav.txt");
				List<Item> newListItems = new ArrayList<Item>();
				for(int i = 0 ; i < listItems.size() ; i++)
					if(listChecks.get(i) == false)	newListItems.add(listItems.get(i));
					else if(buffer != null && buffer.isEmpty() == false)	buffer = buffer.replace(listItems.get(i).getID() + "\t", "");
				listItems.clear();
				listChecks.clear();
				if(newListItems.isEmpty())	adapter.notifyDataSetChanged();
				else	for(Item i : newListItems)	appendItem(_rootActivity, i);
				if(buffer != null)	new FileOperator().write(_rootActivity.getApplicationInfo().dataDir + "/fav.txt", buffer);
			}
		});
		btn_addToCart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String buffer = new FileOperator().read(_rootActivity.getApplicationInfo().dataDir + "/cart.txt");
				int count = 0;
				for(int i = 0 ; i < listItems.size() ; i++)	if(listChecks.get(i) == true) {
					count++;
					if(buffer != null && buffer.isEmpty() == false) {
						buffer = buffer.replace(listItems.get(i).getID() + "\t", "");
						buffer = buffer + listItems.get(i).getID() + "\t";
					}
					else	buffer = listItems.get(i).getID() + "\t";
				}
				if(buffer != null)	new FileOperator().write(_rootActivity.getApplicationInfo().dataDir + "/cart.txt", buffer);
				Toast popup = Toast.makeText(_rootActivity.getApplicationContext(), count + " Items Added to Cart", Toast.LENGTH_SHORT);
				popup.show();
			}
		});
		cb_selectAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true)	for(int i = 0 ; i < listChecks.size() ; i++)	listChecks.set(i, true);
				else	for(int i = 0 ; i < listChecks.size() ; i++)	listChecks.set(i, false);
				adapter.notifyDataSetChanged();
			}
		});
//		add items
		String buffer = new FileOperator().read(_rootActivity.getApplicationInfo().dataDir + "/fav.txt");
		String[] splt_buf = null;
		Data data = Data.getInstance(_rootActivity);
		if(buffer != null && buffer.isEmpty() == false)	splt_buf = buffer.split("\t");
		if(splt_buf != null)
			for(String s : splt_buf)	if(s.isEmpty() == false)	appendItem(_rootActivity, new Item(Integer.parseInt(s), data));
	}
	
	private void appendItem(final Activity _rootActivity, final Item _item) {
		_rootActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listItems.add(_item);
				listChecks.add(false);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
}
