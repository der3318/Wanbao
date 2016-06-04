package com.g7.wanbao.object;

import java.util.List;

import com.g7.wanbao.R;
import com.g7.wanbao.font.TypeFaceProvider;
import com.g7.wanbao.io.FileOperator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class DrawerItemAdapter extends ArrayAdapter<String> {
	 
    private Context context;
    private List<String> listItems;
    int layoutResID;
    Typeface fsfont;
    
    public DrawerItemAdapter(Context _context, int layoutResourceID, List<String> _listItems) {
    	super(_context, layoutResourceID, _listItems);
        this.context = _context;
        this.listItems = _listItems;
        this.layoutResID = layoutResourceID;
        fsfont = TypeFaceProvider.getTypeFace(this.getContext(), "fonts/fangsong.ttf");
    }
 
    @Override
    public int getCount() {
        return listItems.size();
    }
 
    @Override
    public String getItem(int _position) {
        return listItems.get(_position);
    }
 
    @Override
    public long getItemId(int _position) {
        return _position;
    }
 
    @SuppressLint("InflateParams")
	@Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
    	final String title = listItems.get(_position);
    	View view = _convertView;
    	if (view == null) {
    		LayoutInflater mInflater = ((Activity) context).getLayoutInflater();
    		view = mInflater.inflate(layoutResID, _parent, false);
    		view.setTag(title);
    	}
    	TextView name = (TextView) view.findViewById(R.id.name_id);
        TextView item_title = (TextView) view.findViewById(R.id.drawer_item_title);
        item_title.setTypeface(fsfont, fsfont.BOLD);
        ImageView item_icon = (ImageView) view.findViewById(R.id.drawer_item_icon);
        android.view.ViewGroup.LayoutParams layoutParams = item_icon.getLayoutParams();
        layoutParams.width = 100;
        layoutParams.height = 100;
        item_icon.setLayoutParams(layoutParams);
        
        item_title.setText(title);
        name.setVisibility(View.GONE);
        if (_position == 0) {
        	item_icon.setVisibility(View.GONE);
        	name.setVisibility(View.VISIBLE);
        	String buffer = new FileOperator().read(context.getApplicationInfo().dataDir + "/username.txt");
			if (buffer != null && buffer.isEmpty() == false)	name.setText(buffer + " " + context.getResources().getString(R.string.edit));
			else	name.setText(context.getResources().getString(R.string.default_username)+ " " + context.getResources().getString(R.string.edit));
        	name.setTypeface(fsfont);
			// set name for name
        	view.setOnClickListener(new OnClickListener() {
        		@Override
				public void onClick(View v) {
					LayoutInflater li = LayoutInflater.from(context);
					View promptsView = li.inflate(R.layout.prompt_name, null);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder.setView(promptsView);
					final EditText userInput = (EditText) promptsView.findViewById(R.id.name_edtTxt_user);
					String buffer = new FileOperator().read(context.getApplicationInfo().dataDir + "/username.txt");
					if (buffer != null && buffer.isEmpty() == false)	userInput.setText(buffer);
					alertDialogBuilder.setCancelable(false)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							String username = userInput.getText().toString().trim();
							if (username == null || username.isEmpty())	return;
							new FileOperator().write(context.getApplicationInfo().dataDir + "/username.txt", username);
							DrawerItemAdapter.this.notifyDataSetChanged();
						}
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			});
        }
        else if(_position == 1) {
        	item_icon.setImageResource(R.drawable.ic_star_lightbrown_18dp);
        }
        else if(_position == 2) {
        	item_icon.setImageResource(R.drawable.ic_shopping_cart_lightbrown_24dp);
        }
        else if(_position == 3) {
        	item_icon.setImageResource(R.drawable.ic_favorite_brown_24dp);
        }
        else if(_position == 4) {
        	item_icon.setImageResource(R.drawable.ic_assignment_lightbrown_24dp);
        }
        
        return view;
    }
}
