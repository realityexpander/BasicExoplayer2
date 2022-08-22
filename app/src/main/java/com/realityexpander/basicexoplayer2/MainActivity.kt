package com.realityexpander.basicexoplayer2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import com.google.android.exoplayer2.C.WAKE_MODE_LOCAL
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import java.net.URL

// More M3U8 links:
// https://github.com/iptv-restream/channels/blob/master/channels.m3u8

class MainActivity : AppCompatActivity() {

    // for 2.13.3
    var simpleExoPlayer: SimpleExoPlayer? = null

    // for 2.18.1
    var exoPlayer: ExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        initPlayer() // for exoplayer 2.13.3
        initPlayer2() // for exoplayer 2.18.1
    }

    // For 'com.google.android.exoplayer:exoplayer:2.13.3'
    fun initPlayer() {
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        val playerView = findViewById<PlayerView>(R.id.playerView)

        playerView?.apply {
            controllerShowTimeoutMs = 0
            cameraDistance = 30f
            player = simpleExoPlayer
        }

        val dataSourceFactory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, "BasicExoPlayer"))
        val audioSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse("https://files.freemusicarchive.org//storage-freemusicarchive-org//tracks//Pwgnnzp2ZsICaklopTbKD24keSTqsptGRvZSmY2J.mp3")))
        simpleExoPlayer?.apply {
            setMediaSource(audioSource)
            prepare()
            playWhenReady = true
        }
    }

    // for 'com.google.android.exoplayer:exoplayer:2.18.1'
    fun initPlayer2() {
        val playerView = findViewById<StyledPlayerView>(R.id.playerView)

        exoPlayer = ExoPlayer.Builder(this).build()
        playerView?.apply {
            player = exoPlayer
            controllerShowTimeoutMs = 0 // keep controls visible
            useController = true // show controls
            controllerAutoShow = false // show controls automatically
            controllerHideOnTouch = false // hide controls when touched
            requestFocus()
            showController()
        }


        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()

        // for HLS (m3u8)
        val mediaSource =
            HlsMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(MediaItem.fromUri("https://www.djing.com/tv/28676-04.m3u8"))

        // for Progressive (mp3)
        val mediaSource2 =
            ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(MediaItem.fromUri("https://files.freemusicarchive.org//storage-freemusicarchive-org//tracks//Pwgnnzp2ZsICaklopTbKD24keSTqsptGRvZSmY2J.mp3"))

        exoPlayer?.apply {
            setMediaSource(mediaSource2)
            seekTo(0)
            playWhenReady = true
            prepare()
        }
    }
}