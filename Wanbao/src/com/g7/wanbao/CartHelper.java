package com.g7.wanbao;

import java.util.ArrayList;
import java.util.List;

import com.g7.wanbao.csmuse.CSmuseServerManager;
import com.g7.wanbao.csmuse.Language;
import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.io.FileOperator;
import com.g7.wanbao.local.Data;
import com.g7.wanbao.object.CartListAdapter;
import com.g7.wanbao.object.Item;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class CartHelper implements SectionHelper {

	// adapter
	CartListAdapter adapter;
	List<Item> listItems;
	List<Boolean> listChecks;
	List<Integer> listQts, listPrices;
	ListView listViewItems;
	Button btn_delete, btn_pay;
	CheckBox cb_selectAll;
	TextView tv_already, tv_qt;

	public void setup(final Activity _rootActivity, View _rootView) {
		// text font
		Typeface fsfont = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/fangsong.ttf");
		Typeface ldfont = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/lingdian.ttf");
		Typeface wqfont = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/wqmicrohei.ttf");
		tv_already = (TextView) _rootView.findViewById(R.id.cart_tv_already);
		tv_already.setTypeface(fsfont);
		tv_qt = (TextView) _rootView.findViewById(R.id.cart_tv_qt);
		tv_qt.setTypeface(fsfont);

		// find view
		listViewItems = (ListView) _rootView.findViewById(R.id.cart_lv_items);
		listItems = new ArrayList<Item>();
		listChecks = new ArrayList<Boolean>();
		listQts = new ArrayList<Integer>();
		listPrices = new ArrayList<Integer>();
		adapter = new CartListAdapter(_rootActivity, listItems, listChecks, listQts);
		listViewItems.setAdapter(adapter);
		btn_delete = (Button) _rootView.findViewById(R.id.cart_btn_delete);
		btn_delete.setTypeface(ldfont);
		btn_pay = (Button) _rootView.findViewById(R.id.cart_btn_pay);
		btn_pay.setTypeface(ldfont);
		cb_selectAll = (CheckBox) _rootView.findViewById(R.id.cart_cb_selectAll);
		cb_selectAll.setTypeface(wqfont);
		
		// set listener
		btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String buffer = new FileOperator().read(_rootActivity.getApplicationInfo().dataDir + "/cart.txt");
				List<Item> newListItems = new ArrayList<Item>();
				List<Integer> newListQts = new ArrayList<Integer>();
				for (int i = 0; i < listItems.size(); i++)
					if (listChecks.get(i) == false) {
						newListItems.add(listItems.get(i));
						newListQts.add(listQts.get(i));
					} else if (buffer != null && buffer.isEmpty() == false)
						buffer = buffer.replace(listItems.get(i).getID() + "\t", "");
				listItems.clear();
				listChecks.clear();
				listQts.clear();
				if (newListItems.isEmpty())
					adapter.notifyDataSetChanged();
				else
					for (int i = 0; i < newListItems.size(); i++)
						appendItem(_rootActivity, newListItems.get(i), newListQts.get(i));
				if (buffer != null)
					new FileOperator().write(_rootActivity.getApplicationInfo().dataDir + "/cart.txt", buffer);
				new FetchPriceTask(_rootActivity).execute();
			}
		});
		btn_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String buffer = new FileOperator().read(_rootActivity.getApplicationInfo().dataDir + "/cart.txt");
				int count = 0;
				ArrayList<Integer> params = new ArrayList<Integer>();
				params.add(count);
				List<Item> newListItems = new ArrayList<Item>();
				List<Integer> newListQts = new ArrayList<Integer>();
				for (int i = 0; i < listItems.size(); i++) {
					if (listChecks.get(i) == false) {
						newListItems.add(listItems.get(i));
						newListQts.add(listQts.get(i));
					} else {
						params.add(listItems.get(i).getID());
						params.add(listQts.get(i));
						params.add(listPrices.get(i));
						count = count + listPrices.get(i) * listQts.get(i);
						if (buffer != null && buffer.isEmpty() == false)
							buffer = buffer.replace(listItems.get(i).getID() + "\t", "");
					}
				}
				listItems.clear();
				listChecks.clear();
				listQts.clear();
				if (newListItems.isEmpty())
					adapter.notifyDataSetChanged();
				else
					for (int i = 0; i < newListItems.size(); i++)
						appendItem(_rootActivity, newListItems.get(i), newListQts.get(i));
				if (buffer != null)
					new FileOperator().write(_rootActivity.getApplicationInfo().dataDir + "/cart.txt", buffer);
				
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				long orderid = System.currentTimeMillis();
				params.set(0, count);
				bundle.putString("orderid", orderid + "");
				bundle.putIntegerArrayList("params", params);				
				intent.setClass(_rootActivity, CheckoutActivity.class);
				intent.putExtras(bundle);
				_rootActivity.startActivity(intent);
				new FetchPriceTask(_rootActivity).execute();
			}
		});
		cb_selectAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked == true)
					for (int i = 0; i < listChecks.size(); i++)
						listChecks.set(i, true);
				else
					for (int i = 0; i < listChecks.size(); i++)
						listChecks.set(i, false);
				adapter.notifyDataSetChanged();
			}
		});
		// add items
		String buffer = new FileOperator().read(_rootActivity.getApplicationInfo().dataDir + "/cart.txt");
		String[] splt_buf = null;
		Data data = Data.getInstance(_rootActivity);
		if (buffer != null && buffer.isEmpty() == false)
			splt_buf = buffer.split("\t");
		if (splt_buf != null)
			for (String s : splt_buf)
				if (s.isEmpty() == false)
					appendItem(_rootActivity, new Item(Integer.parseInt(s), data), 1);
		new FetchPriceTask(_rootActivity).execute();
	}

	private void appendItem(final Activity _rootActivity, final Item _item, final int _qt) {
			listItems.add(_item);
			listChecks.add(false);
			listQts.add(_qt);
			adapter.notifyDataSetChanged();
	}
	
	private class FetchPriceTask extends AsyncTask<Void, Void, Integer> {
    	
		Activity rootActivity;
		
		public FetchPriceTask(Activity _rootActivity) {
            this.rootActivity = _rootActivity;
            btn_pay.setClickable(false);
            listPrices.clear();
        }

        protected Integer doInBackground(Void... params) {
            int result = 0;
            try {
            	CSmuseServerManager manager = CSmuseServerManager.getInstance(rootActivity);
            	for(int i = 0 ; i < listItems.size() ; i++)
            		listPrices.add(manager.getProduct(listItems.get(i).getID(), Language.ENG).getSellPriceCNY());
            	result = 1;
            } catch(Exception e) {
            	Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Integer result) {
        	if(result == 1)	btn_pay.setClickable(true);
        }
    }

}
