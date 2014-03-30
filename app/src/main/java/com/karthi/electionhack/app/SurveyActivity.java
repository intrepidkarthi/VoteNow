package com.karthi.electionhack.app;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.karthi.electionhack.fragments.TabsPagerAdapter;

public class SurveyActivity extends FragmentActivity {

	private ViewPager viewPager;
	private int viewpagerid;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "FirstQuestionFragment",
			"SecondQuestionFragment", "ThirdQuestionFragment",
			"FourthQuestionFragment"};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
	
		// actionBar.setHomeButtonEnabled(false);
		// actionBar.setNavigationMode(ActionBar.);

		// Adding Tabs
		// for (String tab_name : tabs) {
		// actionBar.addTab(actionBar.newTab().setText(tab_name)
		// .setTabListener(this));
		//
		// }
	}

	
	public void onRadioButtonClicked1(View view) {
		viewPager.setCurrentItem(0);
	}

	public void onRadioButtonClicked2(View view) {
		viewPager.setCurrentItem(1);
	}

	public void onRadioButtonClicked3(View view) {
		viewPager.setCurrentItem(2);
	}

	public void onRadioButtonClicked4(View view) {
		viewPager.setCurrentItem(3);
	}

}
