package teeku.com.picture.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import teeku.com.picture.Model.ItemChild;
import teeku.com.picture.R;

/**
 * Created by usman on 11/23/15.
 */
public class CustomListChildAdapter extends RecyclerView.Adapter<CustomListChildAdapter.ViewHolder> {

    private List<ItemChild> countries;
    private int rowLayout;
    private Context mContext;

    public CustomListChildAdapter(List<ItemChild> countries, int rowLayout, Context context) {
        this.countries = countries;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_card_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemChild country = countries.get(i);
        viewHolder.title.setText(country.getTitle());
        viewHolder.description.setText(country.getDescription());
        //viewHolder.countryImage.setImageDrawable(mContext.getDrawable(country.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        // public ImageView countryImage;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.video_title);
            description = (TextView) itemView.findViewById(R.id.video_description);
            //   countryImage = (ImageView)itemView.findViewById(R.id.countryImage);
        }

    }
}
