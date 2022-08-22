package com.realityexpander.basicexoplayer2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import java.net.URL

class MainActivity : AppCompatActivity() {

    var simpleExoPlayer: SimpleExoPlayer? = null
    var exoPlayer: ExoPlayer? = null
    var playerView: PlayerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        initPlayer() // for exoplayer 2.13.3
        initPlayer2() // for exoplayer 2.18.1
    }

    // For 'com.google.android.exoplayer:exoplayer:2.13.3'
    fun initPlayer() {
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        playerView = findViewById(R.id.playerView)

        playerView?.apply {
            controllerShowTimeoutMs = 0
            cameraDistance = 30f
            player = simpleExoPlayer
        }

        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "BasicExoPlayer"))
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
        playerView = findViewById(R.id.playerView)

        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer?.playWhenReady = true
        playerView?.player = exoPlayer

        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem =
            MediaItem.fromUri("https://files.freemusicarchive.org//storage-freemusicarchive-org//tracks//Pwgnnzp2ZsICaklopTbKD24keSTqsptGRvZSmY2J.mp3")
//            MediaItem.fromUri("https://www.djing.com/tv/28676-04.m3u8")

        // for HLS (m3u8)
        val mediaSource =
            HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)

        // for Progressive (mp3)
        val mediaSource2 = ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(mediaItem)

        exoPlayer?.apply {
            setMediaSource(mediaSource2)
            seekTo(0)
            playWhenReady = playWhenReady
            prepare()
        }
    }
}