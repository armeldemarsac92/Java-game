package GameLogic;

import java.awt.Image;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private Map<String, SoftReference<Image>> cache;

    public ImageCache() {
        this.cache = new HashMap<>();
    }

    public void addImage(String key, Image image) {
        cache.put(key, new SoftReference<>(image));
    }

    public Image getImage(String key) {
        SoftReference<Image> ref = cache.get(key);
        return (ref != null) ? ref.get() : null;
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key) && cache.get(key).get() != null;
    }
}
