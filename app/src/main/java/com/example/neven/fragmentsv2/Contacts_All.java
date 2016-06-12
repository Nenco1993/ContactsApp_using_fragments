package com.example.neven.fragmentsv2;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts_All extends ListFragment {


    DatabaseHandler handler;
    ArrayList<ContactsObjects> newContactsList = new ArrayList<ContactsObjects>();

    public Contacts_All() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contacts_all, container, false);


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }


    public void printDatabase() {

        handler = new DatabaseHandler(getActivity(), null, null, 1);

        List<ContactsObjects> contactsList = handler.getAllContacts();
        newContactsList = new ArrayList<ContactsObjects>();
        for (ContactsObjects cn : contactsList) {

            newContactsList.add(cn);

        }

        ListAdapter la = new ArrayAdapter<ContactsObjects>(getActivity(), android.R.layout.simple_list_item_1, newContactsList);

        setListAdapter(la);


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.my_back_arrowID).setVisible(true);


        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onStart() {
        super.onStart();


        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {

                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.contacts_popup, popupMenu.getMenu());
                popupMenu.show();


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.obrisiPopUpID:

                                long idFromDB2 = newContactsList.get(position).get_id();

                                ContactsObjects contact = new ContactsObjects(idFromDB2);

                                handler.deleteContact(contact);

                                printDatabase();

                                break;


                        }


                        return true;
                    }
                });


                return true;
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                final long idFromDB1 = newContactsList.get(position).get_id();

                ContactsObjects singleContact = handler.getContact(idFromDB1);

                String singleFirstName = singleContact.getFirstName();
                String singleLastName = singleContact.getLastName();
                String singlePhoneNumber = singleContact.getPhoneNumber();
                String singleEmail = singleContact.getEmail();

                //_________________________________________________________

                android.app.AlertDialog.Builder makeDialog = new android.app.AlertDialog.Builder(getActivity());
                LayoutInflater li = getLayoutInflater(null);
                View viewUpdate = li.inflate(R.layout.contacts_update_dialog, null);
                makeDialog.setTitle("Update dialog");
                makeDialog.setMessage("Do you want to update?");
                makeDialog.setView(viewUpdate);

                final EditText fnameUpdate = (EditText) viewUpdate.findViewById(R.id.etFirstNameUpdateID);
                final EditText lnameUpdate = (EditText) viewUpdate.findViewById(R.id.etLastNameUpdateID);
                final EditText phoneUpdate = (EditText) viewUpdate.findViewById(R.id.etPhoneNumberUpdateID);
                final EditText emailUpdate = (EditText) viewUpdate.findViewById(R.id.etEmailUpdateID);

                fnameUpdate.setText(singleFirstName);
                lnameUpdate.setText(singleLastName);
                phoneUpdate.setText(singlePhoneNumber);
                emailUpdate.setText(singleEmail);


                makeDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ContactsObjects contact = new ContactsObjects();
                        contact.set_id(idFromDB1);
                        contact.setFirstName(fnameUpdate.getText().toString());
                        contact.setLastName(lnameUpdate.getText().toString());
                        contact.setPhoneNumber(phoneUpdate.getText().toString());
                        contact.setEmail(emailUpdate.getText().toString());

                        handler.updateContact(contact);

                        printDatabase();


                    }
                });

                makeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                android.app.AlertDialog ad = makeDialog.create();
                ad.show();


                //_____________________________________________________________


            }
        });


    }


}
