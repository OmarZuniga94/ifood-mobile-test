package com.oazg.twitter_exam.search_module

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.adapter.TweetsAdapter
import com.oazg.twitter_exam.databinding.ActivitySearchBinding
import com.oazg.twitter_exam.utils.AnalyzeTweet
import com.oazg.twitter_exam.utils.SnackbarHelper
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession

class SearchActivity : AppCompatActivity(), SearchContracts.Presenter, TextWatcher, SearchContracts.UsersItemClick,
        AdapterView.OnItemClickListener, TextView.OnEditorActionListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val iterator = SearchIterator(this)
    private val router = SearchRouter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.edtSearchUser.addTextChangedListener(this)
        binding.edtSearchUser.setOnEditorActionListener(this)
        binding.edtSearchUser.onItemClickListener = this
        binding.rcvUserList.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        binding.rcvUserList.addItemDecoration(object :
                DividerItemDecoration(binding.rcvUserList.context, DividerItemDecoration.VERTICAL) {})
        initTwitterButton()
    }

    override fun initTwitterButton() {
        if (!App.getPreferences().loadDataBoolean(R.string.prf_already_logged, false)) {
            binding.loginButton.callback = object : Callback<TwitterSession>() {
                override fun success(result: Result<TwitterSession>) {
                    SnackbarHelper.showSuccessSnackbar(
                            binding.root,
                            getString(R.string.success_login_twitter),
                            Snackbar.LENGTH_LONG
                    )
                    App.getPreferences().saveDataBool(R.string.prf_already_logged, true)
                    binding.loginButton.visibility = GONE
                    binding.edtSearchUser.visibility = VISIBLE
                    binding.rcvUserList.visibility = GONE
                }

                override fun failure(exception: TwitterException?) {
                    SnackbarHelper.showErrorSnackbar(binding.root, exception?.message!!, Snackbar.LENGTH_LONG)
                }
            }
        } else {
            binding.loginButton.visibility = GONE
            binding.edtSearchUser.visibility = VISIBLE
            binding.rcvUserList.visibility = GONE
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        iterator.searchUser(binding.edtSearchUser.text.toString())
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (iterator.usersList.size > 0) {
                binding.rcvUserList.visibility = VISIBLE
                iterator.displayTweets(iterator.usersList[0].id, this)
            } else {
                iterator.searchUser(binding.edtSearchUser.text.toString())
            }
        }
        return false
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.e(this.javaClass.simpleName, iterator.usersList[position].id.toString())
        binding.rcvUserList.visibility = VISIBLE
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        iterator.displayTweets(iterator.usersList[position].id, this)
    }

    override fun onUsersFound(users: Array<String?>) {
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, users)
        binding.edtSearchUser.setAdapter(adapter)
        adapter.notifyDataSetChanged()
        binding.rcvUserList.visibility = GONE
    }

    override fun onTweetsFound(adapter: TweetsAdapter) {
        binding.rcvUserList.visibility = VISIBLE
        binding.rcvUserList.adapter = adapter
    }

    override fun onTweetAnalyze(tweet: AnalyzeTweet) {
        router.displayAnalyzeDialog(tweet, supportFragmentManager)
    }

    override fun onSearchFailed(msg: String) {
        SnackbarHelper.showErrorSnackbar(binding.root, msg, Snackbar.LENGTH_SHORT)
    }

    override fun onAnalyzeFailed(msg: String) {
        SnackbarHelper.showErrorSnackbar(binding.root, msg, Snackbar.LENGTH_SHORT)
    }

    override fun onListItemClick(v: View, position: Int) {
        iterator.analyzeTweet(iterator.tweetsList[position])
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
