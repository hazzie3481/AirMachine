package com.example.hireme.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hireme.ActivatedActivity;
import com.example.hireme.DeActivatedActivity;
import com.example.hireme.LoginActivity;
import com.example.hireme.R;
import com.example.hireme.UserManagementActivity;
import com.example.hireme.model.UserListData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.DistributionOrBuilder;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<UserListData> mDataset;
    public Button btnDelete;
    public Button btnActivate;
    public Button btnDeActivate;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public ImageView mImageView;
        public TextView mEmail;
        public TextView mName;
        public LinearLayout linearLayout;
        ConstraintLayout expandableLayout;
        ConstraintLayout notExpandableLayout;
        ConstraintLayout mainLayout;
        CardView cardView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_user_name);
            mEmail = (TextView) itemView.findViewById(R.id.tv_user_email);
            btnActivate = (Button) itemView.findViewById(R.id.btnActivate);
            btnDeActivate = (Button) itemView.findViewById(R.id.btnDeActivate);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.relativeItemListLayout);
            expandableLayout = (ConstraintLayout) itemView.findViewById(R.id.expandableLayout);
            notExpandableLayout = (ConstraintLayout) itemView.findViewById(R.id.notExpandableLayout);
            mainLayout = (ConstraintLayout)itemView.findViewById(R.id.mainLayout);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            notExpandableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserListData user = mDataset.get(getAdapterPosition());
                    user.setExpanded(!user.getExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            btnDeActivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //notExpandableLayout.setBackgroundColor(Color.parseColor("#80000000"));
                    Intent intent = new Intent(view.getContext(), DeActivatedActivity.class);
                    intent.putExtra("user",mDataset.get(getAdapterPosition()).getTitle().toString());

                    view.getContext().startActivity(intent);

                    Snackbar snackbar = Snackbar.make(linearLayout,"user "+mDataset.get(getAdapterPosition()).getTitle() +" De-Activated",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
            btnActivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //notExpandableLayout.setBackgroundColor(Color.parseColor("#b7defb"));
                    Intent intent = new Intent(view.getContext(), ActivatedActivity.class);
                    intent.putExtra("user",mDataset.get(getAdapterPosition()).getTitle().toString());
                    view.getContext().startActivity(intent);

                    Snackbar snackbar = Snackbar.make(linearLayout,"user "+mDataset.get(getAdapterPosition()).getTitle() +" Activated",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar.make(linearLayout,"user "+mDataset.get(getAdapterPosition()).getTitle() +" Deleted",Snackbar.LENGTH_LONG);
                    mDataset.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getItemCount(),mDataset.size());

                    snackbar.show();
                }
            });

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(ArrayList<UserListData> userDataset) {
        mDataset = userDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_user_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ArrayList<UserListData> movieListData = mDataset;
        holder.mName.setText(mDataset.get(position).getTitle());
        holder.mEmail.setText(mDataset.get(position).getEmail());

        boolean isExpanded = mDataset.get(position).getExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//        String teststring = mDataset.get(position).getImgSrc();
//        String[] separatedStrings  = mDataset.get(position).getImgSrc().split("w200");
////        String compareAbleString = separatedStrings[1];
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(135, 200);
        params.setMargins(75, 1, 75, 1);
//        if(compareAbleString.equalsIgnoreCase("null")){
//           holder.mImageView.setLayoutParams(params);
//           holder.mImageView.setImageResource(R.drawable.dummy);
//        }else
// Picasso.get().load(mDataset.get(position).getImgSrc()).into(holder.mImageView);
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Intent intent;
//                intent =  new Intent(view.getContext(), DetailsMovieActivity.class);
//                intent.putExtra("Title", mDataset.get(position).getTitle());
//                intent.putExtra("Description", mDataset.get(position).getDescription());
//                intent.putExtra("imageSrc", mDataset.get(position).getImgSrc());
//
//                //Toast.makeText(view.getContext(),"click on item: "+mDataset.get(position).getDescription(),Toast.LENGTH_LONG).show();
//                view.getContext().startActivity(intent);
//            }
//        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                //openDialog(view.getContext(),mDataset.get(position).getTitle());
                return true;
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}