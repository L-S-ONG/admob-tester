package raynordev.admobtester;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by MrFlAwLeSs on 5/1/2015.
 */
public class VideoAdapter extends BaseAdapter {
    // The list of videos to display
    List<Video> videos;
    // An inflator to use when creating rows
    private LayoutInflater mInflater;

    /**
     * @param context this is the context that the list will be shown in - used to create new list rows
     * @param videos this is a list of videos to display
     */
    public VideoAdapter(Context context, List<Video> videos) {
        this.videos = videos;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // If convertView wasn't null it means we have already set it to our list_item_user_video so no need to do it again
        if(convertView == null){
            // This is the layout we are using for each row in our list
            // anything you declare in this layout can then be referenced below
            convertView = mInflater.inflate(R.layout.videoslayout, null);
        }
        // We are using a custom imageview so that we can load images using urls
        // For further explanation see: http://blog.blundell-apps.com/imageview-with-loading-spinner/
        ImageView thumb = (ImageView) convertView.findViewById(R.id.itemImage);

        TextView title = (TextView) convertView.findViewById(R.id.itemName);
        TextView duration = (TextView) convertView.findViewById(R.id.itemDuration);

        // Get a single video from our list
        Video video = videos.get(position);

        int durationLength = Integer.parseInt(video.getDuration()) * 1000;
        TimeZone tz = TimeZone.getTimeZone("GMT");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date(durationLength));

        title.setText(video.getTitle());
        duration.setText(time);

        new DownloadImageTask((ImageView) convertView.findViewById(R.id.itemImage))
                .execute(video.getThumbUrl());

        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            //Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            //Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            //Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
