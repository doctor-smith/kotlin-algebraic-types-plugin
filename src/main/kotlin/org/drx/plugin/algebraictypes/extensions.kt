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

sealed class DimensionSelection {
    data class Single(val dimension: Int) : DimensionSelection()
    data class Range(val from: Int = 2, val to: Int) : DimensionSelection()
    data class List(val list: ArrayList<Int>) : DimensionSelection()
}

fun DimensionSelection.toList(): ArrayList<Int> = when (this) {
    is DimensionSelection.Single -> arrayListOf(dimension)
    is DimensionSelection.Range -> arrayListOf(*IntRange(from,to).toList().toTypedArray())
    is DimensionSelection.List -> list
}

/* TODO integrate in plugin structure*/
open class AlgebraicTypesExtension {
    var productTypes: DimensionSelection? = null
    var sumTypes: DimensionSelection? = null
    var dualities: DimensionSelection? = null
    var productTypeArithmetics : DimensionSelection? = null
    var evoleqSums : DimensionSelection? = null
    var evoleqProducts : DimensionSelection? = null

    var outputs: Outputs = Outputs()

}


open class DimensionSelectionExtension {
    var dimensionSelection: DimensionSelection? = null

    fun single(dimension: Int) {
        if(dimensionSelection != null) {
            throw Exception("Selection already set")
        }
        dimensionSelection = DimensionSelection.Single(dimension)
    }

    fun list(vararg dimensions: Int) {
        if(dimensionSelection != null) {
            throw Exception("Selection already set")
        }
        dimensionSelection = DimensionSelection.List(arrayListOf(*dimensions.toTypedArray()))
    }

    fun range(from: Int, to: Int) {
        if(dimensionSelection != null) {
            throw Exception("Selection already set")
        }
        dimensionSelection = DimensionSelection.Range(from,to)
    }
}
open class SingleDimensionSelectionExtension {
    var dimensionSelection: DimensionSelection.Single? = null

    fun dimension(dimension: Int) {
        if (dimensionSelection != null) {
            throw Exception("Selection already set")
        }
        dimensionSelection = DimensionSelection.Single(dimension)
    }
}

fun AlgebraicTypesExtension.products(configuration: DimensionSelectionExtension.()->Unit) {
    val extension = DimensionSelectionExtension()
    extension.configuration()
    productTypes = extension.dimensionSelection
}

fun AlgebraicTypesExtension.sums(configuration: DimensionSelectionExtension.()->Unit) {
    val extension = DimensionSelectionExtension()
    extension.configuration()
    sumTypes = extension.dimensionSelection
}

fun AlgebraicTypesExtension.productArithmetics(configuration: SingleDimensionSelectionExtension.()->Unit) {
    val extension = SingleDimensionSelectionExtension()
    extension.configuration()
    productTypeArithmetics = extension.dimensionSelection
}

fun AlgebraicTypesExtension.evoleqProducts(configuration: DimensionSelectionExtension.()->Unit) {
    val extension = DimensionSelectionExtension()
    extension.configuration()
    evoleqProducts = extension.dimensionSelection
}

fun AlgebraicTypesExtension.evoleqSums(configuration: DimensionSelectionExtension.()->Unit) {
    val extension = DimensionSelectionExtension()
    extension.configuration()
    evoleqSums = extension.dimensionSelection
}


fun AlgebraicTypesExtension.dualities(configuration: DimensionSelectionExtension.()->Unit) {
    val extension = DimensionSelectionExtension()
    extension.configuration()
    dualities = extension.dimensionSelection
}

data class Outputs(
        val compile: ArrayList<String> = arrayListOf("main"),
        val runtime: ArrayList<String> = arrayListOf("main"),
        val test: ArrayList<String> = arrayListOf("main")
)

open class OutputExtension {
    val compile = arrayListOf<String>("main")
    val runtime = arrayListOf<String>("main")
    val test = arrayListOf<String>("main")


    fun compile(sourceSet: String) {
        compile.add(sourceSet)
    }
    fun runtime(sourceSet: String) {
        runtime.add(sourceSet)
    }
    fun test(sourceSet: String) {
        test.add(sourceSet)
    }

    fun excludeMain(from: ArrayList<String>) {
        from.forEach {
            if(it == "compile") {
                compile.remove(it)
            }
            if(it == "runtime") {
                runtime.remove(it)
            }
            if(it == "test") {
                test.remove(it)
            }
        }
    }
}


fun AlgebraicTypesExtension.outputs(configuration: OutputExtension.()->Unit) {
    val extension = OutputExtension()
    extension.configuration()

    outputs = Outputs(
            extension.compile,
            extension.runtime,
            extension.test
    )
}
