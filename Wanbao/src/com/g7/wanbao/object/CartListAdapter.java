package com.g7.wanbao.object;

import java.io.InputStream;
import java.util.List;

import com.g7.wanbao.DetailActivity;
import com.g7.wanbao.R;
import com.g7.wanbao.csmuse.CSmuseServerManager;
import com.g7.wanbao.csmuse.Language;
import com.g7.wanbao.font.TypeFaceProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class CartListAdapter extends BaseAdapter {
	 
    private Context context;
    private List<Item> listItems;
    private List<Boolean> listChecks;
    private List<Integer> listQts;
    private Typeface fsfont;
	private Typeface ldfont;
 
    public CartListAdapter(Context _context, List<Item> _listItems, List<Boolean> _listChecks, List<Integer> _listQts) {
        this.context = _context;
        this.listItems = _listItems;
        this.listChecks = _listChecks;
        this.listQts = _listQts;
        fsfont = TypeFaceProvider.getTypeFace(context, "fonts/fangsong.ttf");
        ldfont = TypeFaceProvider.getTypeFace(context, "fonts/lingdian.ttf");
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
		
    	final Item i = listItems.get(_position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        _convertView = mInflater.inflate(R.layout.cart_list_item, null);
        TextView tv_name = (TextView) _convertView.findViewById(R.id.cartItem_tv_name);
        tv_name.setTypeface(fsfont);
        TextView tv_price = (TextView) _convertView.findViewById(R.id.cartItem_tv_price);
        tv_price.setTypeface(ldfont);
        Button btn_detail = (Button) _convertView.findViewById(R.id.cartItem_btn_detail);
        btn_detail.setTypeface(ldfont);
        CheckBox cb_check = (CheckBox) _convertView.findViewById(R.id.cartItem_cb_check);
        ImageButton ib_plus = (ImageButton) _convertView.findViewById(R.id.cartItem_ib_plus);
        ImageButton ib_minus = (ImageButton) _convertView.findViewById(R.id.cartItem_ib_minus);
        final TextView tv_qt = (TextView) _convertView.findViewById(R.id.cartItem_tv_qt); 
        tv_qt.setTypeface(ldfont);
        btn_detail.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("id", i.getID());
				intent.setClass(context, DetailActivity.class);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
        });
        cb_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true)	listChecks.set(_position, true);
				else	listChecks.set(_position, false);
			}
        });
        ib_plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int qt = listQts.get(_position);
				if(qt < 100)	listQts.set(_position, qt + 1);
				tv_qt.setText(listQts.get(_position) + "");
			}
        });
        ib_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int qt = listQts.get(_position);
				if(qt > 1)	listQts.set(_position, qt - 1);
				tv_qt.setText(listQts.get(_position) + "");
			}
        });
        tv_qt.setText(listQts.get(_position) + "");
        tv_name.setText(i.getName());
//        tv_price.setText(context.getString(R.string.money_tag) + i.getPrice());
        new FetchPriceTask(tv_price).execute(i.getID() + "");
        if(listChecks.get(_position) == true)	cb_check.setChecked(true);
        else	cb_check.setChecked(false);
        new DownloadImageTask((ImageView) _convertView.findViewById(R.id.cartItem_iv_img)).execute(i.getImgUrl());
        return _convertView;
    }
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
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
            	CSmuseServerManager manager = CSmuseServerManager.getInstance(context);
            	result = manager.getProduct(id, Language.ENG).getSellPriceCNY();
            } catch(Exception e) {
            	Log.e("Error", e.getMessage() + ", id = " + id);
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Integer result) {
        	textview.setText(context.getResources().getString(R.string.money_tag) + result);
        }
    }
}
