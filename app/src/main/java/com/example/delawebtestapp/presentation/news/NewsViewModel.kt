package com.example.delawebtestapp.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delawebtestapp.domain.entitys.News
import com.example.delawebtestapp.domain.usecases.GetNewsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsUseCase: GetNewsUseCase
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _news: MutableLiveData<News> = MutableLiveData()
    val news: LiveData<News> = _news

    fun getNews(index: Int) {
        compositeDisposable.add(
            newsUseCase.getNews(index).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { news ->
                    _news.value = news
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}