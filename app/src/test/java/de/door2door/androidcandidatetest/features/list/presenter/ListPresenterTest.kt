package de.door2door.androidcandidatetest.features.list.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import de.door2door.androidcandidatetest.features.list.mapper.RouteModelMapper
import de.door2door.androidcandidatetest.features.list.view.ListView
import de.door2door.androidcandidatetest.interactor.MainInteractorImp
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ListPresenterTest {
    @Mock
    private lateinit var mapper: RouteModelMapper
    @Mock
    private lateinit var mainInteractor: MainInteractorImp
    @Mock
    private lateinit var listView: ListView

    private lateinit var listPresenter: ListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        listPresenter = ListPresenter(listView, mapper, mainInteractor)
    }

    @Test
    fun `should initialise recycler view`() {
        whenever(mainInteractor.getAllRoutes()).thenReturn(emptyList())

        listPresenter.onViewCreated()

        Mockito.verify(listView).initToolbar()
        Mockito.verify(listView).initRecyclerView(any())
    }
}