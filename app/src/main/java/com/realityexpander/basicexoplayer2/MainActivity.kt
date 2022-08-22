package com.realityexpander.basicexoplayer2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {

    var simpleExoPlayer: SimpleExoPlayer? = null
    var playerView: PlayerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPlayer()
    }

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
        simpleExoPlayer?.setMediaSource(audioSource)
        simpleExoPlayer?.prepare()
        simpleExoPlayer?.playWhenReady = true
    }
}