package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.mematdigi.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import Model.General;

public class GeneralAdapter extends ArrayAdapter<General> {

    Context context;
    List<General> listData;
    int resource;

    public GeneralAdapter(@NonNull Context context, int resource,List<General> listData) {
        super(context, resource);

        this.context=context;
        this.resource=resource;
        this.listData=listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.general_book,null,true);
        }

          General model = listData.get(position);

          ImageView img = (ImageView)convertView.findViewById(R.id.bookImage);
          TextView name = (TextView)convertView.findViewById(R.id.bookName);
          TextView cost = (TextView)convertView.findViewById(R.id.bookPrice);

          assert model != null;
          name.setText(model.getName());
          cost.setText(model.getPrice());

          RequestOptions requestOptions = new RequestOptions();

         Glide.with(context).load(model.getImage()).apply(requestOptions).into(img);


        return convertView;
    }
}
