package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.R;

/**
 * The Message list Adapter class
 * 
 * @author Group 1
 * 
 */
public class MessageListAdapter extends ArrayAdapter<Message> {
	private final Activity context;
	private List<Message> messages;

	public MessageListAdapter(Activity context, List<Message> objects) {
		super(context, R.layout.list_row, objects);
		messages = new ArrayList<Message>();
		this.messages = objects;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	/**
	 * The viewholder including all the components of an list row
	 * 
	 * @author Zaoxing Liu
	 * 
	 */
	public class ViewHolder {
		public TextView title;
		public TextView desc;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_row, parent, false);
		TextView title = (TextView) rowView.findViewById(R.id.txtTitle);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView desc = (TextView) rowView.findViewById(R.id.txtDesc);

		title.setText(messages.get(position).getTitle());
		desc.setText(messages.get(position).getDescription());// please modify

		imageView.setImageResource(R.drawable.taiji);

		return rowView;

	}
}
