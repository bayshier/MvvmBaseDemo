package com.module.me.activity

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lib.base.mvvm.viewmodel.BaseRefreshViewModel
import com.lib.net.DataRepository
import com.lib.net.DataRepositorySource
import com.lib.net.dto.Resource
import com.lib.net.local.entity.UserTestRoom
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *
 */
class RoomTestViewModel(state: SavedStateHandle) :
    BaseRefreshViewModel<UserTestRoom>() {

    private val savedStateHandle = state

    var showEmpty = ObservableField(false)

    private val dataRepositoryRepository: DataRepositorySource = DataRepository()

    private val userTestRoomLiveDataPrivate = MutableLiveData<Resource<List<UserTestRoom>>>()
    val userTestRoomLiveData: LiveData<Resource<List<UserTestRoom>>> get() = userTestRoomLiveDataPrivate

    fun insertUserTestRoom(userTestRoom: UserTestRoom) {
        viewModelScope.launch {
            dataRepositoryRepository.insertUserTestRoom(userTestRoom).collect {
                showToastMessage("插入数据成功$it")
            }
        }
    }

    fun deleteUserTestRoom(userTestRoom: UserTestRoom) {
        viewModelScope.launch {
            dataRepositoryRepository.removeUserTestRoom(userTestRoom).collect {
                showToastMessage("删除数据成功$it")
                refreshData()
            }
        }
    }

    private fun getUserTestRoom() {
        viewModelScope.launch {
            dataRepositoryRepository.getAllUserTestRoom().collect {
                userTestRoomLiveDataPrivate.value = it
                postStopRefreshEvent()
            }
        }
    }

    private fun showToastMessage(msg: String) {
        postShowToastViewEvent(msg)
    }

    override fun refreshData() {
        getUserTestRoom()
    }

    override fun loadMore() {

    }
}