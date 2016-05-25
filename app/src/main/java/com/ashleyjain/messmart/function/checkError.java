package com.ashleyjain.messmart.function;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ashleyjain.messmart.R;

/**
 * Created by ashleyjain on 27/03/16.
 */
public class checkError implements TextWatcher {

    EditText editTextI;
    //EditText editTextII;

    private static boolean checkValid(String mobileno){
        return mobileno.matches("[0-9]{10}");  //checks if mobile no is 10 digits
    }

    public checkError(EditText nameEdText){            //Class constructor takes an edittext element as an argument
        this.editTextI = nameEdText;
        if(editTextI.getText().toString().length()==0){editTextI.setError("field cannot be left blank");}
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //TODO Auto-generated method stub
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }

    public void afterTextChanged(Editable s) {

        if(editTextI.getId()== R.id.phone | editTextI.getId() == R.id.newmob){
            if(!checkValid(editTextI.getText().toString())){
                editTextI.setError("Please enter a valid mobile no");  //sets error if entry no validation returns false
            }
        }
        else{
            if(editTextI.getText().toString().length()==0){
                editTextI.setError("Field cannot be left blank.");  //sets error if field is left blank
            }
        }
    }

}

