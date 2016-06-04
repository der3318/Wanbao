package com.g7.wanbao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.io.FileOperator;
import com.g7.wanbao.object.Message;
import com.g7.wanbao.object.MessageListAdapter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class CommentFragment extends Fragment {
	
	private ImageButton ib_send;
	private EditText edtTxt_msg;
	private TextView tv_makeComment;
	private ListView listViewMessages;
	private MessageListAdapter adapter;
	private List<Message> listMessages;
	private int num, itemID;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_detail_comments, container, false);
		itemID = this.getActivity().getIntent().getExtras().getInt("id");
		Typeface fsfont = TypeFaceProvider.getTypeFace(this.getContext(), "fonts/fangsong.ttf");
		
		((TextView)view.findViewById(R.id.commentLoading_tv_loading)).setTypeface(fsfont);
		tv_makeComment = (TextView) view.findViewById(R.id.detail_tv_makeComment);
		tv_makeComment.setTypeface(fsfont);
		ib_send = (ImageButton) view.findViewById(R.id.comment_ib_send);
		edtTxt_msg = (EditText) view.findViewById(R.id.comment_edtTxt_msg);
		edtTxt_msg.setTypeface(fsfont);
		listViewMessages = (ListView) view.findViewById(R.id.comment_lv_comments);
		listMessages = new ArrayList<Message>();
		adapter = new MessageListAdapter(this.getContext(), listMessages);
		listViewMessages.setAdapter(adapter);
		
		ib_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String data = edtTxt_msg.getText().toString();
				if(data.isEmpty() == false)	sendMessage(data);
				edtTxt_msg.setText("");
			}
		});
		startFindingMessages();
		
		return view;
	}
	
	public void sendMessage(String _data) {
		String user;
		String buffer = new FileOperator().read(this.getContext().getApplicationInfo().dataDir + "/username.txt");
		if (buffer != null && buffer.isEmpty() == false)	user = buffer;
		else	user = getResources().getString(R.string.default_username);
		(new NewMessage()).execute(itemID + "", user + "#tab#" + _data);
	}
	
	public void startFindingMessages() {
		listMessages.clear();
		num = 1;
		findMessage(num);
	}
	
	public void findMessage(int _num) {
		(new ViewMessage()).execute(itemID + "", _num + "");
	}
	
	class NewMessage extends AsyncTask<String, Void, Void> {
		private String buf;
		@Override
	    protected Void doInBackground(String... params) {
	        HttpClient httpClient = new DefaultHttpClient();
	        try {
	        	buf = params[1];
	            HttpPost request = new HttpPost("https://wanbaoweb.herokuapp.com/comments/m_create");
	            StringEntity se = new StringEntity("{ \"title\":\"" + params[0] + "\", \"text\":\"" + params[1] + "\"}", HTTP.UTF_8);
	            request.addHeader("Content-Type", "application/json");
	            request.setEntity(se);
	            httpClient.execute(request);
	        } catch (Exception e) {
	            Log.d("GetCode", "Request exception:" + e.getMessage());
	        } finally {
	            httpClient.getConnectionManager().shutdown();
	        }
	        return null;
	    }
	    @SuppressLint("SimpleDateFormat")
		@Override
	    protected void onPostExecute(Void v) {
	    	if(buf == null)	return;
            String[] buf_splt = buf.split("#tab#");
            listMessages.add(0, new Message(buf_splt[1], buf_splt[0], new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
            adapter.notifyDataSetChanged();
	    }
	}
	
    class ViewMessage extends AsyncTask<String, Void, Void> {
    	private String buf, buf_t;
		@Override
        protected Void doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            try {
                HttpPost request = new HttpPost("https://wanbaoweb.herokuapp.com/comments/m_view");
                StringEntity se = new StringEntity("{ \"title\":\"" + params[0] + "\", \"num\":\"" + params[1] + "\"}", HTTP.UTF_8);
                request.addHeader("Content-Type", "application/json");
                request.setEntity(se);
                HttpResponse response = httpClient.execute(request);
                ResponseHandler<String> handler = new BasicResponseHandler();
                String httpResponse = handler.handleResponse(response);
                JSONObject responseJSON = new JSONObject(httpResponse);
                if (responseJSON.getString("text").toString().equals("fail") == false) {
                	buf = responseJSON.getString("text").toString();
                	buf_t = responseJSON.getString("time").toString().substring(0, 10);
                } else {
                    buf = null;
                }
            } catch (Exception e) {
                Log.d("Exception", "Request exception:" + e.getMessage());
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
        	setListViewHeightBasedOnChildren(listViewMessages);
            if(buf == null) {
            	view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            	return;
            }
            String[] buf_splt = buf.split("#tab#");
            listMessages.add(new Message(buf_splt[1], buf_splt[0], buf_t));
            adapter.notifyDataSetChanged();
            findMessage(++num);
        }
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
    }
	
	
	
}
