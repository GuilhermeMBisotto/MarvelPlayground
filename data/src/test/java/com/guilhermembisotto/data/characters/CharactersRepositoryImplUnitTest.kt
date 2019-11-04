package com.guilhermembisotto.data.characters

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.guilhermembisotto.data.characters.contract.CharactersRepository
import com.guilhermembisotto.data.characters.model.Character
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
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharactersRepositoryImplUnitTest {

    private lateinit var charactersRepositoryImplMock: CharactersRepository
    private val CHARACTER_ID = 1010699

    @Before
    fun setUp() {
        charactersRepositoryImplMock = mock()
    }

    @Test
    fun `Get character detail, when it is requested to obtain character detail, then return character detail`() {
        runBlocking {

            // ARRANGE
            val expectedCharacters = arrayListOf<Character>()
            expectedCharacters.add(
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

            whenever(charactersRepositoryImplMock.getCharacter(CHARACTER_ID)).thenReturn(
                expectedCharacters
            )

            // ACT
            val characters = charactersRepositoryImplMock.getCharacter(CHARACTER_ID)

            // ASSERT
            verify(charactersRepositoryImplMock, times(1)).getCharacter(CHARACTER_ID).apply {
                print("expectedCharacters: $expectedCharacters -------- characters: $characters")
                assertEquals(expectedCharacters, characters)

                assertEquals(expectedCharacters[0].id, characters!![0].id)
                assertEquals(expectedCharacters[0].name, characters[0].name)
                assertEquals(expectedCharacters[0].description, characters[0].description)
                assertEquals(expectedCharacters[0].modified, characters[0].modified)
                assertEquals(expectedCharacters[0].resourceURI, characters[0].resourceURI)

                assertEquals(expectedCharacters[0].thumbnail, characters[0].thumbnail)
                assertEquals(
                    expectedCharacters[0].thumbnail.extension,
                    characters[0].thumbnail.extension
                )
                assertEquals(expectedCharacters[0].thumbnail.path, characters[0].thumbnail.path)
                assertEquals(
                    expectedCharacters[0].thumbnail.urlPath(),
                    characters[0].thumbnail.urlPath()
                )

                assertEquals(expectedCharacters[0].comics, characters[0].comics)
                assertEquals(
                    expectedCharacters[0].comics.available,
                    characters[0].comics.available
                )
                assertEquals(
                    expectedCharacters[0].comics.collectionURI,
                    characters[0].comics.collectionURI
                )
                assertEquals(
                    expectedCharacters[0].comics.returned,
                    characters[0].comics.returned
                )
                assertEquals(expectedCharacters[0].comics.items, characters[0].comics.items)
                assertEquals(
                    expectedCharacters[0].comics.items[0].type,
                    characters[0].comics.items[0].type
                )
                assertEquals(
                    expectedCharacters[0].comics.items[0].resourceURI,
                    characters[0].comics.items[0].resourceURI
                )
                assertEquals(
                    expectedCharacters[0].comics.items[0].name,
                    characters[0].comics.items[0].name
                )

                assertEquals(expectedCharacters[0].series, characters[0].series)
                assertEquals(
                    expectedCharacters[0].series.available,
                    characters[0].series.available
                )
                assertEquals(
                    expectedCharacters[0].series.collectionURI,
                    characters[0].series.collectionURI
                )
                assertEquals(expectedCharacters[0].series.items, characters[0].series.items)
                assertEquals(
                    expectedCharacters[0].series.items[0].type,
                    characters[0].series.items[0].type
                )
                assertEquals(
                    expectedCharacters[0].series.items[0].resourceURI,
                    characters[0].series.items[0].resourceURI
                )
                assertEquals(
                    expectedCharacters[0].series.items[0].name,
                    characters[0].series.items[0].name
                )

                assertEquals(expectedCharacters[0].stories, characters[0].stories)
                assertEquals(
                    expectedCharacters[0].stories.available,
                    characters[0].stories.available
                )
                assertEquals(
                    expectedCharacters[0].stories.collectionURI,
                    characters[0].stories.collectionURI
                )
                assertEquals(
                    expectedCharacters[0].stories.returned,
                    characters[0].stories.returned
                )
                assertEquals(expectedCharacters[0].stories.items, characters[0].stories.items)
                assertEquals(
                    expectedCharacters[0].stories.items[0].type,
                    characters[0].stories.items[0].type
                )
                assertEquals(
                    expectedCharacters[0].stories.items[0].resourceURI,
                    characters[0].stories.items[0].resourceURI
                )
                assertEquals(
                    expectedCharacters[0].stories.items[0].name,
                    characters[0].stories.items[0].name
                )

                assertEquals(expectedCharacters[0].events, characters[0].events)
                assertEquals(
                    expectedCharacters[0].events.available,
                    characters[0].events.available
                )
                assertEquals(
                    expectedCharacters[0].events.collectionURI,
                    characters[0].events.collectionURI
                )
                assertEquals(expectedCharacters[0].events.items, characters[0].events.items)
                assertEquals(expectedCharacters[0].events.returned, characters[0].events.returned)

                assertEquals(expectedCharacters[0].urls, characters[0].urls)
                assertEquals(expectedCharacters[0].urls[0].type, characters[0].urls[0].type)
                assertEquals(expectedCharacters[0].urls[0].url, characters[0].urls[0].url)

            }
        }
    }

    @Test
    fun `Get character detail, when it is requested to obtain character detail, then return none`() {
        runBlocking {
            // ARRANGE
            val expectedCharacters = arrayListOf<Character>()

            whenever(charactersRepositoryImplMock.getCharacter(CHARACTER_ID)).thenReturn(
                expectedCharacters
            )

            // ACT
            val characters = charactersRepositoryImplMock.getCharacter(CHARACTER_ID)

            // ASSERT
            verify(charactersRepositoryImplMock, times(1)).getCharacter(CHARACTER_ID).apply {
                print("expectedCharacters: $expectedCharacters -------- characters: $characters")
                assertEquals(expectedCharacters, characters)
            }
        }
    }

    @Test
    fun `Get character comics, when it is requested to obtain character comics, then return character comics`() {
        runBlocking {

            // ARRANGE
            val expectedComics = arrayListOf<Comic>()
            expectedComics.add(
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

            whenever(charactersRepositoryImplMock.getCharacterComics(CHARACTER_ID)).thenReturn(
                expectedComics
            )

            // ACT
            val comics = charactersRepositoryImplMock.getCharacterComics(CHARACTER_ID)

            // ASSERT
            verify(charactersRepositoryImplMock, times(1)).getCharacterComics(CHARACTER_ID).apply {
                print("expectedComics: $expectedComics -------- comics: $comics")
                assertEquals(expectedComics, comics)

                assertEquals(expectedComics[0].urls, comics!![0].urls)

                assertEquals(expectedComics[0].urls[0].type, comics[0].urls[0].type)
                assertEquals(expectedComics[0].urls[0].url, comics[0].urls[0].url)


                assertEquals(expectedComics[0].thumbnail.urlPath(), comics[0].thumbnail.urlPath())
                assertEquals(expectedComics[0].thumbnail.path, comics[0].thumbnail.path)
                assertEquals(expectedComics[0].thumbnail.extension, comics[0].thumbnail.extension)

                assertEquals(expectedComics[0].title, comics[0].title)
                assertEquals(expectedComics[0].id, comics[0].id)
            }
        }
    }

    @Test
    fun `Get character comics, when it is requested to obtain character comics, then return none`() {
        runBlocking {
            // ARRANGE
            val expectedComics = arrayListOf<Comic>()

            whenever(charactersRepositoryImplMock.getCharacterComics(CHARACTER_ID)).thenReturn(
                expectedComics
            )

            // ACT
            val comics = charactersRepositoryImplMock.getCharacterComics(CHARACTER_ID)

            // ASSERT
            verify(charactersRepositoryImplMock, times(1)).getCharacterComics(CHARACTER_ID).apply {
                print("expectedComics: $expectedComics -------- comics: $comics")
                assertEquals(expectedComics, comics)
            }
        }
    }

    @Test
    fun `Get initializedPagedListBuilder, when it is requested to obtain a LivePagedListBuilder with type Int and Character, then return something`() {
        runBlocking {
            // ARRANGE
            val PAGE_SIZE = 20
            val dataSourceFactory: CharactersDataSourceFactory = mock()
            val config: PagedList.Config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build()
            val expectedLivePagedListBuilder: LivePagedListBuilder<Int, Character> =
                LivePagedListBuilder(dataSourceFactory, config)

            whenever(charactersRepositoryImplMock.initializedPagedListBuilder(config)).thenReturn(
                expectedLivePagedListBuilder
            )

            // ACT
            val livePagedListBuilder =
                charactersRepositoryImplMock.initializedPagedListBuilder(config)

            // ASSERT
            verify(charactersRepositoryImplMock, times(1)).initializedPagedListBuilder(config)
                .apply {
                    print("expectedLivePagedListBuilder: $expectedLivePagedListBuilder -------- livePagedListBuilder: $livePagedListBuilder")
                    assertEquals(expectedLivePagedListBuilder, livePagedListBuilder)
                }
        }
    }

    @Test
    fun `Get initializedPagedListBuilder, when it is requested to obtain a LivePagedListBuilder with type Int and Character, then return null`() {
        runBlocking {
            // ARRANGE
            val expectedLivePagedListBuilder: LivePagedListBuilder<Int, Character> = mock()
            val config: PagedList.Config = mock()

            whenever(charactersRepositoryImplMock.initializedPagedListBuilder(config)).thenReturn(
                expectedLivePagedListBuilder
            )

            // ACT
            val livePagedListBuilder =
                charactersRepositoryImplMock.initializedPagedListBuilder(config)

            // ASSERT
            verify(charactersRepositoryImplMock, times(1)).initializedPagedListBuilder(config)
                .apply {
                    print("expectedLivePagedListBuilder: $expectedLivePagedListBuilder -------- livePagedListBuilder: $livePagedListBuilder")
                    print("expectedLivePagedListBuilder.Build(): ${expectedLivePagedListBuilder.build()} -------- livePagedListBuilder.Build(): ${livePagedListBuilder.build()}")
                    assertEquals(expectedLivePagedListBuilder, livePagedListBuilder)
                    assertEquals(expectedLivePagedListBuilder.build(), livePagedListBuilder.build())
                }
        }
    }
}