package scs2682.exercise08.ui.home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import scs2682.exercise08.R;
import scs2682.exercise08.ui.home.events.OnContactEvent;
import scs2682.exercise08.ui.home.events.OnContactUpdatedEvent;

public class Home extends ViewPager {
	private static final int POSITION_CONTACTS = 0;
	private static final int POSITION_CONTACT = 1;

	public Home(Context context) {
		this(context, null);
	}

	public Home(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Subscribe
	public void onEventBusEvent(OnContactEvent onContactEvent) {
		// move to second page
		setCurrentItem(POSITION_CONTACT);
	}

	@Subscribe
	public void onEventBusEvent(OnContactUpdatedEvent onContactUpdatedEvent) {
		// move to first page
		setCurrentItem(POSITION_CONTACTS);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		// set the two pages
		List<HomeAdapterPage> pages = new ArrayList<>(2);
		pages.add(POSITION_CONTACTS, new HomeAdapterPage(R.layout.home_contacts, getContext().getString(R.string.contacts)));
		pages.add(POSITION_CONTACT, new HomeAdapterPage(R.layout.home_contact, getContext().getString(R.string.contact_form)));

		// and set the adapter
		setAdapter(new HomeAdapter(getContext(), pages));
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		// register to listen for any events from the bus
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// unregister to listen for any events
		EventBus.getDefault().unregister(this);
	}
}