/**
 * Copyright (c) 2019 Dr. Florian Schmidt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drx.plugin.algebraictypes

import org.drx.plugin.algebraictypes.generate.duality.buildOpposeProductFunction
import org.drx.plugin.algebraictypes.generate.duality.buildOpposeSumFunction
import org.drx.plugin.algebraictypes.generate.duality.buildSimpleSumMeasureFunction
import org.drx.plugin.algebraictypes.generate.duality.buildSumMeasureFunction
import org.junit.Test

class DualityTest {

    @Test
    fun test() {

        println(buildOpposeProductFunction(4))

        println(buildOpposeSumFunction(4))

        println(buildSumMeasureFunction(4))

        println(buildSimpleSumMeasureFunction(4))
    }

}