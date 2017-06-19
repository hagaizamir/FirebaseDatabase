package hagai.edu.firebasedatabase;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class WhatsappFragment extends Fragment {
    FirebaseDatabase mDatabase;
    FirebaseUser user;



    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    Unbinder unbinder;

    public WhatsappFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whatsapp, container, false);
        unbinder = ButterKnife.bind(this, view);

        //TODO: discussed sharedInstance
        mDatabase = FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

//        readFromDb();
//        readOnce();
        readIncremental();

        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSend)
    public void onBtnSendClicked() {
        String text = etMessage.getText().toString();
       if (TextUtils.isEmpty(text))return;

        //reference to a table MyCoolChat
       // DatabaseReference chatTable = mDatabase.getReference("MyCoolChat");

        //add a new record and get reference to the new record
       // DatabaseReference currentRow = chatTable.push();

        //set the value
        //currentRow.setValue(text);

        mDatabase.getReference("Chat").push().setValue(text);

        etMessage.setText(null);
    }

    private void setupRecycler(){
        ChatAdapter adapter = new ChatAdapter(mDatabase.getReference("Chat"));
        rvChat.setAdapter(adapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void  readFromDb(){

        //1.get a ref to the table
        DatabaseReference chatRef = mDatabase.getReference("Chat");
        //2.add a listener to the table

        //get the table at the begining
        //and each time the data changes - get all the table again

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> rows = dataSnapshot.getChildren();
                for (DataSnapshot row : dataSnapshot.getChildren()){
                    String text = row.getValue(String.class);

                    Toast.makeText(getContext(), text,Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void readOnce(){
        //get a reference to the table
        //add a listener

        //get the data once froom the server . not updating unless we run the query again
        mDatabase.getReference("Chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot row : dataSnapshot.getChildren()){
                    String text = row.getValue(String.class);

                    Toast.makeText(getContext(), text,Toast.LENGTH_SHORT).show();


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void readIncremental(){
        mDatabase.getReference("Chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Once get all the table
                String text = dataSnapshot.getValue(String.class);
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

                //once a new item is added we will only get the new child
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    static  class  ChatAdapter extends FirebaseRecyclerAdapter<String,ChatAdapter.ChatViewHolder{

        public  ChatAdapter (Query ref){
            super(String.class , R.layout.chat_item , ChatViewHolder.class, ref);
        }
        @Override
        protected void populateViewHolder(ChatViewHolder v , String text, int position) {
            v.tvChat.setText(text);
        }

        public static class ChatViewHolder extends  RecyclerView.ViewHolder{
            TextView tvChat;

            public ChatViewHolder(View itemView) {
                super(itemView);
                tvChat = (TextView)itemView.findViewById(R.id.tvChat);
            }
        }

    }

}
