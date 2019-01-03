package com.oazg.twitter_exam.modules.analyze

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.databinding.ActivityAnalysisBinding
import com.oazg.twitter_exam.utils.AnalyzeTweet
import com.squareup.picasso.Picasso

const val BUNDLE_TWEET = "bundle_tweet"

class AnalysisActivity : AppCompatActivity(), AnalysisContracts.Presenter {

    private val SAD_SCORE = -1.0..-0.25
    private val NEUTRAL_SCORE = -0.25..0.25
    private val HAPPY_SCORE = 0.25..1.0
    private var tweetSelected: AnalyzeTweet? = null
    private lateinit var router: AnalysisRouter

    private lateinit var binding: ActivityAnalysisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_analysis)
        router = AnalysisRouter(this)
        intent?.let {
            tweetSelected = it.getParcelableExtra(BUNDLE_TWEET)
        }
        initToolbar()
        initViews()
    }

    override fun initToolbar() {
        setSupportActionBar(binding.toolbarAnalysis)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_back_arrow))
    }

    override fun initViews() {
        binding.includeRow.txtErrorSearch.visibility = View.GONE
        binding.includeRow.txtUserName.text = tweetSelected!!.name
        binding.includeRow.txtUserScreenName.text = "@".plus(tweetSelected!!.screenName)
        binding.includeRow.txtTweetDate.text = "Date".plus(tweetSelected!!.dateTweeted)
        binding.includeRow.txtUserTweet.text = tweetSelected!!.tweet
        Picasso.with(this).load(tweetSelected!!.urlPhoto).resize(40, 40)
            .into(binding.includeRow.imgUserPhoto)
        when (tweetSelected!!.score) {
            in SAD_SCORE -> {
                binding.imgEmoji.setImageResource(R.drawable.sad)
                binding.cslAnalysis.setBackgroundColor(resources.getColor(R.color.colorBlue))
            }
            in NEUTRAL_SCORE -> {
                binding.imgEmoji.setImageResource(R.drawable.neutral)
                binding.cslAnalysis.setBackgroundColor(resources.getColor(R.color.colorGray))
            }
            in HAPPY_SCORE -> {
                binding.imgEmoji.setImageResource(R.drawable.happy)
                binding.cslAnalysis.setBackgroundColor(resources.getColor(R.color.colorYellow))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> router.presentTweetsScreen()
        }
        return true
    }
}
