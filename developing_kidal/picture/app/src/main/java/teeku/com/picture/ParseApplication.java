package teeku.com.picture;

/**
 * Created by usman on 11/20/15.
 */
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // / Add your initialization code here
        Parse.initialize(this, "q382UANLqBnrnQQgBoVacWyYA4m9dkmiwgX0QG6K", "FJoiX4nhFhacOdfCS1rjMnW8MkB8ZIU5GxbvGdXv");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}
