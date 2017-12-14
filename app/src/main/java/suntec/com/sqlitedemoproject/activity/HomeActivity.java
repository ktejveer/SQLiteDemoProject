package suntec.com.sqlitedemoproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import suntec.com.sqlitedemoproject.adapters.ContactAdapter;
import suntec.com.sqlitedemoproject.models.ContactModel;
import suntec.com.sqlitedemoproject.R;
import suntec.com.sqlitedemoproject.database.DatabaseHelper;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtName;
    private EditText txtPhoneNumber;
    private LinearLayout llAddContact;
    private RecyclerView recAllContacts;
    private ContactAdapter adapter;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Referencing XML Widgets
        init();

        helper = new DatabaseHelper(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recAllContacts.setLayoutManager(mLayoutManager);
        recAllContacts.setItemAnimator(new DefaultItemAnimator());

        bindAdapter();

    }

    //Method to bind Adapter
    public void bindAdapter() {
        ArrayList<ContactModel> contacts = helper.getAllContacts();
        adapter = new ContactAdapter(this, contacts);
        recAllContacts.setAdapter(adapter);
    }

    //Method to reference XML widgets
    public void init() {
        txtName = (EditText) findViewById(R.id.txt_name);
        txtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
        llAddContact = (LinearLayout) findViewById(R.id.ll_add_contact);
        recAllContacts = (RecyclerView) findViewById(R.id.rec_contacts);

        //Register click listener
        llAddContact.setOnClickListener(this);
    }

    //Click listener of XML widgets
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_contact: {
                ContactModel contact = new ContactModel();
                DatabaseHelper helper = new DatabaseHelper(this);
                contact.setName(txtName.getText().toString());
                contact.setPhoneNumber(txtPhoneNumber.getText().toString());
                helper.addContact(contact);

                txtName.setText("");
                txtPhoneNumber.setText("");

                bindAdapter();
            }
            break;
        }
    }
}
