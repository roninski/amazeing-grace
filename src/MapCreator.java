import java.util.ArrayList;

public interface MapCreator {
	ArrayList<Entity> create();
	int getHeight();
	int getWidth();
}
