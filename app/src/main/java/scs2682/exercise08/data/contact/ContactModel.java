package scs2682.exercise08.data.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;


public class ContactModel {
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String PHONE = "phone";
	private static final String WEBSITE = "website";

	@NonNull private final Bundle bundle;

	public ContactModel(String firstName, String lastName, String phone, String website) {
		bundle = new Bundle();
		bundle.putString(FIRST_NAME, firstName);
		bundle.putString(LAST_NAME, lastName);
		bundle.putString(PHONE, phone);
		bundle.putString(WEBSITE, website);
	}

	public ContactModel(@NonNull Bundle bundle) {
		this.bundle = bundle;
	}

	/**
	 * Compares against other object and returns true if other object is from 'ContactModel' class,
	 * as well as 'First name' and 'Web site' strings are equal
	 *
	 * @param obj Any other object to compare against
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof ContactModel) {
			ContactModel other = (ContactModel) obj;
			return getFirstName().equals(other.getFirstName()) && getWebsite().equals(other.getWebsite());
		}
		else {
			return false;
		}
	}

	@NonNull
	public Bundle getBundle() {
		return this.bundle;
	}

	@NonNull
	public String getFirstName() {
		return bundle.getString(FIRST_NAME, "");
	}

	@NonNull
	public String getLastName() {
		return bundle.getString(LAST_NAME, "");
	}

	@NonNull
	public String getPhone() {
		return bundle.getString(PHONE, "");
	}

	@NonNull
	public String getWebsite() {
		return bundle.getString(WEBSITE, "");
	}
}
