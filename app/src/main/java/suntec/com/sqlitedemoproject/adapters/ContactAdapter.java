package suntec.com.sqlitedemoproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import suntec.com.sqlitedemoproject.R;
import suntec.com.sqlitedemoproject.database.DatabaseHelper;
import suntec.com.sqlitedemoproject.models.ContactModel;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    ContactModel contact;
    private ArrayList<ContactModel> contacts;
    DatabaseHelper helper;

    public ContactAdapter(Context context, ArrayList<ContactModel> contacts) {
        this.context = context;
        this.contacts = contacts;
        helper=new DatabaseHelper(context);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_row, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        contact = contacts.get(position);
        holder.txtId.setText(String.valueOf(contact.getId()));
        holder.txtName.setText(contact.getName());
        holder.txtContact.setText(contact.getPhoneNumber());

        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.deleteContact(contacts.get(position));
                contacts.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView txtId;
        private TextView txtName;
        private TextView txtContact;
        private LinearLayout llDelete;

        public ContactViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.txt_id);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtContact = (TextView) itemView.findViewById(R.id.txt_mobile_number);
            llDelete = (LinearLayout) itemView.findViewById(R.id.ll_delete_contact);
        }
    }
}
