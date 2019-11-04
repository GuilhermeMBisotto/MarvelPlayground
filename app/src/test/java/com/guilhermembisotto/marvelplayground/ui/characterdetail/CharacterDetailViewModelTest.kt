package com.guilhermembisotto.marvelplayground.ui.characterdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.guilhermembisotto.data.characters.contract.CharactersRepository
import com.guilhermembisotto.data.characters.model.Character
import com.guilhermembisotto.data.characters.model.CharacterParcelable
import com.guilhermembisotto.data.characters.model.Comic
import com.guilhermembisotto.data.characters.model.Comics
import com.guilhermembisotto.data.characters.model.Events
import com.guilhermembisotto.data.characters.model.Resource
import com.guilhermembisotto.data.characters.model.Series
import com.guilhermembisotto.data.characters.model.Stories
import com.guilhermembisotto.data.characters.model.Thumbnail
import com.guilhermembisotto.data.characters.model.Url
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class CharacterDetailViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: CharactersRepository
    private lateinit var viewModel: CharacterDetailViewModel
    private val CHARACTER_ID = 1010699
    private val testCharacterParcelable = CharacterParcelable(
        id = CHARACTER_ID,
        name = "",
        thumbnail = Thumbnail(
            path = "",
            extension = ""
        )
    )
    private val testCharacter = arrayListOf(
        Character(
            id = 1010699,
            name = "Aaron Stack",
            description = "",
            modified = "1969-12-31T19:00:00-0500",
            thumbnail = Thumbnail(
                path = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available",
                extension = "jpg"
            ),
            resourceURI = "http://gateway.marvel.com/v1/public/characters/1010699",
            comics = Comics(
                available = 1,
                collectionURI = "http://gateway.marvel.com/v1/public/characters/1010699/comics",
                items = arrayListOf(
                    Resource(
                        resourceURI = "http://gateway.marvel.com/v1/public/comics/40776",
                        name = "Dark Avengers (2012) #177",
                        type = ""
                    )
                ),
                returned = 1
            ),
            series = Series(
                available = 1,
                collectionURI = "http://gateway.marvel.com/v1/public/characters/1010699/series",
                items = arrayListOf(
                    Resource(
                        resourceURI = "http://gateway.marvel.com/v1/public/series/789",
                        name = "Dark Avengers (2012 - 2013)",
                        type = ""
                    )
                )
            ),
            stories = Stories(
                available = 1,
                collectionURI = "http://gateway.marvel.com/v1/public/characters/1010699/stories",
                items = arrayListOf(
                    Resource(
                        resourceURI = "http://gateway.marvel.com/v1/public/stories/25634",
                        name = "Universe X (2000) #10",
                        type = "cover"
                    )
                ),
                returned = 1
            ),
            events = Events(
                available = 0,
                collectionURI = "http://gateway.marvel.com/v1/public/characters/1010699/events",
                items = arrayListOf(),
                returned = 0
            ),
            urls = arrayListOf(
                Url(
                    type = "detail",
                    url = "http://marvel.com/comics/characters/1010699/aaron_stack?utm_campaign=apiRef&utm_source=06e34ce4d8d5e5418a1077ffa5015f09"
                )
            )
        )
    )

    private val testComics = arrayListOf(
        Comic(
            id = 37193,
            title = "Heroes for Hire (2010) #11",
            thumbnail = Thumbnail(
                path = "http://i.annihil.us/u/prod/marvel/i/mg/2/f0/56438c33ab080",
                extension = "jpg"
            ),
            urls = arrayListOf(
                Url(
                    type = "detail",
                    url = "http://marvel.com/comics/issue/37193/heroes_for_hire_2010_11?utm_campaign=apiRef&utm_source=06e34ce4d8d5e5418a1077ffa5015f09"
                )
            )
        )
    )

    @Before
    fun setUp() {

        repository = mock()

        viewModel = CharacterDetailViewModel(repository)

        whenever(runBlocking {
            repository.getCharacter(CHARACTER_ID)
        }).thenReturn(
            testCharacter
        )

        whenever(runBlocking {
            repository.getCharacterComics(CHARACTER_ID)
        }).thenReturn(
            testComics
        )
    }

    @Test
    fun `set character, when set to character, then set in liveData`() {
        runBlocking {

            viewModel.setCharacter(
                CharacterParcelable(
                    id = CHARACTER_ID,
                    name = "",
                    thumbnail = Thumbnail(
                        path = "",
                        extension = ""
                    )
                ),
                hasInternet = true
            )

            assertEquals(viewModel.characterParcelable.getOrAwaitValue(), testCharacterParcelable)
        }
    }

    @Test
    fun `get character detail, when requested to character detail, then set in liveData`() {
        runBlocking {

            viewModel.setCharacter(
                CharacterParcelable(
                    id = CHARACTER_ID,
                    name = "",
                    thumbnail = Thumbnail(
                        path = "",
                        extension = ""
                    )
                ),
                hasInternet = true
            )

            assertEquals(viewModel.character.getOrAwaitValue(), testCharacter.first())
            assertEquals(viewModel.contentVisibility.getOrAwaitValue(), true)
        }
    }

    @Test
    fun `get character detail, when requested to character detail, then set in error liveData`() {
        runBlocking {

            viewModel.setCharacter(
                CharacterParcelable(
                    id = 11,
                    name = "",
                    thumbnail = Thumbnail(
                        path = "",
                        extension = ""
                    )
                ),
                hasInternet = true
            )

            assertEquals(viewModel.error.getOrAwaitValue(), true)
            assertEquals(viewModel.contentVisibility.getOrAwaitValue(), false)
        }
    }

    @Test
    fun `get character comics, when requested to character comics, then set in liveData`() {
        runBlocking {

            viewModel.getCharacterComics(CHARACTER_ID)

            assertEquals(viewModel.comics.getOrAwaitValue(), testComics)
            assertEquals(viewModel.pageVisibility.getOrAwaitValue(), true)
        }
    }

    @Test
    fun `set internet access true, when have internet, then set true in viewModel`() {
        runBlocking {
            val vm: CharacterDetailViewModel = mock()
            vm.setHasInternet(true)

            verify(vm, times(1)).setHasInternet(true)
        }
    }

    @Test
    fun `set internet access false, when not have internet, then set false in viewModel`() {
        runBlocking {
            val vm: CharacterDetailViewModel = mock()
            vm.setHasInternet(false)

            verify(vm, times(1)).setHasInternet(false)
        }
    }

    /**
     * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
     *
     * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
     * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
     */
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    /**
     * Observes a [LiveData] until the `block` is done executing.
     */
    fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
        val observer = Observer<T> { }
        try {
            observeForever(observer)
            block()
        } finally {
            removeObserver(observer)
        }
    }
}