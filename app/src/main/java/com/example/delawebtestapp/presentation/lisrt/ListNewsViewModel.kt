package com.example.delawebtestapp.presentation.lisrt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.example.delawebtestapp.data.NetworkRequestState
import com.example.delawebtestapp.domain.entitys.News
import com.example.delawebtestapp.domain.usecases.GetPageUseCase
import com.example.delawebtestapp.presentation.factory.DiffCallback
import io.reactivex.Single
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

    init {
        _listNews.value = mutableListOf()
    }

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
                        setNews(it)
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
        val lastList = _listNews.value?.toList()
        val newList = _listNews.value
        newList?.addAll(listNews)
        _listNews.value = newList
        setDiffUtil(lastList!!, newList!!)
    }

    private fun setNews(newList: List<News>) {
        val lastList = _listNews.value
        _listNews.value = newList.toMutableList()
        setDiffUtil(lastList!!, newList)
    }

    //DiffUtill в поток
    private fun setDiffUtil(lastList: List<News>, newList: List<News>) {
        compositeDisposable.add(
            Single.just(DiffUtil.calculateDiff(DiffCallback(lastList, newList)))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    _diffResult.value = result
                }
        )
    }
}