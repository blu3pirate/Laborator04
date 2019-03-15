package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private Button saveButton;
    private Button cancelButton;
    private Button toggleButton;
    private boolean isAddFieldsVisible;
    private EditText edit_name;
    private EditText edit_phone;
    private EditText edit_email;
    private EditText edit_address;
    private EditText edit_jobTitle;
    private EditText edit_company;
    private EditText edit_website;
    private EditText edit_im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);


        saveButton = (Button) findViewById(R.id.save);
        cancelButton = (Button) findViewById(R.id.cancel);
        toggleButton = (Button) findViewById(R.id.toggle_add_fields);
        edit_name = findViewById(R.id.name);
        edit_phone = findViewById(R.id.phone);
        edit_email = findViewById(R.id.email);
        edit_address = findViewById(R.id.address);
        edit_jobTitle = findViewById(R.id.jobTitle);
        edit_company = findViewById(R.id.company);
        edit_website = findViewById(R.id.website);
        edit_im = findViewById(R.id.im);


        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                edit_phone.setText(phone);
            } else {
                Toast.makeText(this, "Phone ERR", Toast.LENGTH_LONG).show();
            }
        }

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.save:
                        final String name = edit_name.getText().toString();
                        final String phone = edit_phone.getText().toString();
                        final String email = edit_email.getText().toString();
                        final String address = edit_address.getText().toString();
                        final String jobTitle = edit_jobTitle.getText().toString();
                        final String company = edit_company.getText().toString();
                        final String website = edit_website.getText().toString();
                        final String im = edit_im.getText().toString();

                        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        if (!name.isEmpty()) {
                            intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                        }
                        if (phone != null) {
                            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                        }
                        if (email != null) {
                            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                        }
                        if (address != null) {
                            intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                        }
                        if (jobTitle != null) {
                            intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                        }
                        if (company != null) {
                            intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                        }
                        ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                        if (website != null) {
                            ContentValues websiteRow = new ContentValues();
                            websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                            websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                            contactData.add(websiteRow);
                        }
                        if (im != null) {
                            ContentValues imRow = new ContentValues();
                            imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                            imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                            contactData.add(imRow);
                        }
                        intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                        //startActivity(intent);
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.cancel:
                        setResult(Activity.RESULT_CANCELED, new Intent());
                        finish();
                        break;
                    case R.id.toggle_add_fields:
                        LinearLayout container = findViewById(R.id.add_fields);
                        if(isAddFieldsVisible) {
                            container.setVisibility(View.GONE);
                            toggleButton.setText("Show Additional Fields");
                            isAddFieldsVisible = false;
                        } else {
                            container.setVisibility(View.VISIBLE);
                            toggleButton.setText("Hide Additional Fields");
                            isAddFieldsVisible = true;
                        }

                        break;
                    default:

                }
            }
        };

        saveButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
        toggleButton.setOnClickListener(listener);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case 1:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
