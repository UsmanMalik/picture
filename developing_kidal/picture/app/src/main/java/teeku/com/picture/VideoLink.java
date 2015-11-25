package teeku.com.picture;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import teeku.com.picture.Adapter.CustomListAdapter;
import teeku.com.picture.Adapter.CustomListChildAdapter;
import teeku.com.picture.Database.DatabaseHandler;
import teeku.com.picture.Model.Item;
import teeku.com.picture.Model.ItemChild;
import teeku.com.picture.utilities.RecyclerItemClickListener;

public class VideoLink extends AppCompatActivity {

    WebView webView;

    RecyclerView rv;
    LinearLayoutManager llm;
    Context context;

    //private List<Movie> movieList = new ArrayList<Movie>(); // REMOVE
    private List<ItemChild> itemChildList = new ArrayList<ItemChild>();
    private RecyclerView listView;
    private CustomListChildAdapter adapter;
    final DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_link);


        // database

        Integer itemId;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                itemId= null;
            } else {
                itemId= extras.getInt("item_id");

            }
        } else {
            itemId= (Integer) savedInstanceState.getSerializable("item_id");

        }
        Toast.makeText(this,"Item id: "+itemId ,Toast.LENGTH_SHORT  ).show();

        itemChildList = db.getCategoryBoxes(itemId); // Handle if null
        Log.e("Boxlist size", itemChildList.size() + "");

        if(itemChildList.size() == 0){

            Toast.makeText(this,"No Box found", Toast.LENGTH_SHORT).show();

//            Intent i = new Intent(this,MainActivity.class);
//            startActivity(i);

        }

        for (ItemChild cn : itemChildList) {
            String log = " ** ** Title: " + cn.getTitle() + " ,List : " + cn.getDescription();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
        ///


        context = getApplicationContext();

        // Add Values
//        ItemChild one = new ItemChild();
//        one.setTitle("Child");
//
//
//
//        categoryList.add(one);
//
//        ItemChild second = new ItemChild();
//        second.setTitle("second");
//
//
//        categoryList.add(second);


        rv = (RecyclerView) findViewById(R.id.listchild);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomListChildAdapter(itemChildList, R.layout.video_card_item, context);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(context, position + "", Toast.LENGTH_LONG).show();
                        ItemChild item = itemChildList.get(position);
                        Log.e("Clicked: ", item.getTitle());



                        // do whatever
                        Toast.makeText(context, position + "", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(VideoLink.this, PlayVideo.class);
                        i.putExtra("video_key", item.getImage_path());
                        VideoLink.this.startActivity(i);

                    }
                })
        );


//
//        String videoHtml;
//        webView = (WebView) findViewById(R.id.webView);
//        videoHtml = "<iframe frameborder=\"0\" width=\"480\" height=\"270\" src=\"http://www.dailymotion.com/embed/video/xznb64\"></iframe><br /><a href=\"http://www.dailymotion.com/video/xznb64_leyla-ile-mecnun-dan-evlilik-programlarina-gonderme_news\" target=\"_blank\">Leyla İle Mecnun&#039;dan Evlilik Programlarına...</a> <i>ile  <a href=\"http://www.dailymotion.com/trthaber\" target=\"_blank\">trthaber</a></i>";
//        webView.loadData(videoHtml, "text/html", "UTF-8");
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webView.getSettings().setJavaScriptEnabled(true);
       // webView.getSettings().setPluginsEnabled(true);
//        VideoView vidView = (VideoView)findViewById(R.id.myVideo);
//        String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
//        Uri vidUri = Uri.parse(vidAddress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_link, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
