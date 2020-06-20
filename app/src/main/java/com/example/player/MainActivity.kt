package com.example.player


import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.player.application.Settings
import com.example.player.widget.media.AndroidMediaController
import com.example.player.widget.media.IjkVideoView
import tv.danmaku.ijk.media.player.IjkMediaPlayer


/*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // rtmp://47.115.21.212/live/livestream
    }
}*/


class MainActivity : AppCompatActivity() {
    private var mVideoView: IjkVideoView? = null
    private var mMediaController: AndroidMediaController? = null
    private val mTextView: TextView? = null
    private var mVideoPath: String? = null
    private var mHudView: TableLayout? = null
    private var mSettings: Settings? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startVideo()
    }

    private fun startVideo() {
        mSettings = Settings(this)
        mVideoPath = "rtmp://47.115.21.212/live/livestream"
        mMediaController = AndroidMediaController(this, false)
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")
        mVideoView = findViewById<View>(R.id.videoView) as IjkVideoView
        mHudView = findViewById<View>(R.id.hud_view) as TableLayout
        mVideoView!!.setMediaController(mMediaController)
        mVideoView!!.setHudView(mHudView)
        if (TextUtils.isEmpty(mVideoPath)) {
            Toast.makeText(
                this,
                "No Video Found! Press Back Button To Exit",
                Toast.LENGTH_LONG
            ).show()
        } else {
            mVideoView!!.setVideoURI(Uri.parse(mVideoPath))
            mVideoView!!.start()
        }
    }

    public override fun onStop() {
        super.onStop()
        mVideoView!!.stopPlayback()
        mVideoView!!.release(true)
        mVideoView!!.stopBackgroundPlay()
        IjkMediaPlayer.native_profileEnd()
    }
}
