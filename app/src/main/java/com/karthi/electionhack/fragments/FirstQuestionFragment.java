package com.karthi.electionhack.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthi.electionhack.app.R;

public class FirstQuestionFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_first, container, false);
		newInstance(getTag());
		return rootView;	
	}

	public static int newInstance(String string) {
		// TODO Auto-generated method stub
		FirstQuestionFragment f = new FirstQuestionFragment();
		System.out.println(f.getId());
		return f.getId();
	}
	
}
