package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mematdigi.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.ReferenceQueue;
import java.util.List;

import Model.Personal;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context ctx;
    List<Personal> personalImg;

    public RecyclerAdapter(Context ctx, List<Personal> personalImg) {
        this.ctx = ctx;
        this.personalImg = personalImg;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal,parent,false);

       ViewHolder vi = new ViewHolder(v);

        return vi;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        Personal model = personalImg.get(position);

        holder.title.setText(model.getTitle());

        RequestOptions requestOptions = new RequestOptions();

        Glide.with(ctx).load(model.getImg()).apply(requestOptions).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return personalImg.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.personalImage);
            title = itemView.findViewById(R.id.title);
        }
    }


}
