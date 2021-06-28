package com.example.mycontacts;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnClickListenerAddContact implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.contact_input_form, null, false);

        final EditText editTextFirstName = formElementsView.findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = formElementsView.findViewById(R.id.editTextLastName);
        final EditText editTextPhone = formElementsView.findViewById(R.id.editTextPhone);
        final EditText editTextEmail = formElementsView.findViewById(R.id.editTextEmail);
        final EditText editTextAddress = formElementsView.findViewById(R.id.editTextAddress);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Create Contact")
                .setPositiveButton("Add",
                        (dialog, id) -> {

                            String contactFirstName = editTextFirstName.getText().toString();
                            String contactLastName = editTextLastName.getText().toString();
                            String contactPhone = editTextPhone.getText().toString();
                            String contactEmail = editTextEmail.getText().toString();
                            String contactAddress = editTextAddress.getText().toString();

                            ObjectContact objectContact = new ObjectContact();
                            objectContact.firstName= contactFirstName;
                            objectContact.lastName= contactLastName;
                            objectContact.phone= contactPhone;
                            objectContact.email= contactEmail;
                            objectContact.address= contactAddress;

                            boolean createSuccessful = new TableControllerContact(context).create(objectContact);

                            if(createSuccessful){
                                Toast.makeText(context, "Student information was saved.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to save student information.", Toast.LENGTH_SHORT).show();
                            }


                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();

                            dialog.cancel();
                        }).show();

    }
}
