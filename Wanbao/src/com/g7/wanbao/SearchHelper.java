package com.g7.wanbao;

import java.util.ArrayList;
import java.util.List;

import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.local.Data;
import com.g7.wanbao.object.Item;
import com.g7.wanbao.object.SearchListAdapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchHelper implements SectionHelper {
	
//	adapter
	SearchListAdapter adapter;
	List<Item> listItems;
	ListView listViewItems;

//	fonts
	private Typeface fsfont;
	private Typeface ldfont;
	
	public void setup(final Activity _rootActivity, View _rootView) {
		fsfont = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/fangsong.ttf");
        ldfont = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/lingdian.ttf");
//		find view
		final TextView tv_keyword = (TextView) _rootView.findViewById(R.id.search_tv_keyword);
		tv_keyword.setTypeface(fsfont);
		final EditText edtTxt_keyword = (EditText) _rootView.findViewById(R.id.search_edtTxt_keyword);
		edtTxt_keyword.setTypeface(fsfont);
		Button btn_submit = (Button) _rootView.findViewById(R.id.search_btn_submit);
		btn_submit.setTypeface(ldfont);
		listViewItems = (ListView) _rootView.findViewById(R.id.search_lv_items);
		listItems = new ArrayList<Item>();
		adapter = new SearchListAdapter(_rootActivity, listItems);
		listViewItems.setAdapter(adapter);
//		set listener
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String keyword = edtTxt_keyword.getText().toString();
				listItems.clear();
				Data data = Data.getInstance(_rootActivity);
				for(int i = Data.MIN_ITEM_ID ; i <= Data.MAX_ITEM_ID ; i++) {
					String itemName = data.getItemNamefromItemID(i);
					String compName = data.getCompNamefromCompID(data.getCompIDfromItemID(i));
					String info = data.getItemIntrofromItemID(i);
					if(itemName != null && itemName.contains(keyword))	appendItem(_rootActivity, new Item(i, data));
					else if(compName != null && compName.contains(keyword))	appendItem(_rootActivity, new Item(i, data));
					else if(info != null && info.contains(keyword))	appendItem(_rootActivity, new Item(i, data));
				}
				checkResult(_rootActivity);
			}
		});
	}
	
	private void appendItem(final Activity _rootActivity, final Item _item) {
		_rootActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listItems.add(_item);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	private void checkResult(final Activity _rootActivity) {
		_rootActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(listItems.isEmpty())	Toast.makeText(_rootActivity.getApplicationContext(), "No Corresponding Data", Toast.LENGTH_LONG).show();
			}
		});
	}
	
}
