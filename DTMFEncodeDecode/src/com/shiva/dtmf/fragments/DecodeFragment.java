package com.shiva.dtmf.fragments;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.shiva.dtmf.MainActivity;
import com.shiva.dtmf.R;
import com.shiva.dtmf.utils.decode.History;

public class DecodeFragment extends SherlockFragment {
	private Button stateButton;
	private Button clearButton;
	private EditText recognizeredEditText;
	private String recognizeredText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_decode, container, false);
		stateButton = (Button) view.findViewById(R.id.stateButton);
		stateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				((MainActivity)getActivity()).controller.changeState();
			}
		});

		clearButton = (Button) view.findViewById(R.id.clearButton);
		clearButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				((MainActivity)getActivity()).controller.clear();
			}
		});

		recognizeredEditText = (EditText) view
				.findViewById(R.id.recognizeredText);
		recognizeredEditText.setFocusable(false);

//		setEnabled(false);

		recognizeredText = "";
		return view;
	}
	
	public void start() {
		stateButton.setText(R.string.stop);
		setEnabled(true);
	}

	public void stop(History history) {
		history.add(recognizeredText);

		stateButton.setText(R.string.start);
		setEnabled(false);
	}


	public void clearText(History history) {
		history.add(recognizeredText);

		recognizeredText = "";
		recognizeredEditText.setText("");
	}

	public void addText(Character c) {
		recognizeredText += c;
		recognizeredEditText.setText(recognizeredText);
	}

	public void setText(String text) {
		recognizeredEditText.setText(text);
	}

	public void setEnabled(boolean enabled) {
		recognizeredEditText.setEnabled(enabled);
		// numKeyboard.setEnabled(enabled);
	}
	
	public void destroyMe(History history) {
		history.add(recognizeredText);
		history.save();
	}
}
