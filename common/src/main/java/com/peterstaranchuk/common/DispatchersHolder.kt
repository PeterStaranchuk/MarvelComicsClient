package com.peterstaranchuk.common

import kotlin.coroutines.CoroutineContext

class DispatchersHolder(val io: CoroutineContext, val main: CoroutineContext)