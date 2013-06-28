package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.utils.Post;
import edu.jhu.cs.oose.fall2012.group1.jyogurt.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The Adapter for handing the list view
 * 
 * @author Zaoxing Liu
 * 
 */
public class ListAdapter extends ArrayAdapter<Post> {
	private final Activity context;
	private List<Post> posts;
	private AsyncImageLoader loader;
	
	public ListAdapter(Activity context,	List<Post> objects) {
		super(context,R.layout.list_row, objects);
		posts = new ArrayList<Post>();
		this.posts = objects;
		this.context = context;
		loader=new AsyncImageLoader();
	}
	/**
	 * The viewholder including all the components of an list row
	 * @author Zaoxing Liu
	 *
	 */
	public class ViewHolder
	{
		public TextView title;
		public ImageView image;
		public TextView desc;
	}
	
	/**
	 * Get the View
	 */
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.list_row, parent, false);
		    TextView title = (TextView) rowView.findViewById(R.id.txtTitle);
		    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		    TextView desc = (TextView) rowView.findViewById(R.id.txtDesc);
		    
		    title.setText(posts.get(position).getTitle());
		    desc.setText(posts.get(position).getAuthor());//please modify
		    
		    if(posts.get(position).getImageUrl() == null || posts.get(position).getImageUrl().size() == 0){
		    	imageView.setImageResource(R.drawable.taiji);
		    }else{
		    	CallbackImpl callbackImpl=new CallbackImpl(imageView);
			    Drawable Imagecache=loader.loadDrawable(posts.get(position).getImageUrl().get(0), callbackImpl);
			    if(Imagecache!=null){
		            imageView.setImageDrawable(Imagecache);
		        }
		    }
		    
		    //imageView.set(posts.get(position).getImageUrl());

		    return rowView;
	}
}
