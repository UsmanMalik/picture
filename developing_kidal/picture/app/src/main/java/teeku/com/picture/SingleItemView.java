package teeku.com.picture;

/**
 * Created by usman on 11/20/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class SingleItemView extends Activity {

    String phone;
    ImageLoader imageLoader = new ImageLoader(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);

        Intent i = getIntent();
        // Get the intent from ListViewAdapter
        phone = i.getStringExtra("phone");

        // Locate the ImageView in singleitemview.xml
        ImageView imgphone = (ImageView) findViewById(R.id.phone);

        // Load image into the ImageView
        imageLoader.DisplayImage(phone, imgphone);
    }
}