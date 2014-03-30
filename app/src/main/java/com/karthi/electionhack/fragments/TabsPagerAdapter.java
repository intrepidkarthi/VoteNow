package com.karthi.electionhack.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// FirstQuestion fragment activity
			return new FirstQuestionFragment();
		case 1:
			// SecondQuestionfragment activity
			return new SecondQuestionFragment();
		case 2:
			// ThirdQuestion fragment activity
			return new ThirdQuestionFragment();
		case 3:
			// FourthQuestion fragment activity
			return new FourthQuestionFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
