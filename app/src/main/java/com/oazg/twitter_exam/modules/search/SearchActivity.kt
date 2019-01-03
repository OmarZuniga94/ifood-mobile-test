package com.oazg.twitter_exam.modules.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
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
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.adapter.TweetsAdapter
import com.oazg.twitter_exam.databinding.ActivitySearchBinding
import com.oazg.twitter_exam.utils.AnalyzeTweet
import com.oazg.twitter_exam.utils.SnackbarHelper

class SearchActivity : AppCompatActivity(), SearchContracts.Presenter, TextWatcher, SearchContracts.UsersItemClick,
        AdapterView.OnItemClickListener, TextView.OnEditorActionListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listAdapter: TweetsAdapter
    private val iterator = SearchIterator(this)
    private val router = SearchRouter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.edtSearchUser.setOnEditorActionListener(this)
        binding.edtSearchUser.onItemClickListener = this
        binding.rcvUserList.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        binding.rcvUserList.addItemDecoration(object :
                DividerItemDecoration(binding.rcvUserList.context, DividerItemDecoration.VERTICAL) {})
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
            if (iterator.usersList != null && iterator.usersList!!.size > 0) {
                iterator.displayTweets(iterator.usersList!![0].id, this)
            } else {
                iterator.searchUser(binding.edtSearchUser.text.toString())
            }
        }
        return false
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        iterator.displayTweets(iterator.usersList!![position].id, this)
    }

    override fun onUsersFound(users: Array<String?>) {
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, users)
        binding.edtSearchUser.setAdapter(adapter)
        adapter.notifyDataSetChanged()
    }

    override fun onTweetsFound(adapter: TweetsAdapter) {
        listAdapter = adapter
        binding.rcvUserList.adapter = listAdapter
    }

    override fun onTweetAnalyze(tweet: AnalyzeTweet) {
        router.displayAnalyzeDialog(tweet, listAdapter.getRowView())
    }

    override fun onSearchFailed(msg: String) {
        SnackbarHelper.showErrorSnackbar(binding.root, msg, Snackbar.LENGTH_SHORT)
    }

    override fun onAnalyzeFailed(msg: String) {
        SnackbarHelper.showErrorSnackbar(binding.root, msg, Snackbar.LENGTH_SHORT)
    }

    override fun onListItemClick(v: View, position: Int) {
        iterator.analyzeTweet(iterator.tweetsList!![position])
    }
}
