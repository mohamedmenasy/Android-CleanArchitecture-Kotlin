/*
 * *
 *  * Created by Mohamed Nabil on 12/10/18 3:20 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 12/10/18 11:41 AM
 *
 */

package com.fernandocejas.sample.core.interactor

import com.fernandocejas.sample.core.exception.Failure
import com.fernandocejas.sample.core.functional.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        GlobalScope.launch(Dispatchers.Main) {
            val job = async(Dispatchers.IO) { run(params) }
            onResult(job.await())
        }
    }

    class None
}
