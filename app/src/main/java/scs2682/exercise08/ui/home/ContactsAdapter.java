package scs2682.exercise08.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import scs2682.exercise08.data.contact.ContactModel;

class ContactsAdapter extends RecyclerView.Adapter<ContactsCellHolder> {
	final List<ContactModel> contacts;

	ContactsAdapter() {
		contacts = new ArrayList<>();
	}

	@Override
	public ContactsCellHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
		return new ContactsCellHolder(recyclerView);
	}

	@Override
	public void onBindViewHolder(ContactsCellHolder holder, int position) {
		holder.bind(contacts.get(position));
	}

	@Override
	public int getItemCount() {
		return contacts.size();
	}
}
