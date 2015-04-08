package com.shiva.dtmf.fragments;

import android.content.res.Configuration;
import android.inputmethodservice.Keyboard;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.shiva.dtmf.R;
import com.shiva.dtmf.utils.encode.BasicOnKeyboardActionListener;
import com.shiva.dtmf.utils.encode.OnKeyPressed;
import com.shiva.dtmf.view.DTMFCustomKeyboardView;

public class EncodeFragment extends SherlockFragment {
	private Keyboard mKeyboard;
	private DTMFCustomKeyboardView mKeyboardView;
	private TextView encodeTextView;
	private OnKeyPressed onKeyPressed;
	private HandlerThread handlerThread;
	private Handler handler;
	private static final int INVALID_DTMF_CONSTANT = -1;
	private ToneGenerator tone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_encode, container, false);
		handlerThread = new HandlerThread("TonesHandler");
		handlerThread.start();

		tone = new ToneGenerator(AudioManager.STREAM_DTMF,
				ToneGenerator.MAX_VOLUME);
		encodeTextView = (TextView) view
				.findViewById(R.id.fragment_encode_encode);
		mKeyboard = new Keyboard(getActivity(), R.xml.keyboard_dtmf);
		mKeyboardView = (DTMFCustomKeyboardView) view
				.findViewById(R.id.keyboard_view);
		mKeyboardView.setKeyboard(mKeyboard);
		initializeListener();
		mKeyboardView
		.setOnKeyboardActionListener(new BasicOnKeyboardActionListener(
				getActivity(), onKeyPressed));
		return view;
	}
	
	public void inValidateKeyboardView()
	{
		mKeyboardView.setVisibility(View.VISIBLE);
	}

	private void initializeListener() {

		handler = new Handler(handlerThread.getLooper());
		onKeyPressed = new OnKeyPressed() {

			private int dtmfConstant;
			private String encodedText="";

			@Override
			public void onKeyPressed(final int primaryKeyCode) {
				handler.post(new Runnable() {

					@Override
					public void run() {
						try {
							dtmfConstant = getDTMFConstatnt(primaryKeyCode);
							encodedText += String
									.valueOf((char) primaryKeyCode);
							updateUIText(encodedText);
							if (dtmfConstant != INVALID_DTMF_CONSTANT
									&& tone.startTone(dtmfConstant, 200))
								Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});

			}
		};
	}

	protected void updateUIText(final String encodedText) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				try {
					encodeTextView.setText(encodedText);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	private int getDTMFConstatnt(int primaryKeyCode) {
		int dtmfConstant;
		switch (primaryKeyCode) {
		case '0':
			dtmfConstant = ToneGenerator.TONE_DTMF_0;
			break;
		case '1':
			dtmfConstant = ToneGenerator.TONE_DTMF_1;
			break;
		case '2':
			dtmfConstant = ToneGenerator.TONE_DTMF_2;
			break;
		case '3':
			dtmfConstant = ToneGenerator.TONE_DTMF_3;
			break;
		case '4':
			dtmfConstant = ToneGenerator.TONE_DTMF_4;
			break;
		case '5':
			dtmfConstant = ToneGenerator.TONE_DTMF_5;
			break;
		case '6':
			dtmfConstant = ToneGenerator.TONE_DTMF_6;
			break;
		case '7':
			dtmfConstant = ToneGenerator.TONE_DTMF_7;
			break;
		case '8':
			dtmfConstant = ToneGenerator.TONE_DTMF_8;
			break;
		case '9':
			dtmfConstant = ToneGenerator.TONE_DTMF_9;
			break;
		case '*':
			dtmfConstant = ToneGenerator.TONE_DTMF_S;
			break;
		case '#':
			dtmfConstant = ToneGenerator.TONE_DTMF_P;
			break;
		case 'A':
			dtmfConstant = ToneGenerator.TONE_DTMF_A;
			break;
		case 'B':
			dtmfConstant = ToneGenerator.TONE_DTMF_B;
			break;
		case 'C':
			dtmfConstant = ToneGenerator.TONE_DTMF_C;
			break;
		case 'D':
			dtmfConstant = ToneGenerator.TONE_DTMF_D;
			break;
		default:
			dtmfConstant = INVALID_DTMF_CONSTANT;
			break;
		}
		return dtmfConstant;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		showKeyboardWithAnimation();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		handlerThread.quit();
	}

	private void showKeyboardWithAnimation() {
		if (mKeyboardView.getVisibility() == View.GONE) {
			mKeyboardView.setVisibility(View.VISIBLE);
			Animation animation = AnimationUtils.loadAnimation(getActivity(),
					R.anim.slide_in_bottom);
			mKeyboardView.startAnimation(animation);
		}
	}
}
