package com.tallerprogramacion.movieapp.ui.screen.tvShowTopRated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tallerprogramacion.movieapp.data.core.Resource
import com.tallerprogramacion.movieapp.domain.model.TvShow
import com.tallerprogramacion.movieapp.domain.usecases.GetTVShowTopRatedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TVShowViewModel(private val useCase: GetTVShowTopRatedUseCase):
    ViewModel() {

    private val _tvShowsState = MutableStateFlow<Resource<List< TvShow>>>(Resource.Loading)
    val tvShowsState: StateFlow<Resource<List<TvShow>>> get() = _tvShowsState

    fun fetchTVShowTopRated(){
        viewModelScope.launch {
            useCase.invoke().collect{ resource ->
                _tvShowsState.value = resource
            }
        }
    }
}