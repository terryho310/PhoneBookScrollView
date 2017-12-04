package scs2682.exercise08.ui.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Adapter to load both pages in the view pager
 */
public class HomeAdapter extends PagerAdapter {
	private final LayoutInflater layoutInflater;
	private final List<HomeAdapterPage> pages;

	HomeAdapter(Context context, List<HomeAdapterPage> pages) {
		layoutInflater = LayoutInflater.from(context);
		this.pages = pages;
	}

	@Override
	public Object instantiateItem(ViewGroup viewPager, int position) {
		HomeAdapterPage page = pages.get(position);

		View view = layoutInflater.inflate(page.getLayoutId(), viewPager, false);
		viewPager.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup viewPager, int position, Object pageView) {
		viewPager.removeView((View) pageView);
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return pages.get(position).getTitle();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return object == view;
	}
}
