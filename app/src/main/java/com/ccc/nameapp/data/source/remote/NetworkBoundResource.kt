package com.ccc.nameapp.data.source.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundResource<ResultType> {

    private val result: Flowable<Resource<ResultType>>

    init {
        val diskObservable = Flowable.defer {
            loadFromLocalDb().subscribeOn(Schedulers.io())
        }

        val networkObservable = Flowable.defer {
            loadFromNetwork().subscribeOn(Schedulers.io())
        }

        result = diskObservable
            .flatMap { result ->
                networkObservable
                    .observeOn(Schedulers.computation())
                    .doOnNext { handleBeforeSave(it) }
                    .flatMap {
                        saveToLocalDb(it).andThen(Flowable.just(Resource.success(it)))
                    }.startWith(Resource.loading(result))
            }.onErrorReturn { Resource.error(null, it.message) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun emit(): Flowable<Resource<ResultType>> {
        return result
    }

    protected abstract fun saveToLocalDb(data: ResultType): Completable

    protected abstract fun handleBeforeSave(data: ResultType)

    protected abstract fun loadFromLocalDb(): Flowable<ResultType>

    protected abstract fun loadFromNetwork(): Flowable<ResultType>
}
