package scs2682.exercise08.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import scs2682.exercise08.AppActivity;
import scs2682.exercise08.R;
import scs2682.exercise08.data.contact.ContactModel;
import scs2682.exercise08.ui.home.events.OnContactEvent;
import scs2682.exercise08.ui.home.events.OnContactUpdatedEvent;

/**
 * The view details will be inflated in a layout file.
 *
 * This view is merely a holder of all logic, and its view content will be fleshed out in a layout, res/layout/contact.xml
 */
public class Contact extends ScrollView {
	private TextView firstName;
	private TextView lastName;
	private TextView phone;
	private TextView website;

	private ContactModel contactModel;

	// Any negative position means ignore
	private int contactModelPosition = -1;

	public Contact(Context context) {
		super(context);
	}

	public Contact(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Contact(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Subscribe
	public void onEventBusEvent(OnContactEvent onContactEvent) {
		this.contactModel = onContactEvent.contactModel;
		this.contactModelPosition = onContactEvent.contactModelPosition;

		updateUi();

		// show keyboard
		AppActivity.showKeyboard(getContext(), firstName);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		// this method will be called at the end of the layout inflation.
		// It is safe to look for any view children

		firstName = findViewById(R.id.firstName);
		lastName = findViewById(R.id.lastName);
		phone = findViewById(R.id.phone);
		website = findViewById(R.id.website);

		Button web = findViewById(R.id.web);
		web.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String websiteVallue = website.getText().toString();
				if (!websiteVallue.isEmpty()) {
					// there is some url
					if (!websiteVallue.startsWith("http://")) {
						// append "http://" at the url if there is no such in advance
						websiteVallue = "http://" + websiteVallue;
					}

					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(websiteVallue));
					getContext().startActivity(intent);
				}
			}
		});

		Button cancel = findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// post null as contact, so nothing will be added or updated
				EventBus.getDefault().post(new OnContactUpdatedEvent(null, -1));

				// reset UI
				contactModel = null;
				contactModelPosition = -1;
				updateUi();
			}
		});

		Button update = findViewById(R.id.update);
		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String firstNameValue = firstName.getText().toString();
				String websiteValue = website.getText().toString();

				if (firstNameValue.isEmpty() || websiteValue.isEmpty()) {
					// empty value for 'first name' or 'website'
					Toast
						.makeText(getContext(), getContext().getString(R.string.first_name_and_website_cannot_be_empty), Toast.LENGTH_SHORT)
						.show();
				}
				else {
					if (contactModel != null) {
						// definitely was called from a user action rather swiping blindly from first page
						contactModel = new ContactModel(firstNameValue, lastName.getText().toString(), phone.getText().toString(),
							websiteValue);
					}

					// dispatch the event with the updated contact and its position
					EventBus.getDefault().post(new OnContactUpdatedEvent(contactModel, contactModelPosition));

					// reset UI
					contactModel = null;
					contactModelPosition = -1;
					updateUi();
				}
			}
		});
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

	/**
	 * Update UI from 'contactModel'
	 */
	private void updateUi() {
		// default as empty, nothing
		String firstNameValue = "";
		String lastNameValue = "";
		String phoneValue = "";
		String websiteValue = "";

		if (contactModel != null) {
			firstNameValue = contactModel.getFirstName();
			lastNameValue = contactModel.getLastName();
			phoneValue = contactModel.getPhone();
			websiteValue = contactModel.getWebsite();
		}

		firstName.setText(firstNameValue);
		lastName.setText(lastNameValue);
		phone.setText(phoneValue);
		website.setText(websiteValue);
	}
}
