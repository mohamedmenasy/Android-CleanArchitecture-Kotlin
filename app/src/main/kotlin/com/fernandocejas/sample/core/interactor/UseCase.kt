/**
 * Copyright (C) 2019 Mohamed Naabil Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

  operator fun invoke(
    params: Params,
    onResult: (Either<Failure, Type>) -> Unit = {}
  ) {
    GlobalScope.launch(Dispatchers.Main) {
      val job = async(Dispatchers.IO) { run(params) }
      onResult(job.await())
    }
  }

  class None
}
