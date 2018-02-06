package com.huypham.snack.objectbox.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huypham.snack.objectbox.R;
import com.huypham.snack.objectbox.model.Animal;

import java.util.List;

/**
 * Snack project_object box
 * Created by HuyPhamNA on 01/02/2018.
 */

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private Context mContext;
    private List<Animal> mAnimals;
    private OnItemClickListener mListener;

    public AnimalAdapter(List<Animal> animals, Context context, OnItemClickListener listener) {
        mContext = context;
        mAnimals = animals;
        mListener = listener;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        holder.setDataForAnimal(position);
    }

    @Override
    public int getItemCount() {
        return mAnimals.size();
    }

    /**
     * Animal view holder
     */
    class AnimalViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvOrderNumber;

        AnimalViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvOrderNumber = itemView.findViewById(R.id.tvOrderNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                   return  mListener.onItemLongClick(getAdapterPosition());
                }
            });
        }

        private void setDataForAnimal(int position) {
            Animal animal = mAnimals.get(position);
            tvOrderNumber.setText(mContext.getString(R.string.text_order_number, animal.getId()));
            tvDescription.setText(animal.getDescription());
            tvName.setText(animal.getName());
        }
    }

    /**
     * Interface click on item
     */
    interface OnItemClickListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }
}
