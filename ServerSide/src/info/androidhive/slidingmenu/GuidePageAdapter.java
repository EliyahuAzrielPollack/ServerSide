package info.androidhive.slidingmenu;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import info.androidhive.slidingmenu.PageActivity;
public class GuidePageAdapter extends PagerAdapter {

	private ArrayList<View> pageViews;
	private Context mContext;
	private Button btn;
	 PageActivity p ;
  
	public GuidePageAdapter(ArrayList<View> pageViews, Context mContext ,PageActivity p  ) {
		super();
		this.pageViews = pageViews;
		this.mContext = mContext;
		this.p=p;
	}

	@Override
	public Object instantiateItem(View v, int position) {
	
		switch (position) {
		case 0:
			RadioButton rad = (RadioButton) pageViews.get(position).findViewById(R.id.Button1022);
//			btn = (Button) pageViews.get(position).findViewById(R.id.button1);
 			rad.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
	            	p.viewPager.setCurrentItem(0, true); 
				}
			});
			break;
		case 1:
			
			
			 rad = (RadioButton) pageViews.get(position).findViewById(R.id.Button1022);
//			btn = (Button) pageViews.get(position).findViewById(R.id.button1);
 			rad.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
	            	p.viewPager.setCurrentItem(3, true); 
				}
			});
			break;
		case 2:
			 rad = (RadioButton) pageViews.get(position).findViewById(R.id.Button1022);
//			btn = (Button) pageViews.get(position).findViewById(R.id.button1);
 			rad.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
	            	p.viewPager.setCurrentItem(2, true); 
				}
			});
			break;
		case 3:
			 rad = (RadioButton) pageViews.get(position).findViewById(R.id.Button1022);
//			btn = (Button) pageViews.get(position).findViewById(R.id.button1);
 			rad.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
	            	p.viewPager.setCurrentItem(1, true); 
				}
			});
			break;
		default:
			break;
		}

		((ViewPager) v).addView(pageViews.get(position));
		return pageViews.get(position);
	}

	@Override
	public void destroyItem(View v, int position, Object arg2) {
		((ViewPager) v).removeView(pageViews.get(position));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return pageViews.size();
	}

	@Override
	public boolean isViewFromObject(View v, Object arg1) {
		return v == arg1;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
}