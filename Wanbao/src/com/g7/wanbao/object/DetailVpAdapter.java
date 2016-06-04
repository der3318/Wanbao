package com.g7.wanbao.object;

import com.g7.wanbao.CommentFragment;
import com.g7.wanbao.InfoFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailVpAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 2;
	private String tabtitles[] = new String[] { "Item Detail", "Comments" };
	Context context;
 
	public DetailVpAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getCount() {
		return PAGE_COUNT;
	}
 
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			InfoFragment fragmenttab1 = new InfoFragment();
			return fragmenttab1;
		case 1:
			CommentFragment fragmenttab2 = new CommentFragment();
			return fragmenttab2;
		}
		return null;
	}
 
	@Override
	public CharSequence getPageTitle(int position) {
		return tabtitles[position];
	}

}
