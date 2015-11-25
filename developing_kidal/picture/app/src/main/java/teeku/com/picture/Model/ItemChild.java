package teeku.com.picture.Model;

/**
 * Created by usman on 11/23/15.
 */
public class ItemChild {

    private int id;
    private String title;
    private String description;
    private String image_path;
    private int item_id;

    public ItemChild(int id, int item_id,  String title, String description , String image_path) {
        this.id = id;
        this.item_id = item_id;
        this.image_path = image_path;
        this.description = description;
        this.title = title;
    }

    public ItemChild(){

    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }


}
