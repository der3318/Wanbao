package com.g7.wanbao;

import java.util.ArrayList;

import com.g7.wanbao.csmuse.CSmuseServerManager;
import com.g7.wanbao.csmuse.Language;
import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.io.FileOperator;
import com.g7.wanbao.local.Data;
import com.g7.wanbao.object.DetailVpAdapter;
import com.g7.wanbao.object.SlidingTabLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends FragmentActivity {

	ViewPager vp_pager;
	TextView tv_name, tv_price;
	Button btn_detail, btn_pay, btn_cart, btn_favorite;
	SlidingTabLayout tabs;
	int itemID, price;
	Data data;
	
	//testing
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		itemID = this.getIntent().getExtras().getInt("id");
//		itemID = 310;
		data = Data.getInstance(this);
		// set back button
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Typeface dsbfont = TypeFaceProvider.getTypeFace(this, "fonts/DroidSerif-Bold.ttf");
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
		TextView ActionBarView = (TextView) findViewById(titleId);
		ActionBarView.setTypeface(dsbfont);
		// set viewpager
		vp_pager = (ViewPager) findViewById(R.id.detail_vp_pager);
		vp_pager.setAdapter(new DetailVpAdapter(getSupportFragmentManager()));

		// set tabs
		tabs = (SlidingTabLayout) findViewById(R.id.detail_tabs);
		tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.lightbrown);
			}

			@Override
			public int getDividerColor(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
		});

		// setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(vp_pager);

		tv_name = (TextView) findViewById(R.id.detail_tv_name);
		tv_price = (TextView) findViewById(R.id.detail_tv_price);
		Typeface fsfont = TypeFaceProvider.getTypeFace(this, "fonts/fangsong.ttf");
		Typeface ldfont = TypeFaceProvider.getTypeFace(this, "fonts/lingdian.ttf");
		tv_name.setTypeface(fsfont);
		tv_price.setTypeface(ldfont);

		btn_pay = (Button) findViewById(R.id.detail_btn_pay);
		btn_pay.setTypeface(ldfont);
		btn_cart = (Button) findViewById(R.id.detail_btn_cart);
		btn_cart.setTypeface(ldfont);
		btn_favorite = (Button) findViewById(R.id.detail_btn_favorite);
		btn_favorite.setTypeface(ldfont);
		tv_name.setText(data.getItemNamefromItemID(itemID));
        
		btn_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// GO checkout
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				// set up info
				long orderid = System.currentTimeMillis();
				ArrayList<Integer> params = new ArrayList<Integer>();
				params.add(price);
				params.add(itemID);
				params.add(1);
				params.add(price);
				bundle.putString("orderid", orderid + "");
				bundle.putIntegerArrayList("params", params);				
				intent.setClass(DetailActivity.this, CheckoutActivity.class);
				intent.putExtras(bundle);
				DetailActivity.this.startActivity(intent);
			}
		});
		btn_cart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String buffer = new FileOperator().read(DetailActivity.this.getApplicationInfo().dataDir + "/cart.txt");
				if (buffer != null && buffer.isEmpty() == false) {
					buffer = buffer.replace(itemID + "\t", "");
					buffer = buffer + itemID + "\t";
				} else
					buffer = itemID + "\t";
				if (buffer != null)
					new FileOperator().write(DetailActivity.this.getApplicationInfo().dataDir + "/cart.txt", buffer);
				Toast popup = Toast.makeText(DetailActivity.this.getApplicationContext(), "Added to Shopping Cart", Toast.LENGTH_SHORT);
				popup.show();
			}
		});
		btn_favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String buffer = new FileOperator().read(DetailActivity.this.getApplicationInfo().dataDir + "/fav.txt");
				if (buffer != null && buffer.isEmpty() == false) {
					buffer = buffer.replace(itemID + "\t", "");
					buffer = buffer + itemID + "\t";
				} else
					buffer = itemID + "\t";
				if (buffer != null)
					new FileOperator().write(DetailActivity.this.getApplicationInfo().dataDir + "/fav.txt", buffer);
				Toast popup = Toast.makeText(DetailActivity.this.getApplicationContext(), "Added to Favorite", Toast.LENGTH_SHORT);
				popup.show();
			}
		});
		
//		tv_price.setText("$1000");
		new FetchPriceTask(tv_price).execute(itemID + "");

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

	private class FetchPriceTask extends AsyncTask<String, Void, Integer> {
    	TextView textview;

        public FetchPriceTask(TextView _textview) {
            this.textview = _textview;
        }

        protected Integer doInBackground(String... params) {
            int id = Integer.parseInt(params[0]);
            int result = 0;
            try {
            	CSmuseServerManager manager = CSmuseServerManager.getInstance(DetailActivity.this);
            	result = manager.getProduct(id, Language.ENG).getSellPriceCNY();
            } catch(Exception e) {
            	Log.e("Error", e.getMessage() + ", id = " + id);
                e.printStackTrace();
            }
            price = result;
            return result;
        }

        protected void onPostExecute(Integer result) {
        	textview.setText(DetailActivity.this.getResources().getString(R.string.money_tag) + result);
        	if(result != 0)	btn_pay.setClickable(true);
        }
    }

}
