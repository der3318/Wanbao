package com.g7.wanbao;

import java.util.Locale;

import com.g7.wanbao.csmuse.CSmuseServerManager;
import com.g7.wanbao.font.TypeFaceProvider;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private static CSmuseServerManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		
		manager = CSmuseServerManager.getInstance(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		manager.cancelAllRequests();
		
	}

	@SuppressLint("InflateParams")
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		int backStackCount = getFragmentManager().getBackStackEntryCount();
		for (int i = 0; i < backStackCount; i++) {

			// Get the back stack fragment id.
			int backStackId = getFragmentManager().getBackStackEntryAt(i).getId();

			fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

		} /* end of for */
		fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
				.commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section2);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			break;
		}
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		Typeface face = TypeFaceProvider.getTypeFace(this, "fonts/fangsong.ttf");
		yourTextView.setTypeface(face, face.BOLD);
		yourTextView.setTextSize(30);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		restoreActionBar();
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			// getMenuInflater().inflate(R.menu.main, menu);
			// restoreActionBar();
			return true;
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_search) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(6)).commit();
			mTitle = getString(R.string.title_section6);
			restoreActionBar();
		}
		if (id == R.id.action_ch)	setLocale("");
		if (id == R.id.action_tw)	setLocale("tw");
		return super.onOptionsItemSelected(item);
	}
	
	public void setLocale(String lang) { 
		Locale myLocale = new Locale(lang); 
	    Resources res = getResources(); 
	    DisplayMetrics dm = res.getDisplayMetrics(); 
	    Configuration conf = res.getConfiguration(); 
	    conf.locale = myLocale; 
	    res.updateConfiguration(conf, dm); 
	    Intent refresh = new Intent(this, MainActivity.class); 
	    startActivity(refresh); 
	    finish();
	} 

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		private static View rootView;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			Bundle args = this.getArguments();
			int sectionNumber = args.getInt(ARG_SECTION_NUMBER);
			rootView = inflater.inflate(R.layout.fragment_main, container, false);
			if (sectionNumber == 1) {
				rootView = inflater.inflate(R.layout.fragment_home, container, false);
				new HomeHelper().setup(this.getActivity(), rootView);
			}
			else if(sectionNumber == 2) {
				rootView = inflater.inflate(R.layout.fragment_home, container, false);
				new HomeHelper().setup(this.getActivity(), rootView);
			}
			else if(sectionNumber == 3) {
				rootView = inflater.inflate(R.layout.fragment_cart, container, false);
				new CartHelper().setup(this.getActivity(), rootView);
			} else if (sectionNumber == 4) {
				rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
				new FavoriteHelper().setup(this.getActivity(), rootView);
			} else if (sectionNumber == 5) {
				rootView = inflater.inflate(R.layout.fragment_order_search, container, false);
				new OrderSearchHelper().setup(this.getActivity(), rootView);
			} else if (sectionNumber == 6) { // Search
				rootView = inflater.inflate(R.layout.fragment_search, container, false);
				new SearchHelper().setup(this.getActivity(), rootView);
			} else {
				rootView = inflater.inflate(R.layout.fragment_account, container, false);
				new AccountHelper().setup(this.getActivity(), rootView);
			}
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			rootView = null;
			System.runFinalization();
			Runtime.getRuntime().gc();
			System.gc();
		}
	}

}
