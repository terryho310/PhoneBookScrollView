package scs2682.exercise08.ui.home.events;

import scs2682.exercise08.data.contact.ContactModel;

/**
 * Dispatched when a contact needs to be created or updated
 */
public final class OnContactEvent {
	/**
	 * If null will treat as creating a brand new contact
	 */
	public final ContactModel contactModel;
	public final int contactModelPosition;

	/**
	 * @param contactModelPosition Position of the contact in the dataset. If a negative value will treat as new
	 */
	public OnContactEvent(ContactModel contactModel, int contactModelPosition) {
		this.contactModel = contactModel;
		this.contactModelPosition = contactModelPosition;
	}
}
