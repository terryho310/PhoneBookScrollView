package scs2682.exercise08.ui.home;

/**
 * This is the model (object which keeps all needed data) for a single page to be loaded in our view pager.
 */
public class HomeAdapterPage {
	private final int layoutId;
	private final String title;

	HomeAdapterPage(int layoutId, String title) {
		this.layoutId = layoutId;
		this.title = title;
	}

	public int getLayoutId() {
		return layoutId;
	}

	public String getTitle() {
		return title;
	}
}
