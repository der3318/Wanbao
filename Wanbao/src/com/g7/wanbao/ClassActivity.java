package com.g7.wanbao;

import java.util.ArrayList;
import java.util.List;

import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.local.Data;
import com.g7.wanbao.object.Item;
import com.g7.wanbao.object.SearchListAdapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ClassActivity extends Activity {
	// Chat messages list adapter
	private SearchListAdapter adapter;
	private List<Item> listItems;
	private ImageView iv_class;
	private TextView tv_name;
	private ListView listViewItems;
	private Typeface fsfont;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class);

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Typeface dsbfont = TypeFaceProvider.getTypeFace(this, "fonts/DroidSerif-Bold.ttf");
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
		TextView ActionBarView = (TextView) findViewById(titleId);
		ActionBarView.setTypeface(dsbfont);
		
		fsfont = TypeFaceProvider.getTypeFace(this, "fonts/fangsong.ttf");

		iv_class = (ImageView) findViewById(R.id.class_iv_classImage);
		tv_name = (TextView) findViewById(R.id.class_tv_name);
		listViewItems = (ListView) findViewById(R.id.class_lv_items);

		// set interface depending on class id
		String classId = this.getIntent().getExtras().getString("class");
		int catID = 0;
		if (classId.equals("class1")) {
			iv_class.setImageResource(R.drawable.class_iv_class1);
			tv_name.setText(R.string.class_name_1);
			catID = 2;
		} else if (classId.equals("class2")) {
			iv_class.setImageResource(R.drawable.class_iv_class2);
			tv_name.setText(R.string.class_name_2);
			catID = 3;
		} else if (classId.equals("class3")) {
			iv_class.setImageResource(R.drawable.class_iv_class3);
			tv_name.setText(R.string.class_name_3);
			catID = 6;
		} else if (classId.equals("class4")) {
			iv_class.setImageResource(R.drawable.class_iv_class4);
			tv_name.setText(R.string.class_name_4);
			catID = 1;
		} else if (classId.equals("class5")) {
			iv_class.setImageResource(R.drawable.class_iv_class5);
			tv_name.setText(R.string.class_name_5);
			catID = 5;
		} else if (classId.equals("class6")) {
			iv_class.setImageResource(R.drawable.class_iv_class6);
			tv_name.setText(R.string.class_name_6);
			catID = 4;
		}
		tv_name.setTypeface(fsfont);
		listItems = new ArrayList<Item>();
		adapter = new SearchListAdapter(this, listItems);
		listViewItems.setAdapter(adapter);
		// find the corresponding items
		Data data = Data.getInstance(this);
		for(int i : data.getItemIDListfromCategory(catID))	this.appendItem(new Item(i, data));
	}

	private void appendItem(final Item _item) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listItems.add(_item);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	        finish();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

}
