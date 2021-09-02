package com.example.delawebtestapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.example.delawebtestapp.data.NetworkRequestState
import com.example.delawebtestapp.domain.entitys.News
import com.example.delawebtestapp.domain.usecases.GetPageUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListNewsViewModel @Inject constructor(
    private val pageUseCase: GetPageUseCase
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _networkState: MutableLiveData<NetworkRequestState> = MutableLiveData()
    val networkState: LiveData<NetworkRequestState> = _networkState

    private val _listNews: MutableLiveData<MutableList<News>> = MutableLiveData()
    val listNews: LiveData<MutableList<News>> = _listNews

    private val _diffResult: MutableLiveData<DiffUtil.DiffResult> = MutableLiveData()
    val diffResult: LiveData<DiffUtil.DiffResult> = _diffResult

    fun getNextPage() {
        if (_networkState.value != NetworkRequestState.LOADING) {
            _networkState.value = NetworkRequestState.LOADING
            compositeDisposable.add(
                pageUseCase.getNextPage().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ listNews ->
                        addNews(listNews)
                        _networkState.value = NetworkRequestState.SUCCESS
                    }, {
                        _networkState.value = NetworkRequestState.ERROR
                    })
            )
        }
    }

    fun getListNews() {
        if (_networkState.value != NetworkRequestState.LOADING) {
            _networkState.value = NetworkRequestState.LOADING
            compositeDisposable.add(
                pageUseCase.getListNews().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _listNews.value = it.toMutableList()
                        _networkState.value = NetworkRequestState.SUCCESS
                    }, {
                        _networkState.value = NetworkRequestState.ERROR
                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun addNews(listNews: List<News>) {
        val newList = _listNews.value
        newList?.addAll(listNews)
        _listNews.value = newList
    }
}