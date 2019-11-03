package com.guilhermembisotto.marvelplayground.ui.characterdetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.guilhermembisotto.core.base.BaseActivity
import com.guilhermembisotto.core.utils.CustomPagerTransformation
import com.guilhermembisotto.core.utils.extensions.openLink
import com.guilhermembisotto.core.utils.extensions.runTransition
import com.guilhermembisotto.data.characters.model.CharacterParcelable
import com.guilhermembisotto.marvelplayground.R
import com.guilhermembisotto.marvelplayground.databinding.ActivityCharacterDetailBinding
import com.guilhermembisotto.marvelplayground.ui.characterdetail.adapters.CharacterComicsAdapter
import com.guilhermembisotto.marvelplayground.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailActivity :
    BaseActivity<ActivityCharacterDetailBinding>(R.layout.activity_character_detail) {

    companion object {
        private const val dipValue = 80
    }

    private val viewModel: CharacterDetailViewModel by viewModel()
    private val comicsAdapter = CharacterComicsAdapter { _, comic ->
        viewModel.extractCorrectlyUrl(comic)?.run {
            openLink(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.viewModel = viewModel

        intent.extras?.run {
            this.getParcelable<CharacterParcelable>(MainActivity.CHARACTER_BUNDLE_PARCELABLE)
                ?.run {
                    title = this.name
                    viewModel.setCharacter(this)
                }
        }

        configureViewPager()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configureViewPager() {
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.page_margin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

        val customPagerTransformation = CustomPagerTransformation(
            dipValue,
            resources.displayMetrics,
            offsetPx,
            pageMarginPx,
            CustomPagerTransformation.Companion.AnimationType.TRANSLATION_Y
        )

        binding.vpCharacterDetailCarousel.apply {
            adapter = comicsAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            setPageTransformer(customPagerTransformation)
        }
    }

    /**
     *
     * All delay is just to prevent render's problems in UI
     * and make animations like fade and adjustments in position more fluid
     *
     * **/

    override fun subscribeUi() {
        super.subscribeUi()

        viewModel.contentVisibility.observe(this, Observer {
            launch {
                binding.lottieCharacterDetailLoading.runTransition(
                    duration = 500,
                    block = {
                        launch {
                            visibility = View.GONE
                            delay(300)
                        }
                    }, onCompletion = {
                        binding.materialCardViewCharacterDetailContent.runTransition {
                            visibility = if (it) View.VISIBLE else View.GONE
                        }
                    })
            }
        })

        viewModel.pageVisibility.observe(this, Observer {
            launch {
                binding.vpCharacterDetailCarousel.runTransition {
                    visibility = if (it) View.VISIBLE else View.GONE
                    binding.tvCharacterDetailComicsTitle.visibility =
                        if (it) View.VISIBLE else View.GONE
                }
            }
        })

        viewModel.character.observe(this, Observer {
            launch {
                viewModel.getCharacterComics(it.id)
            }
        })
    }
}