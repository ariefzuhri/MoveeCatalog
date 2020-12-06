package com.ariefzuhri.moveecatalog.response.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ariefzuhri.moveecatalog.R;
import com.ariefzuhri.moveecatalog.response.model.CastCredit;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;

public class CastCreditAdapter extends RecyclerView.Adapter<CastCreditAdapter.CreditViewHolder> {
    private ArrayList<CastCredit> castCredits = new ArrayList<>();

    public void setData(ArrayList<CastCredit> casts){
        castCredits.clear();
        castCredits.addAll(casts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_credit, parent, false);
        return new CreditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditViewHolder holder, int i) {
        CastCredit cast = castCredits.get(i);
        holder.bind(cast);
    }

    @Override
    public int getItemCount() {
        return castCredits.size();
    }

    class CreditViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvRole;

        CreditViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo_credit);
            tvName = itemView.findViewById(R.id.tv_name_credit);
            tvRole = itemView.findViewById(R.id.tv_role_credit);
        }

        void bind(CastCredit cast) {
            loadImage((Activity) itemView.getContext(), imgPhoto, cast.getPhoto(), "original");
            tvName.setText(cast.getName());
            tvRole.setText(cast.getJob());
        }
    }
}
