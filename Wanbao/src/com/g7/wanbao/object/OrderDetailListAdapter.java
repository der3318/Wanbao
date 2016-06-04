package com.g7.wanbao.object;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.g7.wanbao.R;
import com.g7.wanbao.font.TypeFaceProvider;

@SuppressLint("ViewHolder")
public class OrderDetailListAdapter extends BaseAdapter {
	
	private List<String> names;
	private List<String> values;
    private Context context;
	private Typeface mqfont;
 
    public OrderDetailListAdapter(Context _context, List<String> _names, List<String> _values) {
        this.context = _context;
        this.names = _names;
        this.values = _values;
        mqfont = TypeFaceProvider.getTypeFace(_context, "fonts/wqmicrohei.ttf");
        //fsfont = Typeface.createFromAsset(context.getAssets(), "fonts/fangsong.ttf");
        //ldfont = Typeface.createFromAsset(context.getAssets(), "fonts/lingdian.ttf");
    }
 
    @Override
    public int getCount() {
        return names.size();
    }
 
    @Override
    public Object getItem(int _position) {
        return names.get(_position);
    }
 
    @Override
    public long getItemId(int _position) {
        return _position;
    }
 
    @SuppressLint("InflateParams")
	@Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
		final String name = names.get(_position);
		final String value = values.get(_position);

		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        _convertView = mInflater.inflate(R.layout.order_detail_item, null);
        TextView tv_name = (TextView) _convertView.findViewById(R.id.order_detail_tv_name);
        TextView tv_value = (TextView) _convertView.findViewById(R.id.order_detail_tv_value);

        
        tv_name.setText(name);
        tv_value.setText(value);
        tv_name.setTypeface(mqfont);
        tv_value.setTypeface(mqfont);
        return _convertView;
    }
}
