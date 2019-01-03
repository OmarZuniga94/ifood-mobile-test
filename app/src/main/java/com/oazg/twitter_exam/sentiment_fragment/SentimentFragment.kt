package com.oazg.twitter_exam.sentiment_fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.databinding.FragmentSentimentBinding
import com.oazg.twitter_exam.utils.AnalyzeTweet

private const val BUNDLE_TWEET = "bundle_tweet"

class SentimentFragment : DialogFragment(), View.OnClickListener {

    private val SAD_SCORE = -1.0..-0.25
    private val NEUTRAL_SCORE = -0.25..0.25
    private val HAPPY_SCORE = 0.25..1.0
    private var tweetSelected: AnalyzeTweet? = null

    private lateinit var binding: FragmentSentimentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tweetSelected = it.getParcelable(BUNDLE_TWEET)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sentiment, container, false)
        binding.imgCloseDialog.setOnClickListener(this)
        initDialog()
        dialog!!.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.imgCloseDialog.id -> dismiss()
        }
    }

    private fun initDialog() {
        binding.includeRow.cdvUserPhoto.visibility = INVISIBLE
        binding.includeRow.txtErrorSearch.visibility = GONE
        binding.includeRow.txtUserName.text = tweetSelected!!.name
        binding.includeRow.txtUserScreenName.text = "@".plus(tweetSelected!!.screenName)
        binding.includeRow.txtTweetDate.text = tweetSelected!!.dateTweeted
        binding.includeRow.txtUserTweet.text = tweetSelected!!.tweet
        when (tweetSelected!!.score) {
            in SAD_SCORE -> {
                binding.imgEmoji.setImageResource(R.drawable.sad)
                binding.cslSentimentalDialog.setBackgroundColor(resources.getColor(R.color.colorBlue))
            }
            in NEUTRAL_SCORE -> {
                binding.imgEmoji.setImageResource(R.drawable.neutral)
                binding.cslSentimentalDialog.setBackgroundColor(resources.getColor(R.color.colorGray))
            }
            in HAPPY_SCORE -> {
                binding.imgEmoji.setImageResource(R.drawable.happy)
                binding.cslSentimentalDialog.setBackgroundColor(resources.getColor(R.color.colorYellow))
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 [AnalyzeTweet] Parameter 1.
         * @return A new instance of fragment SentimentFragment.
         */
        @JvmStatic
        fun newInstance(param1: AnalyzeTweet) =
                SentimentFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(BUNDLE_TWEET, param1)
                    }
                }
    }
}
