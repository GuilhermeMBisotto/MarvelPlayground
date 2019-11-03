package com.guilhermembisotto.marvelplayground.ui.splash

import android.os.Bundle
import android.view.View
import androidx.core.util.Pair
import com.guilhermembisotto.core.base.BaseActivity
import com.guilhermembisotto.core.utils.extensions.launchActivityForSharedElements
import com.guilhermembisotto.marvelplayground.R
import com.guilhermembisotto.marvelplayground.databinding.ActivitySplashBinding
import com.guilhermembisotto.marvelplayground.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch {
            delay(1_000)

            launchActivityForSharedElements<MainActivity>(
                null,
                Pair(
                    binding.imgSplashMarvelComicsLogo as View,
                    getString(R.string.transition_name_splash)
                )
            )
        }
    }
}