package com.peterstaranchuk.marvelheroesapp.dependency_injection

import com.peterstaranchuk.common.DispatchersHolder
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule: Module = module {
    factory(named("dispatcher_io")) { Dispatchers.IO }
    factory(named("dispatcher_main")) { Dispatchers.Main }
    factory { DispatchersHolder(io = get(named("dispatcher_io")), main = get(named("dispatcher_main"))) }
}
