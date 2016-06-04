package com.g7.wanbao.object;

import java.util.List;

import com.g7.wanbao.R;
import com.g7.wanbao.font.TypeFaceProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class MessageListAdapter extends BaseAdapter {
	 
    private Context context;
    private List<Message> listMessages;
    private Typeface fsfont, ldfont;
    private TextView tv_data, tv_name, tv_time;
    
    public MessageListAdapter(Context _context, List<Message> _listMessages) {
        this.context = _context;
        this.listMessages = _listMessages;
        fsfont = TypeFaceProvider.getTypeFace(_context, "fonts/fangsong.ttf");
        ldfont = TypeFaceProvider.getTypeFace(_context, "fonts/lingdian.ttf");
    }
 
    @Override
    public int getCount() {
        return listMessages.size();
    }
    
    @Override
    public Object getItem(int _position) {
        return listMessages.get(_position);
    }
 
    @Override
    public long getItemId(int _position) {
        return _position;
    }
 
    @SuppressLint("InflateParams")
	@Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
    	final Message m = listMessages.get(_position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        _convertView = mInflater.inflate(R.layout.message_list_item, null);
        tv_name = (TextView) _convertView.findViewById(R.id.msgItem_tv_name);
        tv_name.setTypeface(fsfont);
        tv_name.setText(m.getUser());
        
        tv_time = (TextView) _convertView.findViewById(R.id.msgItem_tv_time);
        tv_time.setTypeface(ldfont);
        tv_time.setText(m.getTime());
        
        tv_data = (TextView) _convertView.findViewById(R.id.msgItem_tv_data);
        tv_data.setText(m.getData());
        tv_data.setTypeface(fsfont);
        return _convertView;
    }
}
