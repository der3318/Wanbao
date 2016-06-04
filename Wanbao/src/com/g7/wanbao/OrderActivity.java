package com.g7.wanbao;

import java.util.ArrayList;
import java.util.List;

import com.g7.wanbao.csmuse.CSmuseServerManager;
import com.g7.wanbao.csmuse.Order;
import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.object.OrderDetailListAdapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class OrderActivity extends Activity {
	// Chat messages list adapter
	
	private OrderDetailListAdapter adapter;
	private List<String> names;
	private List<String> values;
	private ListView listView;
	private String id;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		id = this.getIntent().getExtras().getString("id");
		//init
		names = new ArrayList<String>();
		values = new ArrayList<String>();
		
		//set back button
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.order_title);
		Typeface fsfont = TypeFaceProvider.getTypeFace(this, "fonts/fangsong.ttf");
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
		TextView ActionBarView = (TextView) findViewById(titleId);
		ActionBarView.setTypeface(fsfont, Typeface.BOLD);
		
		//setting names
		//testing 
//		this.values.set(9,"Address Not Found");
		
		this.listView = (ListView) this.findViewById(R.id.order_detail_lv_items);
		adapter = new OrderDetailListAdapter(this, names, values);
		listView.setAdapter(adapter);
		new FetchOrderTask().execute(id);
//		find the corresponding items
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
	
    private class FetchOrderTask extends AsyncTask<String, Void, Order> {

        protected Order doInBackground(String... params) {
        	Order order = null;
            try {
            	CSmuseServerManager manager = CSmuseServerManager.getInstance(OrderActivity.this);
            	order = manager.getOrder(params[0]);
            } catch(Exception e) {
            	Log.e("Error", e.getMessage() + ", id = " + params[0]);
                e.printStackTrace();
            }
            return order;
        }

        protected void onPostExecute(Order order) {
        	String[] names_resources = getResources().getStringArray(R.array.order_names_array);
    		for (int i = 0; i < names_resources.length; i++) {
    			OrderActivity.this.names.add(names_resources[i]);
    			OrderActivity.this.values.add("Data Not Found");
    		}
    		if(order == null) {
    			OrderActivity.this.adapter.notifyDataSetChanged();
    			return;
    		}
    		if(order.getItemName() != null)	OrderActivity.this.values.set(0, order.getItemName());
    		if(order.getOrderNo() != null)	OrderActivity.this.values.set(1, order.getOrderNo());
        	if(order.getStyleA() != null)	OrderActivity.this.values.set(2, order.getStyleA());
        	OrderActivity.this.values.set(3, order.getAmount() + "");
        	OrderActivity.this.values.set(4, order.getPrice() + "");
        	OrderActivity.this.values.set(5, order.getPrice() * order.getAmount() + "");
        	if(order.getConsigneeName() != null)	OrderActivity.this.values.set(6, order.getConsigneeName());
        	if(order.getConsigneePhone() != null)	OrderActivity.this.values.set(7, order.getConsigneePhone());
        	if(order.getSendDate() != null)	OrderActivity.this.values.set(8, order.getSendDate().toString());
        	if(order.getDeliverTime() != null)	OrderActivity.this.values.set(9, order.getDeliverTime());
        	if(order.getPaymentResult() != 0)	OrderActivity.this.values.set(10, OrderActivity.this.getResources().getString(R.string.payment_status1));
        	else	OrderActivity.this.values.set(10, OrderActivity.this.getResources().getString(R.string.payment_status2));
        	if(order.getDealWithResult() != 0)	OrderActivity.this.values.set(11, OrderActivity.this.getResources().getString(R.string.order_status4));
        	else	OrderActivity.this.values.set(11, OrderActivity.this.getResources().getString(R.string.order_status1));
        	OrderActivity.this.adapter.notifyDataSetChanged();
        }
    }

}
