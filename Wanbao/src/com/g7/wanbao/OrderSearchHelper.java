package com.g7.wanbao;

import java.util.ArrayList;
import java.util.List;

import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.io.FileOperator;
import com.g7.wanbao.object.OrderListAdapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;


public class OrderSearchHelper implements SectionHelper {
	
//	adapter
	OrderListAdapter adapter;
	List<String> listItems;
	ListView listViewItems;
	TextView tv_title, tv_name, tv_perItemPrice, tv_itemQt;

//	fonts
	private Typeface fsfont;
	
	public void setup(final Activity _rootActivity, View _rootView) {
		fsfont = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/fangsong.ttf");
        
        tv_title = (TextView) _rootView.findViewById(R.id.order_tv_title);
        tv_title.setTypeface(fsfont);
        tv_name = (TextView) _rootView.findViewById(R.id.order_tv_itemName);
        tv_name.setTypeface(fsfont);
        tv_perItemPrice = (TextView) _rootView.findViewById(R.id.order_tv_perItemPrice);
        tv_perItemPrice.setTypeface(fsfont);
        tv_itemQt = (TextView) _rootView.findViewById(R.id.order_tv_itemQt);
        tv_itemQt.setTypeface(fsfont);
        
		listViewItems = (ListView) _rootView.findViewById(R.id.order_lv_items);
		listItems = new ArrayList<String>();
		/* how to add item */
		adapter = new OrderListAdapter(_rootActivity, listItems);
		listViewItems.setAdapter(adapter);
//		get orders
		String buffer = new FileOperator().read(_rootActivity.getApplicationInfo().dataDir + "/order.txt");
		String[] splt_buf = null;
		if (buffer != null && buffer.isEmpty() == false)
			splt_buf = buffer.split("\t");
		if (splt_buf != null)
			for (String s : splt_buf)
				if (s.isEmpty() == false)
					appendItem(_rootActivity, s);
		WebView webView = (WebView) _rootView.findViewById(R.id.order_imageWebView);
		webView.loadUrl("file:///android_asset/gifwanbao.gif"); 
	}
	
	private void appendItem(final Activity _rootActivity, final String _item) {
		_rootActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listItems.add(_item);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
}
