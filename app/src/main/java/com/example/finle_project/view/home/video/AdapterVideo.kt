package com.example.finle_project.view.home.video

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.model.ModelVideo
import com.example.finle_project.R
import java.util.*

class AdapterVideo(
    private var context: Context,
    private var videoArrayList: ArrayList<ModelVideo>?
) : RecyclerView.Adapter<AdapterVideo.HolderVideo>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderVideo {
        val view = LayoutInflater.from(context).inflate(R.layout.rew_video, parent, false)
        return HolderVideo(view)
    }

    override fun onBindViewHolder(holder: HolderVideo, position: Int) {
        holder.setIsRecyclable(false)
        val modelVideo = videoArrayList!![position]
        val id: String? = modelVideo.id
        val title: String? = modelVideo.title
        val timestamp: String? = modelVideo.timestamp
        val videoUri: String? = modelVideo.videoUri

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp!!.toLong()
        val formattedDateTime =
            android.text.format.DateFormat.format("dd/MM/yyyy K:mm a", calendar).toString()

        holder.timeTv.text = title
        holder.timeTv.text = formattedDateTime
        setVideoUri(modelVideo, holder)


    }

    private fun setVideoUri(modelVideo: ModelVideo, holder: HolderVideo) {

        //show progress
        holder.progressBar.visibility = View.VISIBLE

        //get video uri
        val videoUrl: String? = modelVideo.videoUri

        // MediaController for play/time
        val mediaController = MediaController(context)
        mediaController.setAnchorView(holder.videoView)
        val videoUri = Uri.parse(videoUrl)

        holder.videoView.setMediaController(mediaController)
        holder.videoView.setVideoURI(videoUri)
        holder.videoView.requestFocus()

        holder.videoView.setOnPreparedListener { mediaPlayer ->
            //video is prepared to play
            mediaPlayer.start()
        }
        holder.videoView.setOnInfoListener(MediaPlayer.OnInfoListener { mp, what, extra ->
            //check if buffering/ rendering
            when (what) {
                MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                    //rendering started
                    return@OnInfoListener true

                }
                MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                    //buffering started
                    holder.progressBar.visibility = View.VISIBLE
                    return@OnInfoListener true
                }
                MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                    //buffering ended
                    holder.progressBar.visibility = View.GONE
                    return@OnInfoListener true
                }
            }
            false
        })

        holder.videoView.setOnCompletionListener { mediaPlayer ->
            //restart video
            mediaPlayer.start()
        }


    }

    override fun getItemCount(): Int {
        return videoArrayList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class HolderVideo(itmeView: View) : RecyclerView.ViewHolder(itmeView) {

        var videoView: VideoView = itmeView.findViewById(R.id.videoView)
        var title: TextView = itmeView.findViewById(R.id.titleTv)
        var timeTv: TextView = itmeView.findViewById(R.id.timeTv)
        var progressBar: ProgressBar = itmeView.findViewById(R.id.progressBar)


    }

}