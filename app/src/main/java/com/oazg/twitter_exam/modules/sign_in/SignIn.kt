package com.oazg.twitter_exam.modules.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.databinding.ActivitySignInBinding
import com.oazg.twitter_exam.utils.SnackbarHelper
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession

class SignIn : AppCompatActivity(), SignInContracts.Presenter {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var router: SignInRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        router = SignInRouter(this)
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
                    router.presentSearchScreen()
                }

                override fun failure(exception: TwitterException?) {
                    SnackbarHelper.showErrorSnackbar(binding.root, exception?.message!!, Snackbar.LENGTH_LONG)
                }
            }
        } else {
            router.presentSearchScreen()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
