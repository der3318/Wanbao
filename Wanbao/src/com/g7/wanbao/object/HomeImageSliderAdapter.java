package com.g7.wanbao.object;

import java.io.InputStream;

import com.g7.wanbao.DetailActivity;
import com.g7.wanbao.R;
import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.local.Data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeImageSliderAdapter extends PagerAdapter {

	Context context;
	// int imageDark = R.drawable.home_vp_dark;
	// int[] imageId = {R.drawable.image1, R.drawable.image2, R.drawable.image3,
	// R.drawable.image4, R.drawable.image5};
	int[] itemId = { 310, 214, 141, 138, 8 };
	Typeface fsfont;

	public HomeImageSliderAdapter(Context context) {
		this.context = context;
		fsfont = TypeFaceProvider.getTypeFace(context, "fonts/fangsong.ttf");
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();

		View viewItem = inflater.inflate(R.layout.home_imageslider_item, container, false);
		TextView tv_imageSlider = (TextView) viewItem.findViewById(R.id.home_tv_imageSlider);
		tv_imageSlider.setTypeface(fsfont);
		Data data = Data.getInstance((Activity) context);
		tv_imageSlider.setText(data.getItemNamefromItemID(itemId[position]));
		// ImageView iv_imageSlider = (ImageView)
		// viewItem.findViewById(R.id.home_iv_imageSlider);
		// iv_imageSlider.setBackgroundResource(imageId[position]);
		new DownloadImageTask((ImageView) viewItem.findViewById(R.id.home_iv_imageSlider))
				.execute(data.getImgUrlfromItemID(itemId[position]));
		((ViewPager) container).addView(viewItem);
		viewItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("id", itemId[position]);
				intent.setClass(context, DetailActivity.class);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		return viewItem;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return imageId.length;
		return itemId.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub

		return view == ((View) object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView((View) object);
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

		@SuppressWarnings("deprecation")
		protected void onPostExecute(Bitmap result) {
			BitmapDrawable background = new BitmapDrawable(result);
			bmImage.setBackgroundDrawable(background);
		}
	}

}
