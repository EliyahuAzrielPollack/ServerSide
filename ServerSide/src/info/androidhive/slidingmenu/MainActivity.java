package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.util.ArrayList;

import DB_management.BF;
import DB_management.DB_api;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

public final class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerLinearLayout;//**
	private ListView mDrawerList;
	private ListView mDrawerList2;//**
	private ArrayList<NavDrawerItem> anchorsLabels;
	private ArrayList<String> anchorsCodes;
	private ActionBarDrawerToggle mDrawerToggle;
	private String prayerName;//����� �� ������ �������

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter_external_list;
	private NavDrawerListAdapter adapter_internal_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		

		
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
	   // actionBar.setBackgroundDrawable(new ColorDrawable(0xFF160203));
		//addPreferencesFromResource(R.xml.preferences);
		mTitle = getTitle();
		mDrawerTitle = "����� ����";
		
		//load the nusah of the prayer
		prayerName = "arvit_a";

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLinearLayout = (LinearLayout) findViewById(R.id.drawer_Linearlayout);//**
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		mDrawerList2 = (ListView) findViewById(R.id.list_slidermenu2);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		//navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
		
		anchorsLabels = new ArrayList<NavDrawerItem>(); 

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		mDrawerList2.setOnItemClickListener(new SlideMenu_2_ClickListener());

		// setting the nav drawer list adapter
		adapter_external_list = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems,false);
		adapter_internal_list = new NavDrawerListAdapter(getApplicationContext(),
				anchorsLabels,true);
		mDrawerList.setAdapter(adapter_external_list);
		mDrawerList2.setAdapter(adapter_internal_list);//**

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
		


		
		
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}
	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenu_2_ClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView_2(position, prayerName);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinearLayout);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new ShaharitFragment();
			anchorsLabels = BF.getInstance(this).getAnchorsLabels("shaharit_a", "id2");
			anchorsCodes = BF.getInstance(this).getAnchorsCodes("shaharit_a", "id2");
			adapter_internal_list = new NavDrawerListAdapter(getApplicationContext(),
					anchorsLabels,true);
			mDrawerList2.setAdapter(adapter_internal_list);
			break;
		case 1:
			fragment = new MinhaFragment();
			anchorsLabels = BF.getInstance(this).getAnchorsLabels("minha_a", "id3");
			anchorsCodes = BF.getInstance(this).getAnchorsCodes("minha_a", "id3");
			adapter_internal_list = new NavDrawerListAdapter(getApplicationContext(),
					anchorsLabels,true);
			mDrawerList2.setAdapter(adapter_internal_list);
			break;
		case 2:
			fragment = new ArvitFragment(-1);
			anchorsLabels = BF.getInstance(this).getAnchorsLabels("arvit_a", "id1");
			anchorsCodes = BF.getInstance(this).getAnchorsCodes("arvit_a", "id1");
			adapter_internal_list = new NavDrawerListAdapter(getApplicationContext(),
					anchorsLabels,true);
			mDrawerList2.setAdapter(adapter_internal_list);
			break;
		case 3:
			fragment = new SettingFragment();
			anchorsLabels.clear();
			adapter_internal_list = new NavDrawerListAdapter(getApplicationContext(),
					anchorsLabels,true);
			mDrawerList2.setAdapter(adapter_internal_list);
			break;
		case 4:
//			fragment = new SettingFragment();
//			anchorsLabels.clear();
//			adapter_internal_list = new NavDrawerListAdapter(getApplicationContext(),
//					anchorsLabels,true);
//			mDrawerList2.setAdapter(adapter_internal_list);
	//		Intent intent = new Intent(this, PageActivity .class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		//	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//		startActivity(intent);
			
			
			
			
			break;
			
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerLinearLayout);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	/**
	 * Diplaying fragment view - at some jump point -  for selected nav drawer list item
	 * */
	private void displayView_2(int position, String prayerName ){
		Fragment fragment = null;
		if(prayerName == "shaharit_a" || prayerName == "shaharit_e" || prayerName == "shaharit_s"){

		}
		else if(prayerName == "minha_a" || prayerName == "minha_e" || prayerName == "minha_s"){
			
		}
        else if(prayerName == "arvit_a" || prayerName == "arvit_e" || prayerName == "arvit_s" ){
        	fragment = new ArvitFragment(position);
		}
		
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerLinearLayout);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	
	
	
	
	
	
	
}
