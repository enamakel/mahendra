package com.example.admin.multisms.com.com.hotmoka.multisms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.multisms.R;
import com.example.admin.multisms.com.com.hotmoka.asimov.app.AsimovCallableActivity;
import com.example.admin.multisms.com.com.hotmoka.asimov.app.State;
import com.example.admin.multisms.com.com.hotmoka.asimov.parcelable.PairParcelable;
import com.example.admin.multisms.com.com.hotmoka.multisms.views.MessageEditText;

public class ContactSelectionActivity extends AsimovCallableActivity<PairParcelable<SortedSet<Contact>, MessageEditText.Message>> {

	@State
	private final Set<Contact> selectedContacts = new HashSet<Contact>();
	private final ArrayList<String> selectedPhone = new ArrayList<String>();


	private MessageEditText.Message message;
	private BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

	@Override
	protected void onCreate(PairParcelable<SortedSet<Contact>, MessageEditText.Message> contactsAndMessage) {
		setContentView(R.layout.activity_contact_selection);
	
		this.message = contactsAndMessage.getSecond();

		configureSendButton();
		setAdaptor(contactsAndMessage.getFirst());
	}

	private void configureSendButton() {
		findViewById(R.id.send).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View button) {
				new AlertDialog.Builder(ContactSelectionActivity.this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.confirmation)
						.setMessage(mkConfirmationMessage())
						.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								Contact firstRecipient = getFirstRecipient();

								System.out.println("Message--->" + message.personalizeFor(firstRecipient));
								sendSMS(message.personalizeFor(firstRecipient),selectedPhone);
								/*for (int i = 0; i < selectedPhone.size(); i++) {

									System.out.println("Selected Conats-->" + selectedPhone.get(i));
								}*/
							}
						})
						.setNegativeButton(R.string.cancel, null)
						.show();
			}

			private CharSequence mkConfirmationMessage() {
				Contact firstRecipient = getFirstRecipient();

				String msg = "You are going to send " + selectedContacts.size() + " personalized SMS.";
				msg += " For instance, the following message:\n\n";
				msg += "\"" + message.personalizeFor(firstRecipient) + "\"";
				msg += "\n\nis going to be sent to " + firstRecipient.name + " " + firstRecipient.surname;

				return msg;
			}

			private Contact getFirstRecipient() {
				Contact result = null;
				for (Contact contact : selectedContacts)
					if (result == null || contact.compareTo(result) < 0)
						result = contact;

				return result;
			}
		});

		updateSendButton();
	}

	private void updateSendButton() {
		Button sendButton = (Button) findViewById(R.id.send);

		if (selectedContacts.isEmpty()) {
			sendButton.setEnabled(false);
			sendButton.setText("Send to the selected contacts");
		}
		else if (selectedContacts.size() == 1) {
			sendButton.setEnabled(true);
			sendButton.setText("Send to the selected contact");
		}
		else {
			sendButton.setEnabled(true);
			sendButton.setText("Send to the selected " + selectedContacts.size() + " contacts");
		}
	}

	private void setAdaptor(SortedSet<Contact> contacts) {
		ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, R.layout.single_contact, contacts.toArray(new Contact[contacts.size()])) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = ensureThatConvertViewExists(convertView);
				initViewFromContact(convertView, getItem(position));

				return convertView;
			}

			private View ensureThatConvertViewExists(View convertView) {
				return convertView == null ? getLayoutInflater().inflate(R.layout.single_contact, null) : convertView;
			}

			private void initViewFromContact(View view, final Contact contact) {
				TextView contactText = (TextView) view.findViewById(R.id.contact_name);
				contactText.setText(contact.name + " " + contact.surname);
				final TextView contactPhone = (TextView) view.findViewById(R.id.contact_phone);
				contactPhone.setText(contact.phone);
				ImageView phoneImage = (ImageView) view.findViewById(R.id.phone_type);
				phoneImage.setImageResource(contact.isMobile ? R.drawable.smart_phone : R.drawable.phone);

				final CheckBox checkBox = (CheckBox) view.findViewById(R.id.contact_selector);
				checkBox.setChecked(selectedContacts.contains(contact));

				OnClickListener listener = new OnClickListener() {

					@Override
					public void onClick(View view) {
						if (view != checkBox)
							checkBox.setChecked(!checkBox.isChecked());

						if (checkBox.isChecked()) {
							selectedContacts.add(contact);
							selectedPhone.add(contact.phone);
						}
						else {
							selectedContacts.remove(contact);
							selectedPhone.remove(contact.phone);

						}
						updateSendButton();
					}
				};

				contactText.setOnClickListener(listener);
				contactPhone.setOnClickListener(listener);
				phoneImage.setOnClickListener(listener);
				checkBox.setOnClickListener(listener);
			}
		};

		((ListView) findViewById(R.id.list_of_contacts)).setAdapter(adapter);
	}
	//---sends an SMS message to another device---
	private void sendSMS( String message, ArrayList<String> selectedPhone)
	{
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
				new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		//---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
					case Activity.RESULT_OK:
						Toast.makeText(getBaseContext(), "SMS sent",
								Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						Toast.makeText(getBaseContext(), "Generic failure",
								Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						Toast.makeText(getBaseContext(), "No service",
								Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						Toast.makeText(getBaseContext(), "Null PDU",
								Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						Toast.makeText(getBaseContext(), "Radio off",
								Toast.LENGTH_SHORT).show();
						break;
				}
			}
		}, new IntentFilter(SENT));

		//---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
					case Activity.RESULT_OK:
						Toast.makeText(getBaseContext(), "SMS delivered",
								Toast.LENGTH_SHORT).show();
						break;
					case Activity.RESULT_CANCELED:
						Toast.makeText(getBaseContext(), "SMS not delivered",
								Toast.LENGTH_SHORT).show();
						break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		for (int i = 0; i < selectedPhone.size(); i++) {

			System.out.println("Remove Space--->"+selectedPhone.get(i).toString().trim().replaceAll("\\s+", ""));
			sms.sendTextMessage(selectedPhone.get(i).toString().trim().replaceAll("\\s+", ""), null, message, sentPI, deliveredPI);

		}
	}
}