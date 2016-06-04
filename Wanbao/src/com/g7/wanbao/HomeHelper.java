package com.g7.wanbao;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.object.FavoriteListAdapter;
import com.g7.wanbao.object.HomeImageSliderAdapter;
import com.g7.wanbao.object.Item;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeHelper implements SectionHelper {

	// adapter
	FavoriteListAdapter adapter;
	List<Item> listItems;
	ListView listViewItems;
	Timer swipeTimer;

	public void setup(final Activity _rootActivity, View _rootView) {
		// find view
		final ImageButton btn_class1 = (ImageButton) _rootView.findViewById(R.id.home_btn_class1);
		final ImageButton btn_class2 = (ImageButton) _rootView.findViewById(R.id.home_btn_class2);
		final ImageButton btn_class3 = (ImageButton) _rootView.findViewById(R.id.home_btn_class3);
		final ImageButton btn_class4 = (ImageButton) _rootView.findViewById(R.id.home_btn_class4);
		final ImageButton btn_class5 = (ImageButton) _rootView.findViewById(R.id.home_btn_class5);
		final ImageButton btn_class6 = (ImageButton) _rootView.findViewById(R.id.home_btn_class6);
		final ViewPager vp_imageSlider = (ViewPager) _rootView.findViewById(R.id.home_vp_imageSlider);

		// set font
		//Typeface font = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/kai.ttf");
		Typeface font = TypeFaceProvider.getTypeFace(_rootActivity, "fonts/fangsong.ttf");
		TextView custom = (TextView) _rootView.findViewById(R.id.home_tv_class_header);
		custom.setTypeface(font, font.BOLD);

		PagerAdapter vpAdapter = new HomeImageSliderAdapter(_rootActivity);
		vp_imageSlider.setAdapter(vpAdapter);

		final ImageView img_page1 = (ImageView) _rootView.findViewById(R.id.home_iv_bullet1);
		final ImageView img_page2 = (ImageView) _rootView.findViewById(R.id.home_iv_bullet2);
		final ImageView img_page3 = (ImageView) _rootView.findViewById(R.id.home_iv_bullet3);
		final ImageView img_page4 = (ImageView) _rootView.findViewById(R.id.home_iv_bullet4);
		final ImageView img_page5 = (ImageView) _rootView.findViewById(R.id.home_iv_bullet5);

		// set vp listener
		vp_imageSlider.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {

				switch (position) {
				case 0:
					img_page1.setImageResource(R.drawable.home_bullet_selected);
					img_page2.setImageResource(R.drawable.home_bullet);
					img_page3.setImageResource(R.drawable.home_bullet);
					img_page4.setImageResource(R.drawable.home_bullet);
					img_page5.setImageResource(R.drawable.home_bullet);
					break;

				case 1:
					img_page1.setImageResource(R.drawable.home_bullet);
					img_page2.setImageResource(R.drawable.home_bullet_selected);
					img_page3.setImageResource(R.drawable.home_bullet);
					img_page4.setImageResource(R.drawable.home_bullet);
					img_page5.setImageResource(R.drawable.home_bullet);
					break;

				case 2:
					img_page1.setImageResource(R.drawable.home_bullet);
					img_page2.setImageResource(R.drawable.home_bullet);
					img_page3.setImageResource(R.drawable.home_bullet_selected);
					img_page4.setImageResource(R.drawable.home_bullet);
					img_page5.setImageResource(R.drawable.home_bullet);
					break;

				case 3:
					img_page1.setImageResource(R.drawable.home_bullet);
					img_page2.setImageResource(R.drawable.home_bullet);
					img_page3.setImageResource(R.drawable.home_bullet);
					img_page4.setImageResource(R.drawable.home_bullet_selected);
					img_page5.setImageResource(R.drawable.home_bullet);
					break;

				case 4:
					img_page1.setImageResource(R.drawable.home_bullet);
					img_page2.setImageResource(R.drawable.home_bullet);
					img_page3.setImageResource(R.drawable.home_bullet);
					img_page4.setImageResource(R.drawable.home_bullet);
					img_page5.setImageResource(R.drawable.home_bullet_selected);
					break;

				default:
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		// set btn listener
		btn_class1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("class", "class1");
				intent.setClass(_rootActivity, ClassActivity.class);
				intent.putExtras(bundle);
				_rootActivity.startActivity(intent);
			}
		});
		btn_class2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("class", "class2");
				intent.setClass(_rootActivity, ClassActivity.class);
				intent.putExtras(bundle);
				_rootActivity.startActivity(intent);
			}
		});
		btn_class3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("class", "class3");
				intent.setClass(_rootActivity, ClassActivity.class);
				intent.putExtras(bundle);
				_rootActivity.startActivity(intent);
			}
		});
		btn_class4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("class", "class4");
				intent.setClass(_rootActivity, ClassActivity.class);
				intent.putExtras(bundle);
				_rootActivity.startActivity(intent);
			}
		});
		btn_class5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("class", "class5");
				intent.setClass(_rootActivity, ClassActivity.class);
				intent.putExtras(bundle);
				_rootActivity.startActivity(intent);
			}
		});
		btn_class6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("class", "class6");
				intent.setClass(_rootActivity, ClassActivity.class);
				intent.putExtras(bundle);
				_rootActivity.startActivity(intent);
			}
		});

		final Handler handler = new Handler();
		final Runnable Update = new Runnable() {
			public void run() {
				if (vp_imageSlider.getCurrentItem() == 4) {
					vp_imageSlider.setCurrentItem(0);
				}
				else vp_imageSlider.setCurrentItem(vp_imageSlider.getCurrentItem() + 1, true);
			}
		};

		swipeTimer = new Timer();
		swipeTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(Update);
			}
		}, 5000, 5000);
	}

}
