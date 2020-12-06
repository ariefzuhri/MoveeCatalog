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
import com.ariefzuhri.moveecatalog.response.model.CrewCredit;
import java.util.ArrayList;
import static com.ariefzuhri.moveecatalog.helper.AppHelper.loadImage;

public class CrewCreditAdapter extends RecyclerView.Adapter<CrewCreditAdapter.CreditViewHolder> {
    private ArrayList<CrewCredit> crewCredits = new ArrayList<>();

    public void setData(ArrayList<CrewCredit> crews){
        crewCredits.clear();
        crewCredits.addAll(crews);
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
        CrewCredit crew = crewCredits.get(i);
        holder.bind(crew);
    }

    @Override
    public int getItemCount() {
        return crewCredits.size();
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

        void bind(CrewCredit crew) {
            loadImage((Activity) itemView.getContext(), imgPhoto, crew.getPhoto(), "original");
            tvName.setText(crew.getName());
            tvRole.setText(crew.getJob());
        }
    }
}
