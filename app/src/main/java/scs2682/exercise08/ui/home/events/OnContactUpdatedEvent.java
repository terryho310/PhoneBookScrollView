package scs2682.exercise08.ui.home.events;

import scs2682.exercise08.data.contact.ContactModel;

/**
 * WHen a contact needs to be created or updated
 */
public class OnContactUpdatedEvent {
	public final ContactModel contactModel;
	public final int contactModelPosition;

	/**
	 *
	 * @param contactModel Updated (or newly created) contact
	 * @param contactModelPosition Position of the contact to be updated.
	 * If a new contact, just return a negative value, e.g. -1
	 */
	public OnContactUpdatedEvent(ContactModel contactModel, int contactModelPosition) {
		this.contactModel = contactModel;
		this.contactModelPosition = contactModelPosition;
	}
}
