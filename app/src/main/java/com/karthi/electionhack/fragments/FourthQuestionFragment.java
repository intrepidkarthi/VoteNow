package com.karthi.electionhack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.karthi.electionhack.app.HeatMapActivity;
import com.karthi.electionhack.app.R;

public class FourthQuestionFragment extends Fragment {
	Button mButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_fourth, container,
				false);

		mButton = (Button) rootView.findViewById(R.id.button1);
		mButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

                Intent newIntent = new Intent(getActivity(), HeatMapActivity.class);
                startActivity(newIntent);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


            }
		});
		return rootView;

	}
}
