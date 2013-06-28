package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import edu.jhu.cs.oose.fall2012.group1.jyogurt.R;

/**
 * Overlay class for customized overlay
 * @author Alan
 *
 */
public class MyOverlays extends Overlay {
	GeoPoint location = null;

	public MyOverlays(GeoPoint location)

	{
		super();

		this.location = location;
	}

	public void draw(Canvas canvas, MapView mapView, boolean shadow)

	{
		super.draw(canvas, mapView, shadow);

		// translate the screen pixels

		Point screenPoint = new Point();

		mapView.getProjection().toPixels(this.location, screenPoint);

		// add the image

		canvas.drawBitmap(BitmapFactory.decodeResource(mapView.getResources(),
				R.drawable.marker),

		screenPoint.x, screenPoint.y, null); // Setting the image location on
												// the screen (x,y).

	}

}
