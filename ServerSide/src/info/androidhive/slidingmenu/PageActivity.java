package info.androidhive.slidingmenu;


import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class PageActivity extends Activity {

	public static ViewPager viewPager;
	private ArrayList<View> pageViews; 	// pages view
	private ImageView imageView;        // image => small white and black point
	private ImageView[] imageViews;
	private ViewGroup viewPics;         // layout for view group
	private ViewGroup viewPoints;       // layout for small points
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // save views into array
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        pageViews.add(inflater.inflate(R.layout.page01, null));
        pageViews.add(inflater.inflate(R.layout.page02, null));
        pageViews.add(inflater.inflate(R.layout.page03, null));
        pageViews.add(inflater.inflate(R.layout.page04, null));
        
        // save small points into array
        imageViews = new ImageView[pageViews.size()];
        viewPics = (ViewGroup) inflater.inflate(R.layout.view_pics, null);
        
        // find view by id
        viewPoints = (ViewGroup) viewPics.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) viewPics.findViewById(R.id.guidePages);
        
        // set images
        for(int i=0;i<pageViews.size();i++){
        	
        	imageView = new ImageView(PageActivity.this);
        	imageView.setLayoutParams(new LayoutParams(20,20));
        	imageView.setPadding(20, 0, 20, 0);
        	imageViews[i] = imageView;
        	
        	if(i==0){
        		imageViews[i].setBackgroundResource(R.drawable.circle_white);
        	}else{
        		imageViews[i].setBackgroundResource(R.drawable.circle_grey);
        	}
        	
        	viewPoints.addView(imageViews[i]);
        }
        setContentView(viewPics);
        
        // set page adapter and listener
        viewPager.setAdapter(new GuidePageAdapter(pageViews, this,this));
        viewPager.setOnPageChangeListener(new GuidePageChangeListener(imageViews));
        viewPager.setCurrentItem(3, true); 

    }
}