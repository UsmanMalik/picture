package teeku.com.picture.Adapter;

/**
 * Created by usman on 11/22/15.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import teeku.com.picture.Model.Item;
import teeku.com.picture.R;

/**
 * Created by Trey Robinson on 8/3/14.
 * Copyright 2014 MindMine LLC.
 */
public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder>{

    private List<Item> countries;
    private int rowLayout;
    private Context mContext;
    private int colors[];


    public CustomListAdapter(List<Item> countries, int rowLayout, Context context) {
        this.countries = countries;
        this.rowLayout = rowLayout;
        this.mContext = context;
        setColorsArray();
    }

    public void setColorsArray(){


        // Set colors array
        TypedArray ta = mContext.getResources().obtainTypedArray(R.array.colors);
        ta.length();

        colors = new int[ta.length()];

        for (int j = 0; j < ta.length(); j++) {
            colors[j] = ta.getColor(j, 0);
        }
        ta.recycle();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card_element, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {







        Item country = countries.get(i);
//        int[] colors_ = new int[] { R.color.abc_background_cache_hint_selector_material_dark, R.color.bright_foreground_inverse_material_dark};
        int colorPos = i % colors.length;
        Log.e("Color change #######: ", i + "");


        viewHolder.title.setText(country.getTitle());
        viewHolder.description.setText(country.getDescription());
        viewHolder.image_path.setBackgroundColor(colors[colorPos]);
       // ta.recycle();

        //viewHolder.countryImage.setImageDrawable(mContext.getDrawable(country.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image_path;

        // public ImageView countryImage;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image_path = (ImageView) itemView.findViewById(R.id.image_path);
            description = (TextView) itemView.findViewById(R.id.description);

            //   countryImage = (ImageView)itemView.findViewById(R.id.countryImage);
        }

    }
}