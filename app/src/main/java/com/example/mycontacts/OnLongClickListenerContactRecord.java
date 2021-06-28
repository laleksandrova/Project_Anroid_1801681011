package com.example.mycontacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnLongClickListenerContactRecord implements View.OnLongClickListener {

    Context context;
    String id;

    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Contact Record")
                .setItems(items, (dialog, item) -> {

                    if (item == 0) {
                        editRecord(Integer.parseInt(id));
                    }
                    else if (item == 1) {

                        boolean deleteSuccessful = new TableControllerContact(context).delete(Integer.parseInt(id));

                        if (deleteSuccessful){
                            Toast.makeText(context, "Contact record was deleted.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Unable to delete contact record.", Toast.LENGTH_SHORT).show();
                        }

                        ((MainActivity) context).countRecords();
                        ((MainActivity) context).readRecords();

                    }

                    dialog.dismiss();

                }).show();

        return false;
    }

    public void editRecord(final int contactId) {
        final TableControllerContact tableControllerContact = new TableControllerContact(context);
        ObjectContact objectContact = tableControllerContact.readSingleRecord(contactId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.contact_input_form, null, false);

        final EditText editTextFirstName = formElementsView.findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = formElementsView.findViewById(R.id.editTextLastName);
        final EditText editTextPhone = formElementsView.findViewById(R.id.editTextPhone);
        final EditText editTextEmail = formElementsView.findViewById(R.id.editTextEmail);
        final EditText editTextAddress = formElementsView.findViewById(R.id.editTextAddress);

        editTextFirstName.setText(objectContact.firstName);
        editTextLastName.setText(objectContact.lastName);
        editTextPhone.setText(objectContact.phone);
        editTextEmail.setText(objectContact.email);
        editTextAddress.setText(objectContact.address);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        (dialog, id) -> {

                            ObjectContact objectContact1 = new ObjectContact();
                            objectContact1.id = contactId;
                            objectContact1.firstName = editTextFirstName.getText().toString();
                            objectContact1.lastName = editTextLastName.getText().toString();
                            objectContact1.phone = editTextPhone.getText().toString();
                            objectContact1.email = editTextEmail.getText().toString();
                            objectContact1.address = editTextAddress.getText().toString();

                            boolean updateSuccessful = tableControllerContact.update(objectContact1);

                            if(updateSuccessful){
                                Toast.makeText(context, "Contact record was updated.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to update contact record.", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();

                            dialog.cancel();
                        }).show();
    }

}
