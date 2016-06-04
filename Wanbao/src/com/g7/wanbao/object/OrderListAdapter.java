package com.g7.wanbao.object;

import java.util.List;

import com.g7.wanbao.OrderActivity;
import com.g7.wanbao.R;
import com.g7.wanbao.csmuse.CSmuseServerManager;
import com.g7.wanbao.csmuse.Order;
import com.g7.wanbao.font.TypeFaceProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class OrderListAdapter extends BaseAdapter {
	 
    private Context context;
    private List<String> listItems;
    private Typeface fsfont;
	private Typeface ldfont;
 
    public OrderListAdapter(Context _context, List<String> _listItems) {
        this.context = _context;
        this.listItems = _listItems;
        fsfont = TypeFaceProvider.getTypeFace(_context, "fonts/fangsong.ttf");
        ldfont = TypeFaceProvider.getTypeFace(_context, "fonts/lingdian.ttf");
    }
 
    @Override
    public int getCount() {
        return listItems.size();
    }
 
    @Override
    public Object getItem(int _position) {
        return listItems.get(_position);
    }
 
    @Override
    public long getItemId(int _position) {
        return _position;
    }
 
    @SuppressLint("InflateParams")
	@Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
		
    	final String id = listItems.get(_position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        _convertView = mInflater.inflate(R.layout.order_list_item, null);
        TextView tv_name = (TextView) _convertView.findViewById(R.id.order_tv_name);
        tv_name.setTypeface(fsfont);
        TextView tv_per_price = (TextView) _convertView.findViewById(R.id.order_tv_per_price);
        tv_per_price.setTypeface(ldfont);
        TextView tv_amount = (TextView) _convertView.findViewById(R.id.order_tv_amount);
        tv_amount.setTypeface(ldfont);
        TextView tv_priceTab = (TextView) _convertView.findViewById(R.id.order_tv_priceTab);
        tv_priceTab.setTypeface(fsfont);
        TextView tv_price = (TextView) _convertView.findViewById(R.id.order_tv_price);
        tv_price.setTypeface(ldfont);
        //tv_price.setTypeface(ldfont);
        Button btn_hint = (Button) _convertView.findViewById(R.id.order_btn_hint);
        btn_hint.setTypeface(ldfont);

        btn_hint.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Intent intent = new Intent();
        		Bundle bundle = new Bundle();
				bundle.putString("id", id);
				intent.setClass(context, OrderActivity.class);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
        });
//        tv_name.setText(o.getItemName());
//        tv_per_price.setText("$" + i.getPrice());
//        tv_amount.setText("x" + i.getAmount());
//        tv_price.setText("$" + i.getTotalPrice());
        new FetchOrderTask(tv_name, tv_per_price, tv_amount, tv_price).execute(id);
        
        return _convertView;
    }
    
    private class FetchOrderTask extends AsyncTask<String, Void, Order> {
    	TextView name, perPrice, amount, price;

        public FetchOrderTask(TextView _name, TextView _perPrice, TextView _amount, TextView _price) {
            this.name = _name;
            this.perPrice = _perPrice;
            this.amount = _amount;
            this.price = _price;
        }

        protected Order doInBackground(String... params) {
        	Order order = null;
            try {
            	CSmuseServerManager manager = CSmuseServerManager.getInstance(context);
            	order = manager.getOrder(params[0]);
            } catch(Exception e) {
            	Log.e("Error", e.getMessage() + ", id = " + params[0]);
                e.printStackTrace();
            }
            return order;
        }

        protected void onPostExecute(Order order) {
        	if(order == null)	return;
        	name.setText(order.getItemName());
        	perPrice.setText(context.getResources().getString(R.string.money_tag) + order.getPrice());
        	amount.setText("x" + order.getAmount());
        	price.setText(context.getResources().getString(R.string.money_tag) + order.getPrice() * order.getAmount());
        }
    }
}
