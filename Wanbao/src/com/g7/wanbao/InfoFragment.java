package com.g7.wanbao;

import java.io.InputStream;

import com.g7.wanbao.csmuse.CSmuseServerManager;
import com.g7.wanbao.csmuse.Language;
import com.g7.wanbao.local.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoFragment extends Fragment {
	
	TextView tv_descriptionTab, tv_description;
	
	TextView tv_detailTab;
	TextView tv_priceTab, tv_price;
	TextView tv_itemQtTab, tv_itemQt;

	TextView tv_shopInfoTab;
	TextView tv_shopNameTab, tv_shopName;
	TextView tv_shopDescription;
	
	int itemID;
	Data data;
	
//	Button btn_detail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_detail_item, container, false);
		
		itemID = this.getActivity().getIntent().getExtras().getInt("id");
//		itemID = 310;
		data = Data.getInstance(this.getActivity());
		
		Typeface fsfont = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/fangsong.ttf");
		Typeface ldfont = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/lingdian.ttf");
		
		new DownloadImageTask((ImageView) view.findViewById(R.id.detail_iv_img)).execute(data.getImgUrlfromItemID(itemID));
		tv_descriptionTab = (TextView) view.findViewById(R.id.detail_tv_itemDescriptionTab);
		tv_descriptionTab.setTypeface(fsfont);
		tv_description = (TextView) view.findViewById(R.id.detail_tv_itemDescription);
		tv_description.setTypeface(fsfont);
		tv_description.setText(data.getItemIntrofromItemID(itemID));
		tv_detailTab = (TextView) view.findViewById(R.id.detail_tv_itemDetail);
		tv_detailTab.setTypeface(fsfont);
		tv_priceTab = (TextView) view.findViewById(R.id.detail_tv_priceTab);
		tv_priceTab.setTypeface(fsfont);
		tv_price = (TextView) view.findViewById(R.id.detail_tv_price2);
		tv_price.setTypeface(ldfont);
//		tv_price.setText("$1000");
		new FetchPriceTask(tv_price).execute(itemID + "");
		tv_itemQtTab = (TextView) view.findViewById(R.id.detail_tv_itemQtTab);
		tv_itemQtTab.setTypeface(fsfont);
		tv_itemQt = (TextView) view.findViewById(R.id.detail_tv_itemQt);
		tv_itemQt.setTypeface(ldfont);
		tv_itemQt.setText("100");
		int compID = data.getCompIDfromItemID(itemID);
		tv_shopInfoTab = (TextView) view.findViewById(R.id.detail_tv_shopInfoTab);
		tv_shopInfoTab.setTypeface(fsfont);
		tv_shopName = (TextView) view.findViewById(R.id.detail_tv_shopName);
		tv_shopName.setTypeface(fsfont);
		tv_shopName.setText(data.getCompNamefromCompID(compID));
		tv_shopDescription = (TextView) view.findViewById(R.id.detail_tv_shopDescription);
		tv_shopDescription.setTypeface(fsfont);
		tv_shopDescription.setText(data.getCompIntrofromCompID(compID));
//		btn_detail = (Button) view.findViewById(R.id.detail_btn_itemDetail);
//		btn_detail.setTypeface(ldfont);
		
		return view;
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
            	CSmuseServerManager manager = CSmuseServerManager.getInstance(InfoFragment.this.getContext());
            	result = manager.getProduct(id, Language.ENG).getSellPriceCNY();
            } catch(Exception e) {
            	Log.e("Error", e.getMessage() + ", id = " + id);
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Integer result) {
        	textview.setText(InfoFragment.this.getResources().getString(R.string.money_tag) + result);
        }
    }
	
}
