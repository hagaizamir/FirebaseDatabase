package hagai.edu.firebasedatabase.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hagai.edu.firebasedatabase.R;
import hagai.edu.firebasedatabase.models.ShoppingLists;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddListFragment extends DialogFragment {


    @BindView(R.id.etListName)
    EditText etListName;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAdd)
    public void onBtnAddClicked() {

        //1. get the user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //2. get a reference to the user list table

        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference("UserLists").child(user.getUid());
        //3. push returens the id of the new records

        //created a new row
        DatabaseReference row = ref.push();
        String listUID = row.getKey();

        //get the list name from the edittext
        String listName = etListName.getText().toString();

        //constructor the model
        ShoppingLists model = new ShoppingLists(user.getUid(),listUID,listName);

        row.setValue(model);

        dismiss();
    }
}
