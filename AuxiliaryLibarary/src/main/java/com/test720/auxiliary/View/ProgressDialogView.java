package com.test720.auxiliary.View;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.test720.auxiliary.R;


public class ProgressDialogView {

	Context mContext;
	View view;
	TextView dialogText;
	Dialog progressBar;

	// DismissListener mDismissListener = null;

	public void ProgressDialogView(Context context) {
		// mDismissListener = null;
		mContext = context;
		view = LayoutInflater.from(mContext).inflate(
				R.layout.progress_bar_style, null);
		dialogText = (TextView) view.findViewById(R.id.progress_dialog_text);

		progressBar = new Dialog(mContext, R.style.messagebox_style);
		progressBar.setContentView(view);
		progressBar.setCanceledOnTouchOutside(false);
		show();

	}

	public ProgressDialogView setText(String text) {
		dialogText.setText(text);
		return this;
	}

	/*
	 * public ProgressDialogView setOnDissmissListener(DismissListener
	 * listener){ mDismissListener = listener; return this; } public interface
	 * DismissListener { void onDismiss(DialogInterface dialog); }
	 */
	public ProgressDialogView setCancelable(boolean b){
		progressBar.setCanceledOnTouchOutside(b);
		progressBar.setCancelable(b);
		return this;
	}
	public void show() {
		progressBar.show();
	}

	public boolean isShowing() {
		return progressBar.isShowing();

	}

	public void dismiss() {
		if (progressBar.isShowing()) {
			progressBar.dismiss();
		}
	}


}
