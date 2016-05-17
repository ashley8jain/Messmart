package com.ashleyjain.messmart.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ashleyjain.messmart.R;

public class ImageSlideAdapter extends PagerAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;

	int[] mResources = {
			R.drawable.layout,
			R.drawable.hww1_t,
			R.drawable.hww3_t,
			R.drawable.hww4_t

	};

	public ImageSlideAdapter(Context context) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mResources.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View itemView = mLayoutInflater.inflate(R.layout.vp_image, container, false);

		ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
		imageView.setImageResource(mResources[position]);

		container.addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((LinearLayout) object);
	}
}