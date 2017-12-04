package scs2682.exercise08.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import scs2682.exercise08.R;
import scs2682.exercise08.data.contact.ContactModel;
import scs2682.exercise08.ui.home.events.OnContactEvent;

/**
 * View holder for R.layout.home_contacts_cell layout
 */

class ContactsCellHolder extends RecyclerView.ViewHolder {
	private TextView cardName;
	private TextView cardNumber;
	private ContactModel contactModel;
	private Button openWebsiteButton;

	ContactsCellHolder(ViewGroup recyclerView) {
		// inflate and add directly to its super() an instance of R.layout.home_contacts_cell
		super(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.home_contacts_cell, recyclerView, false));

		cardName = (TextView) itemView.findViewById(R.id.cardName);
		cardName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (contactModel != null) {
					// dispatch blindly the model and its position
					EventBus.getDefault().post(new OnContactEvent(contactModel, getAdapterPosition()));
				}
			}
		});

		openWebsiteButton = itemView.findViewById(R.id.openWebsite);
		openWebsiteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String link = contactModel.getWebsite().toString();
				if (!link.isEmpty()){
					if (!link.startsWith("http://")){
						link = "http://" + link;
					}

					final Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(link));
					view.getContext().startActivity(intent);

				}
			}
		});
	}

	void bind(ContactModel contactModel) {
		this.contactModel = contactModel;

		if (contactModel != null) {
			String textValue = contactModel.getFirstName() + " " + contactModel.getLastName();
			cardName.setText(textValue);

			String numberValue = "  " + contactModel.getPhone();
			cardNumber = (TextView) itemView.findViewById(R.id.cardNumber);
			cardNumber.setText(numberValue);


		}
	}
}
