package hagai.edu.firebasedatabase.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hagai.edu.firebasedatabase.R;
import hagai.edu.firebasedatabase.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends BottomSheetDialogFragment {


    @BindView(R.id.rvUsers)
    RecyclerView rvUsers;
    Unbinder unbinder;

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        unbinder = ButterKnife.bind(this, view);

        Query ref = FirebaseDatabase.getInstance().getReference("Users");
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setAdapter(new UserAdapter(ref));

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public static class UserAdapter extends FirebaseRecyclerAdapter<User, UserAdapter.UserViewHolder> {

        public UserAdapter(Query query) {
            super(User.class, R.layout.user_item, UserViewHolder.class, query);
        }

        @Override
        protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
            Context context = viewHolder.ivUserImage.getContext();
            viewHolder.tvUserName.setText(model.getDisplayName());
            Glide.with(context).load(model.getProfileImage()).into(viewHolder.ivUserImage);

        }

        public static class UserViewHolder extends RecyclerView.ViewHolder {
            ImageView ivUserImage;
            TextView tvUserName;

            public UserViewHolder(View itemView) {
                super(itemView);
                ivUserImage = (ImageView) itemView.findViewById(R.id.ivUserImage);
                tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            }

        }


    }

}
