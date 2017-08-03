package com.eje_c.hlsview;

import android.media.MediaPlayer;

import org.meganekkovr.Entity;
import org.meganekkovr.MeganekkoApp;
import org.meganekkovr.Scene;
import org.meganekkovr.SurfaceRendererComponent;

import java.io.IOException;

/**
 * VR Application entry point.
 */
public class App extends MeganekkoApp {
    private static final String INSTA360_ADDRESS = "192.168.1.2"; // Replace to your Insta360 Pro's IP address
    private final MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * Initialize VR scene.
     */
    @Override
    public void init() {
        super.init();

        // Load video and play it
        try {
            mediaPlayer.setDataSource("http://" + INSTA360_ADDRESS + ":8000/sdcard/liveStream/live.m3u8");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Set VR Scene
        Scene scene = setSceneFromXml(R.xml.scene);

        // Get Entity
        Entity screen = scene.findById(R.id.screen);

        // Setup for rendering video onto screen entity
        SurfaceRendererComponent surfaceRendererComponent = new SurfaceRendererComponent();
        surfaceRendererComponent.setContinuousUpdate(true);
        surfaceRendererComponent.setStereoMode(SurfaceRendererComponent.StereoMode.TOP_BOTTOM);
        mediaPlayer.setSurface(surfaceRendererComponent.getSurface());
        screen.add(surfaceRendererComponent);
    }
}
