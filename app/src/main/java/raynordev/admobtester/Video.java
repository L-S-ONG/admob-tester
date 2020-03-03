package raynordev.admobtester;

import java.io.Serializable;

/**
 * Created by MrFlAwLeSs on 5/1/2015.
 */
public class Video implements Serializable {
    // The title of the video
    private String title;
    // A link to the video on youtube
    private String url;
    // A link to a still image of the youtube video
    private String thumbUrl;
    // Duration of the video
    private String duration;

    public Video(String title, String url, String thumbUrl, String duration) {
        super();
        this.title = title;
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.duration = duration;
    }

    /**
     * @return the title of the video
     */
    public String getTitle(){
        return title;
    }

    /**
     * @return the url to this video on youtube
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the thumbUrl of a still image representation of this video
     */
    public String getThumbUrl() {
        return thumbUrl;
    }


    public String getDuration() {
        return duration;
    }
}
