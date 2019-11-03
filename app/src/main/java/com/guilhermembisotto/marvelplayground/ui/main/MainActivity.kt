package com.guilhermembisotto.marvelplayground.ui.main

import android.os.Bundle
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import com.guilhermembisotto.core.base.BaseActivity
import com.guilhermembisotto.core.utils.extensions.launchActivityForSharedElements
import com.guilhermembisotto.data.State
import com.guilhermembisotto.marvelplayground.R
import com.guilhermembisotto.marvelplayground.databinding.ActivityMainBinding
import com.guilhermembisotto.marvelplayground.ui.characterdetail.CharacterDetailActivity
import com.guilhermembisotto.marvelplayground.ui.main.adapters.MainCharactersAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    companion object {
        const val CHARACTER_BUNDLE_PARCELABLE = "characterBundleParcelable"
    }

    private val viewModel: MainViewModel by viewModel()
    private val charactersAdapter: MainCharactersAdapter by lazy {

        MainCharactersAdapter { view, character ->
            character?.run {
                launchActivityForSharedElements<CharacterDetailActivity>(
                    Bundle().apply {
                        putParcelable(CHARACTER_BUNDLE_PARCELABLE, character.toParcelable())
                    },
                    Pair(
                        view,
                        getString(R.string.transition_name_character_photo)
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.mainRecyclerViewCharacters.adapter = charactersAdapter
    }

    override fun subscribeUi() {
        viewModel.characters.observe(this, Observer {
            launch {
                charactersAdapter.submitList(it)
            }
        })

        viewModel.state.observe(this, Observer {
            if (it == State.LOADING) {
                binding.lottieMainLoading.resumeAnimation()
            } else {
                binding.lottieMainLoading.pauseAnimation()
            }
        })
    }
}
