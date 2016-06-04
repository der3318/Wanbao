package com.g7.wanbao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.g7.wanbao.csmuse.CSmuseServerManager;
import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.io.FileOperator;
import com.unionpaysdk.main.IPaymentCallback;
import com.unionpaysdk.main.UnionPaySDK;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutActivity extends Activity {

	private Typeface fsfont, ldfont, wqfont;
	// TextView tv_buyerHeader;
	EditText edtTxt_buyerName, edtTxt_buyerAddress, edtTxt_buyerEmail, edtTxt_buyerPhone;
	EditText edtTxt_receiverName, edtTxt_receiverAddress, edtTxt_receiverEmail, edtTxt_receiverPhone;
	EditText edtTxt_note;
	CheckBox cb_same;
	Button btn_cancel, btn_continue;
	private UnionPaySDK unionPaySDK = null;
	private String orderid;
	private ArrayList<Integer> params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);

		orderid = this.getIntent().getExtras().getString("orderid");
		params = this.getIntent().getExtras().getIntegerArrayList("params");
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		fsfont = TypeFaceProvider.getTypeFace(this, "fonts/fangsong.ttf");
		ldfont = TypeFaceProvider.getTypeFace(this, "fonts/lingdian.ttf");
		wqfont = TypeFaceProvider.getTypeFace(this, "fonts/wqmicrohei.ttf");

		TextView ActionBarView = (TextView) findViewById(
				getResources().getIdentifier("action_bar_title", "id", "android"));
		getActionBar().setTitle(getResources().getString(R.string.checkout_header));
		ActionBarView.setTypeface(fsfont);

		// set typeface for tabs
		((TextView) findViewById(R.id.checkout_tv_buyerHeader)).setTypeface(fsfont);
		((TextView) findViewById(R.id.checkout_tv_buyerNameTab)).setTypeface(fsfont);
		((TextView) findViewById(R.id.checkout_tv_buyerAddressTab)).setTypeface(fsfont);
		((TextView) findViewById(R.id.checkout_tv_buyerEmailTab)).setTypeface(fsfont);
		((TextView) findViewById(R.id.checkout_tv_buyerPhoneTab)).setTypeface(fsfont);

		// set typeface for edittxt
		edtTxt_buyerName = (EditText) findViewById(R.id.checkout_edtTxt_buyerName);
		edtTxt_buyerName.setTypeface(fsfont);
		edtTxt_buyerAddress = (EditText) findViewById(R.id.checkout_edtTxt_buyerAddress);
		edtTxt_buyerAddress.setTypeface(fsfont);
		edtTxt_buyerEmail = (EditText) findViewById(R.id.checkout_edtTxt_buyerEmail);
		edtTxt_buyerEmail.setTypeface(ldfont);
		edtTxt_buyerPhone = (EditText) findViewById(R.id.checkout_edtTxt_buyerPhone);
		edtTxt_buyerPhone.setTypeface(ldfont);

		// set typeface for tabs
		((TextView) findViewById(R.id.checkout_tv_receiverHeader)).setTypeface(fsfont);
		((TextView) findViewById(R.id.checkout_tv_receiverNameTab)).setTypeface(fsfont);
		((TextView) findViewById(R.id.checkout_tv_receiverAddressTab)).setTypeface(fsfont);
		((TextView) findViewById(R.id.checkout_tv_receiverEmailTab)).setTypeface(fsfont);
		((TextView) findViewById(R.id.checkout_tv_receiverPhoneTab)).setTypeface(fsfont);

		// set typeface for edittxt
		edtTxt_receiverName = (EditText) findViewById(R.id.checkout_edtTxt_receiverName);
		edtTxt_receiverName.setTypeface(fsfont);
		edtTxt_receiverAddress = (EditText) findViewById(R.id.checkout_edtTxt_receiverAddress);
		edtTxt_receiverAddress.setTypeface(fsfont);
		edtTxt_receiverEmail = (EditText) findViewById(R.id.checkout_edtTxt_receiverEmail);
		edtTxt_receiverEmail.setTypeface(ldfont);
		edtTxt_receiverPhone = (EditText) findViewById(R.id.checkout_edtTxt_receiverPhone);
		edtTxt_receiverPhone.setTypeface(ldfont);

		((TextView) findViewById(R.id.checkout_tv_noteHeader)).setTypeface(fsfont);
		edtTxt_note = (EditText) findViewById(R.id.checkout_edtTxt_note);
		edtTxt_note.setTypeface(fsfont);

		cb_same = (CheckBox) findViewById(R.id.checkout_cb_same);
		cb_same.setTypeface(wqfont);
		cb_same.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked == true) {
					edtTxt_receiverName.setText(edtTxt_buyerName.getText().toString());
					edtTxt_receiverAddress.setText(edtTxt_buyerAddress.getText().toString());
					edtTxt_receiverEmail.setText(edtTxt_buyerEmail.getText().toString());
					edtTxt_receiverPhone.setText(edtTxt_buyerPhone.getText().toString());
				} else {
					edtTxt_receiverName.setText("");
					edtTxt_receiverAddress.setText("");
					edtTxt_receiverEmail.setText("");
					edtTxt_receiverPhone.setText("");
				}

			}
		});
		// this will be provided UnionSDK
		final String scode = "EID011601";
		// final String scode="EID000101";
		// this should be applied by CP and will be provided UnionSDK
		final String key = "AtMu3ahenA";
		// final String key="Ta#3642T3t";
		unionPaySDK = UnionPaySDK.getInstance();
		unionPaySDK.Initialize(this, scode, key, true);

		btn_cancel = (Button) findViewById(R.id.checkout_btn_cancel);
		btn_cancel.setTypeface(ldfont);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		btn_continue = (Button) findViewById(R.id.checkout_btn_continue);
		btn_continue.setTypeface(ldfont);
		btn_continue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// check if data is finish
				if (edtTxt_buyerName.getText().toString().equals("") || edtTxt_buyerAddress.getText().toString().equals("")
						|| edtTxt_buyerPhone.getText().toString().equals("")
						|| edtTxt_receiverName.getText().toString().equals("")
						|| edtTxt_receiverAddress.getText().toString().equals("")
						|| edtTxt_receiverPhone.getText().toString().equals("")) {
					Toast popup = Toast.makeText(CheckoutActivity.this.getApplicationContext(), getResources().getString(R.string.checkout_popup_undone), Toast.LENGTH_SHORT);
					popup.show();
				}

				else {

					// popup here

					/* GOTO PAYMENT */
//					String orderid = "1234567891";
					double amount = params.get(0);
					String memo = "memomemomemo";
					String payCallBackUrl = "urlurl.url";

					unionPaySDK.payOrderRequest(CheckoutActivity.this, orderid, amount, memo, payCallBackUrl,
							new IPaymentCallback() {
						@Override
						public void onOrderFinished() {
							Toast.makeText(CheckoutActivity.this.getApplicationContext(), "Order Finished", Toast.LENGTH_SHORT).show();
							new SendOrderTask().execute();
							// after the order is finished, the client should
							// immediately request the response
							// from CP's own server to get to know the result of
							// the order
						}

						@Override
						public void onOrderNotFinished() {
							// show("Order NotFinished~");
						}
					});
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class SendOrderTask extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... _params) {
        	boolean result = true;
        	try {
            	CSmuseServerManager manager = CSmuseServerManager.getInstance(CheckoutActivity.this);
            	for(int i = 1 ; i < params.size() ; i = i + 3) {
	            	Map<String, String> orderDetails = new HashMap<String, String>();
	            	orderDetails.put("ExternalOrderNo", orderid);
	            	orderDetails.put("ProductSN", params.get(i) + "");
	            	orderDetails.put("Quantity", params.get(i + 1) + "");
	            	orderDetails.put("Price", params.get(i + 2) + "");
	            	orderDetails.put("Amount", params.get(i + 1) * params.get(i + 2) + "");
	            	orderDetails.put("OrderName", edtTxt_buyerName.getText().toString());
	            	orderDetails.put("OrderAddress", edtTxt_buyerAddress.getText().toString());
	            	orderDetails.put("OrderEmail", edtTxt_buyerEmail.getText().toString());
	            	orderDetails.put("OrderPhone", edtTxt_buyerPhone.getText().toString());
	            	orderDetails.put("ConsigneeName", edtTxt_receiverName.getText().toString());
	            	orderDetails.put("ConsigneeAddress", edtTxt_receiverAddress.getText().toString());
	            	orderDetails.put("ConsigneeEmail", edtTxt_receiverEmail.getText().toString());
	            	orderDetails.put("ConsigneePhone", edtTxt_receiverPhone.getText().toString());
	            	orderDetails.put("DeliverTime", "Now");
	            	orderDetails.put("Result", "1");
	            	orderDetails.put("PaymentResult", "1");
	            	if(manager.createOrder(orderDetails).getInt("ErrorCode") != 0)	result = false;
            	}
            } catch(Exception e) {
            	Log.e("Error", e.getMessage() + ", id = " + orderid);
                e.printStackTrace();
                result = false;
            }
            return result;
        }

        protected void onPostExecute(Boolean _result) {
        	if(_result == false) {
        		Toast.makeText(CheckoutActivity.this.getApplicationContext(), "Fail to Send the Order", Toast.LENGTH_SHORT).show();
        		return;
        	}
        	String buffer = new FileOperator().read(CheckoutActivity.this.getApplicationInfo().dataDir + "/order.txt");
			if (buffer != null && buffer.isEmpty() == false) {
				buffer = buffer.replace(orderid + "\t", "");
				buffer = buffer + orderid + "\t";
			} else
				buffer = orderid + "\t";
			if (buffer != null)
				new FileOperator().write(CheckoutActivity.this.getApplicationInfo().dataDir + "/order.txt", buffer);
        }
	}
	
}
