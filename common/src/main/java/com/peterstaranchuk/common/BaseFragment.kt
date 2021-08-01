package com.peterstaranchuk.common

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment(), KoinComponent, CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

}