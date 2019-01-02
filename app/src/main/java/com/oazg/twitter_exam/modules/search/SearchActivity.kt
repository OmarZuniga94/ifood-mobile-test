package com.oazg.twitter_exam.modules.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.databinding.ActivitySearchBinding
import com.oazg.twitter_exam.utils.SnackbarHelper
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.Result
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchContracts.Presenter, TextWatcher, SearchContracts.UsersItemClick,
    AdapterView.OnItemClickListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val iterator = SearchIteractor(this)
    private val router = SearchRouter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.edtSearchUser.addTextChangedListener(this)
        binding.edtSearchUser.onItemClickListener = this
        binding.rcvUserList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
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
                    binding.rcvUserList.visibility = VISIBLE
                }

                override fun failure(exception: TwitterException?) {
                    SnackbarHelper.showErrorSnackbar(binding.root, exception?.message!!, Snackbar.LENGTH_LONG)
                }
            }
        } else {
            binding.loginButton.visibility = GONE
            binding.edtSearchUser.visibility = VISIBLE
            binding.rcvUserList.visibility = VISIBLE
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        iterator.searchUser(edt_search_user.text.toString())
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.e(this.javaClass.simpleName, iterator.usersList[position].id.toString())
        iterator.displayTweets(iterator.usersList[position].id, this)
    }

    override fun onUsersFound(users: Array<String?>) {
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, users)
        binding.edtSearchUser.setAdapter(adapter)
        adapter.notifyDataSetChanged()
    }

    override fun onUserNotFound() {
        Log.e(this.javaClass.simpleName, "Usuario NO encontrado")
    }

    override fun onTweetsFound(adapter: TweetsAdapter) {
        binding.rcvUserList.adapter = adapter
    }

    override fun onTweetsNotFound() {
        Log.e(this.javaClass.simpleName, "Tweets NO encontrados")
    }

    override fun onListItemClick(v: View, position: Int) {
        Log.e(this.javaClass.simpleName, iterator.usersList[position].id.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
