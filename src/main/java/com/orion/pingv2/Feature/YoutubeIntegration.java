package com.orion.pingv2.Feature;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.orion.pingv2.Utils.AudioFilePlayer;
import com.orion.pingv2.Utils.Utils;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.axet.wget.info.URLInfo.CONNECT_TIMEOUT;
import static com.github.axet.wget.info.URLInfo.READ_TIMEOUT;

public class YoutubeIntegration {
    private static final String youtubeDataAPIKey = "<secret>";
    private static final String downloaderAPIUrl = "https://api.vevioz.com/api/button/mp3/";
    private static Video currentVideo;
    private static String currentVideoUrl;
    private static String currentVideoId;
    private static String currentVideoName;
    private final static Path videoThumbnailPath = Path.of("./temp/youtubeThumbnail.png");
    private final static Path videoAudioFilePath = Path.of("./temp/youtubeMusic.mp3");
    private final static AudioFilePlayer audioFilePlayer = new AudioFilePlayer();

    public static boolean play() {
        boolean fetched = fetchVideoData();
        if (fetched) {
            audioFilePlayer.setMusicPath(videoAudioFilePath.toString());
            Thread t1 = new Thread(audioFilePlayer);
            t1.start();
        }

        return fetched;
    }

    private static Video getVideoData(String url) throws IOException {
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                request -> {
                }).setApplicationName("video-test").build();

        final String videoId = getVideoId(url);
        YouTube.Videos.List videoRequest = youtube.videos().list(Collections.singletonList("snippet,statistics,contentDetails"));
        videoRequest.setId(Collections.singletonList(videoId));
        videoRequest.setKey(youtubeDataAPIKey);
        VideoListResponse listResponse = videoRequest.execute();
        List<Video> videoList = listResponse.getItems();

        return videoList.iterator().next();
    }

    private static String getVideoId(String url) {
        Matcher matcher = Utils.execRegex("watch\\?v=(?<videoId>.*)", url);

        if (matcher.find()) {
            return matcher.group(1);
        }
        else {
            System.out.println("Couldn't retrieve video id");
            return "";
        }
    }

    public static void getVideoThumbnail(@NotNull Video video, String filename) {
        Utils.downloadFile(video.getSnippet().getThumbnails().getHigh().getUrl(), filename);
    }

    private static String getVideoName() {
        if (YoutubeIntegration.fetchVideoData()) {
            return currentVideo.getSnippet().getTitle();
        }
        return "(null)";
    }


    private static String getDownloadLink(String url) {
        HttpsURLConnection conn = null;
        StringBuilder contents = new StringBuilder();
        System.out.println(url);
        try {
            conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);

            InputStream is = conn.getInputStream();

            String enc = conn.getContentEncoding();

            if (enc == null) {
                Pattern p = Pattern.compile("charset=(.*)");
                Matcher m = p.matcher(conn.getHeaderField("Content-Type"));
                if (m.find()) {
                    enc = m.group(1);
                }
            }

            if (enc == null)
                enc = "UTF-8";

            BufferedReader br = new BufferedReader(new InputStreamReader(is, enc));

            String line = null;


            while ((line = br.readLine()) != null) {
                contents.append(line);
                contents.append("\n");
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        Matcher matcher = Utils.execRegex("<a href=\"(?<downloadLink>.*\\/128\\/.*\\/0)\"", contents.toString());

        if (matcher.find()) {
            return matcher.group(1);
        }
        else
            System.out.println("Didn't find the download link");

        return "null";
    }

    private static void download() {
        String requestLink = downloaderAPIUrl + currentVideoId;
        Utils.downloadFile(getDownloadLink(requestLink), videoAudioFilePath.toString());
        System.out.println("Download Done");
    }

    public static boolean fetchVideoData() {
        if (currentVideoUrl == null) {
            System.out.println("YoutubeIntegration: YouTube video URL not set.");
            return false;
        }

        if (currentVideo == null) {
            try {
                currentVideo = getVideoData(currentVideoUrl);
                System.out.println("Video data fetched");
                currentVideoId = getVideoId(currentVideoUrl);
                currentVideoName = getVideoName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static String getEmbedVideoPlayer() {
        return "https://www.youtube.com/watch?v=" + currentVideoId;
    }

    public static String getVideoUrl() {
        return currentVideoUrl;
    }

    public static void setVideoUrl(String url) {
        currentVideoUrl = url;
    }

    public static Path getVideoThumbnailPath() {
        return videoThumbnailPath;
    }

    public static Path getVideoAudioPath() {
        return videoAudioFilePath;
    }

    public static String getCurrentVideoName() {
        return currentVideoName;
    }

    public void pause() {
        audioFilePlayer.pause();
    }

    public void stop() {
        audioFilePlayer.stop();
    }
}