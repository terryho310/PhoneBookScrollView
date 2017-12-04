package scs2682.exercise08.ui.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import scs2682.exercise08.AppActivity;
import scs2682.exercise08.R;
import scs2682.exercise08.data.contact.ContactModel;
import scs2682.exercise08.ui.home.events.OnContactEvent;
import scs2682.exercise08.ui.home.events.OnContactUpdatedEvent;

/**
 *
 */
public class Contacts extends LinearLayout {
	private Button add;
	private RecyclerView recyclerView;

	private ContactsAdapter contactsAdapter;

	public Contacts(Context context) {
		this(context, null, 0);
	}

	public Contacts(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Contacts(Context context, @Nullable AttributeSet attrs,
		int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Subscribe
	public void onEventBusEvent(OnContactUpdatedEvent onContactUpdatedEvent) {
		// hide keyboard in any case
		AppActivity.hideKeyboard(getContext());

		if (onContactUpdatedEvent.contactModel != null) {
			// there is something to be added
			if (onContactUpdatedEvent.contactModelPosition < 0) {
				// negative value treat as adding the contact as a brand new one at the bottom
				contactsAdapter.contacts.add(onContactUpdatedEvent.contactModel);
				contactsAdapter.notifyItemInserted(contactsAdapter.contacts.size() - 1);
			}
			else {
				// update the existing contact with the one provided and refresh the list
				contactsAdapter.contacts.set(onContactUpdatedEvent.contactModelPosition, onContactUpdatedEvent.contactModel);
				contactsAdapter.notifyItemChanged(onContactUpdatedEvent.contactModelPosition);
			}
		}

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		add = findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// send to the event bus there is a need to create a new contact
				ContactModel emptyContact = new ContactModel("", "", "", "");
				EventBus.getDefault().post(new OnContactEvent(emptyContact, -1));
			}
		});

		contactsAdapter = new ContactsAdapter();

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(contactsAdapter);
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
