package com.fourthwardcoder.android.convertthis;
import java.util.ArrayList;
import java.util.List;

import com.fourthwardcoder.android.tabexample.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

/**
 * ConvertThisActivity
 * 
 * Main Activity of the ConvertThis utility. This sets up the navigation tabs. 
 * All other operatoins are done in the ConvertThisFragment
 * @author chare
 *
 */
public class ConvertThisActivity extends Activity {

	/*********************************************************************/
	/*                         Local Data                                */
	/*********************************************************************/
	ArrayList<Fragment> fragList = new ArrayList<Fragment>();
	Fragment fragment = null;
	ConvertThisFragment tabFragment = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.activity_tab_test);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			ActionBar actionBar = getActionBar();

			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			ActionBar.TabListener tabListener = new ActionBar.TabListener() {

				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {

					if (fragList.size() > tab.getPosition()) {
						ft.remove(fragList.get(tab.getPosition()));
					}

				}

				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					System.out.println("Tab pos: " + tab.getPosition());

					//Already created the fragment, just grab it
					if(fragList.size() > tab.getPosition())
						fragList.get(tab.getPosition());

					if(fragment == null) {
						tabFragment = new ConvertThisFragment();
						Bundle data = new Bundle();
						data.putInt("idx", tab.getPosition());
						tabFragment.setArguments(data);
						fragList.add(tabFragment);

					}
					else
						tabFragment  = (ConvertThisFragment)fragment;

					ft.replace(R.id.fragment_container, tabFragment);

				}

				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub

				}
			};

			actionBar.addTab(actionBar.newTab().setText(R.string.Weight).setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText(R.string.Length).setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText(R.string.Volume).setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText(R.string.Temperature).setTabListener(tabListener));

		}

	}
}
