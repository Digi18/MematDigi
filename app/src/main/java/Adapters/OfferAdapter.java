package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.app.mematdigi.R;

public class OfferAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public OfferAdapter(Context context){

        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.offer_images,container,false);

        ImageView slideImage = view.findViewById(R.id.offerImage);
        slideImage.setImageDrawable(context.getResources().getDrawable(getImageAt(position)));
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    private int getImageAt(int position) {
        switch (position) {
            case 0:
                return R.drawable.img1;
            case 1:
                return R.drawable.img2;
            default:
                return R.drawable.img3;

        }
    }

}
