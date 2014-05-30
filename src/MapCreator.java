import java.util.ArrayList;

/**
 * Interface for a map creator
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public interface MapCreator {
	ArrayList<Entity> create();
	int getHeight();
	int getWidth();
}
