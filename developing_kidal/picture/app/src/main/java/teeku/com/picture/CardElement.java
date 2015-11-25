package teeku.com.picture;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import teeku.com.picture.Adapter.CustomListAdapter;
import teeku.com.picture.Database.DatabaseHandler;
import teeku.com.picture.Model.Item;
import teeku.com.picture.Model.ItemChild;
import teeku.com.picture.utilities.RecyclerItemClickListener;

public class CardElement extends AppCompatActivity {

    RecyclerView rv;
    LinearLayoutManager llm;
    Context context;

    //private List<Movie> movieList = new ArrayList<Movie>(); // REMOVE
    private List<Item> categoryList = new ArrayList<Item>();
    private List<ItemChild> boxList = new ArrayList<ItemChild>();
    private RecyclerView listView;
    private CustomListAdapter adapter;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    final DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("I am in Card", "start");
        setContentView(R.layout.card_list);
        context = getApplicationContext();

        // database check
        int countCategory = db.getCategoryCount();
        int countBox = db.getBoxesCount();


        Log.e("count category: ", countCategory + "");

        if (countCategory == 0) {
            Log.e("Count Zero", "Fetch data from server");
            // Execute RemoteDataTask AsyncTask
            new RemoteDataTask().execute();

        } else {
            categoryList = db.getAllCategories();
            Log.e("Count IS NOT Zero", "Get DB");
            for (Item cn : categoryList) {
                String log = " Title: " + cn.getTitle() + " Descr " + cn.getDescription();
                // Writing Contacts to log
                Log.d("Name: ", log);

            }


            boxList = db.getAllBoxes();
            Log.e("Iterate All boxes", "Get DB");
            for (ItemChild cn : boxList) {
                String log = "  BOX Title: " + cn.getTitle() + " BOX Descr " + cn.getDescription();
                // Writing Contacts to log
                Log.d("Name: ", log);

            }

        }




        // Add Values
//        Item one = new Item();
//        one.setTitle("main");
//        one.setDescription("Hello");
//
//
//        categoryList.add(one);
//
//        Item second = new Item();
//        second.setTitle("second");
//        second.setDescription("second");


//        categoryList.add(second);
//        categoryList.add(second);
//        categoryList.add(second);
//        categoryList.add(second);
//        categoryList.add(second);
//        categoryList.add(second);
//        categoryList.add(second);
//        categoryList.add(second);
//        categoryList.add(second);
//        categoryList.add(second);


        rv = (RecyclerView) findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomListAdapter(categoryList, R.layout.activity_card_element, context);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(context, position + "", Toast.LENGTH_LONG).show();
                        Item item = categoryList.get(position);
                        Log.e("Clicked: ", item.getTitle());

                        Intent i = new Intent(CardElement.this, VideoLink.class);
                        i.putExtra("item_id", item.getId());
                        CardElement.this.startActivity(i);
                    }
                })
        );


//
//        rv.addOnItemTouchListener(
//                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

//        rv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("Item clicked", "Item clicked");
//                int itemPosition = rv.getChildPosition(v);
//                String item = categoryList.get(itemPosition).getTitle();
//                Toast.makeText(context, item, Toast.LENGTH_LONG).show();
//            }
//        });
//        rv = (RecyclerView)findViewById(R.id.rv);
//        rv.setHasFixedSize(true);
//        llm = new LinearLayoutManager(context);
//        rv.setLayoutManager(llm);

    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(CardElement.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Downlaoding pictures..");
            // Set progressdialog message
            mProgressDialog.setMessage("Please wait for few seconds..");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            //phonearraylist = new ArrayList<PhoneList>();
            try {
                // Locate the class table named "SamsungPhones" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "beautdata");
                // Locate the column named "position" in Parse.com and order list
                // by ascending
                query.orderByAscending("position");
                ob = query.find();
                Log.e("Query result: ", ob.toString());
                for (ParseObject country : ob) {

                    Item i = new Item();
                    ItemChild c = new ItemChild();

                    i.setId((int) country.get("item_id"));
                    i.setTitle((String) country.get("item_title"));
                    i.setDescription((String) country.get("item_description"));

                    db.addCategory(i);
                    categoryList.add(i);


                    c.setId((int) country.get("child_id"));
                    c.setItem_id((int) country.get("child_item_id"));
                    c.setTitle((String) country.get("child_title"));
                    c.setDescription((String) country.get("child_description"));
                    c.setImage_path((String) country.get("child_video_path"));

                    db.addBox(c);
                    boxList.add(c);

                    Log.e("item title parse: ", c.getImage_path());


//                    ParseFile image = (ParseFile) country.get("phones");
//                    PhoneList map = new PhoneList();
//                    map.setPhone(image.getUrl());
                    // phonearraylist.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            finish();
            startActivity(getIntent());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_element, menu);
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
