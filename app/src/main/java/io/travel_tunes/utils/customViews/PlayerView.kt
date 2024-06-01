package io.travel_tunes.utils.customViews

import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.annotation.RawRes
import androidx.constraintlayout.widget.ConstraintLayout
import io.travel_tunes.R
import io.travel_tunes.utils.extencions.changeText
import io.travel_tunes.utils.coroutines.launchAtViewScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    /**
     * для запуска и остановки таймера
     */
    private var tickerChannelForPlayerProgress: ReceiveChannel<Unit>? = null

    private var actionButtonImage: ImageView
    private var seekBar: SeekBar
    private var currentProgressText: TextView
    private var maxProgressText: TextView

    private var mediaPlayer: MediaPlayer? = null

    init {
        inflate(context, R.layout.player_with_progress_view, this)

        actionButtonImage = findViewById(R.id.actionButton)
        seekBar = findViewById(R.id.seekbar)
        currentProgressText = findViewById(R.id.currentProgressText)
        maxProgressText = findViewById(R.id.maxProgressText)

        currentProgressText.changeText("00:00")
        maxProgressText.changeText("00:00")
        updateActionDrawable(false)
        initPlayerView()
    }

    private fun initPlayerView() {
        actionButtonImage.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                pause()
            } else {
                start()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    currentProgressText.changeText(getTimeByProgress(progress))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun updateActionDrawable(isPlayNow: Boolean) {
        val drawableRes = if (isPlayNow) {
            R.drawable.ic_pause
        } else
            R.drawable.ic_play
        actionButtonImage.setImageResource(drawableRes)
    }

    fun setAudioRaw(@RawRes audioRes: Int, isStartNow: Boolean = false) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, audioRes)
        val durationAudio = mediaPlayer?.duration ?: 0
        setDefaultProgressState()
        seekBar.max = durationAudio
        maxProgressText.changeText(getTimeByProgress(durationAudio))

        mediaPlayer?.setOnCompletionListener {
            pause()
            setDefaultProgressState()
        }

        if (isStartNow) {
            start()
        } else {
            updateActionDrawable(false)
        }
    }

    /**
     * для продолжения воспроизведения
     */
    private fun start() {
        mediaPlayer?.start()?.let {
            updateActionDrawable(true)
            startUpdatingProgressTimer()
        }
    }

    /**
     * для приостановки воспроизведения
     */
    fun pause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
        updateActionDrawable(false)
        stopUpdatingProgressTimer()
    }

    /**
     * Syncs the mediaPlayer position with seekbar via recurring task.
     */
    private fun startUpdatingProgressTimer() {
        stopUpdatingProgressTimer()

        if (tickerChannelForPlayerProgress == null)
            tickerChannelForPlayerProgress =
                ticker(delayMillis = 1_000, initialDelayMillis = 0)
        launchAtViewScope {
            tickerChannelForPlayerProgress?.let {
                for (event in it) {
                    Log.e("test", "tickerChannel $tickerChannelForPlayerProgress = $event")
                    syncProgressMedia()
                }
            }

        }
    }

    private fun stopUpdatingProgressTimer() {
        tickerChannelForPlayerProgress?.cancel()
        tickerChannelForPlayerProgress = null
    }

    private fun syncProgressMedia() {
        val playingPosition = mediaPlayer?.currentPosition ?: 0
        seekBar.progress = playingPosition
        currentProgressText.changeText(getTimeByProgress(playingPosition))
    }

    private fun setDefaultProgressState() {
        seekBar.progress = 0
        currentProgressText.changeText(getTimeByProgress(0))
    }

    private fun getTimeByProgress(progress: Int): String {
        return if (progress == 0) {
            "00:00"
        } else {
            return SimpleDateFormat("mm:ss", Locale.getDefault()).format(progress)
        }
    }
}