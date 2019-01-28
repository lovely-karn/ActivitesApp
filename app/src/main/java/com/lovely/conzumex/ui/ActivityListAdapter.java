package com.lovely.conzumex.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovely.conzumex.databinding.ItemActivityListBinding;
import com.lovely.conzumex.model.Activity;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityHolder> {


    private ArrayList<Activity> activityArrayList;
    private OnViewHolderItemClick onViewHolderItemClick;

    public ActivityListAdapter(final OnViewHolderItemClick onViewHolderItemClick) {

        this.onViewHolderItemClick = onViewHolderItemClick;
    }

    public void updateList(ArrayList<Activity> activityArrayList) {

        this.activityArrayList = activityArrayList;
    }

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemActivityListBinding itemActivityListBinding = ItemActivityListBinding.inflate(layoutInflater, parent, false);
        return new ActivityListAdapter.ActivityHolder(itemActivityListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHolder viewHolder, int position) {

        ((ActivityHolder) viewHolder).bindTo(activityArrayList.get(viewHolder.getAdapterPosition()));
    }

    public interface OnViewHolderItemClick {

        void onClickOfItem(String img, String activityName);
    }

    @Override
    public int getItemCount() {
        return activityArrayList != null ? activityArrayList.size() : 0;
    }

    public class ActivityHolder extends RecyclerView.ViewHolder {

        private ItemActivityListBinding binding;

        public ActivityHolder(ItemActivityListBinding itemActivityListBinding) {
            super(itemActivityListBinding.getRoot());

            this.binding = itemActivityListBinding;
        }

        public void bindTo(Activity activity) {

            // displaying name in the entry
            binding.tvActivityName.setText(activity.getName());

            // displaying the image in the card - onhold now

            binding.llListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    // call listener
                    onViewHolderItemClick.onClickOfItem(activityArrayList.get(getAdapterPosition()).getImage()
                            , activityArrayList.get(getAdapterPosition()).getName());
                }
            });

        }
    }
}


