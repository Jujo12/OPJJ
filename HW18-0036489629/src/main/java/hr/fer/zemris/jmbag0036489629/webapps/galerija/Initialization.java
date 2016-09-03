package hr.fer.zemris.jmbag0036489629.webapps.galerija;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The initialization web listener that initialized photos for gallery.
 *
 * @author Juraj Juričić
 */
@WebListener
public class Initialization implements ServletContextListener {

	/** The path to decriptor text file. */
	private static final String descriptorName = "/WEB-INF/opisnik.txt";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Path descriptor = Paths.get(sce.getServletContext().getRealPath(descriptorName));
		Map<String, Set<Photo>> photos = new HashMap<>();
		
		try {
			List<String> lines = Files.readAllLines(descriptor);
			Iterator<String> it = lines.iterator();
			while(it.hasNext()){
				String path = it.next();
				String title = it.next();
				String tagsString = it.next();
				
				String[] tags = tagsString.split(",");
				for(int i = 0; i < tags.length; i++){
					tags[i] = tags[i].trim();
				}
				
				Photo photo = new Photo(path, title, tags);
				for(String tag : tags){
					addPhotoToTagMap(photos, tag, photo);
				}
			}
			
		} catch (Exception e) {
			System.out.println("An error occured while initializing photo gallery.");
		}
		
		sce.getServletContext().setAttribute("photosMap", photos);
	}
	
	/**
	 * Adds the photo to tag map.
	 *
	 * @param map the map
	 * @param tag the tag
	 * @param photo the photo
	 */
	private void addPhotoToTagMap(Map<String, Set<Photo>> map, String tag, Photo photo){
		tag = tag.substring(0, 1).toUpperCase() + tag.substring(1).toLowerCase();
		Set<Photo> set = map.get(tag);
		if (set == null){
			set = new HashSet<>();
		}
		
		set.add(photo);
		map.put(tag, set);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}