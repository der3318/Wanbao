package com.g7.wanbao;

import java.io.File;

import com.g7.wanbao.io.FileOperator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountHelper implements SectionHelper {

	public void setup(final Activity _rootActivity, View _rootView) {
//		find view
		final EditText edtTxt_cardID = (EditText) _rootView.findViewById(R.id.account_edtTxt_cardID);
		Button btn_save = (Button) _rootView.findViewById(R.id.account_btn_save);
		Button btn_init = (Button) _rootView.findViewById(R.id.account_btn_init);
		Button btn_order = (Button) _rootView.findViewById(R.id.account_btn_order);
		String buffer = new FileOperator().read(_rootActivity.getApplicationInfo().dataDir + "/userInfo.txt");
		if(buffer != null && buffer.isEmpty() == false)	edtTxt_cardID.setText(buffer);
//		set listener
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String cardID = edtTxt_cardID.getText().toString();
				new FileOperator().write(_rootActivity.getApplicationInfo().dataDir + "/userInfo.txt", cardID);
				Toast popup = Toast.makeText(_rootActivity, "Saved", Toast.LENGTH_SHORT);
				popup.show();
			}
		});
		btn_init.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new File(_rootActivity.getApplicationInfo().dataDir + "/userInfo.txt").delete();
				edtTxt_cardID.setText("");
				Toast popup = Toast.makeText(_rootActivity, "Initialized", Toast.LENGTH_SHORT);
				popup.show();
			}
		});
		btn_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("cardID", edtTxt_cardID.getText().toString());
				intent.setClass(_rootActivity, OrderActivity.class);
				intent.putExtras(bundle);
				_rootActivity.startActivity(intent);
				Toast popup = Toast.makeText(_rootActivity, "Checking Order Status", Toast.LENGTH_SHORT);
				popup.show();
			}
		});
	}
	
}
