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
package org.drx.plugin.algebraictypes.extension

// TODO optimize dsl

/**
 * Data
 * ====================================================================================================================
 */


/**
 * Collect all types of class representations
 */
open class DataClasses {
    val dataClasses : ArrayList<DataClass> = arrayListOf()
    val sealedClasses : ArrayList<SealedClass> = arrayListOf()
    val classes : ArrayList<Class> = arrayListOf()
    val objects : ArrayList<Object> = arrayListOf()
    val interfaces : ArrayList<Object> = arrayListOf()
}


interface ClassRepresentation {
    var name: String
    var packageName: String
    /**
     * Ex: src/main/kotlin
     */
    var sourceFolder: String

    /**
     * Parameters of the class
     */
    val parameters: ArrayList<Parameter>
    /**
     * Comment
     */
    val comment: ArrayList<String>
    /**
     * Setters of parameters may require a postfix to avoid platform declasration clashh
     */
    var settersPostFix: String
    /**
     * The class is serializable?
     */
    var serializable: Boolean
    /**
     * Serialization type
     */
    var serializationType: SerializationType

}

/**
 * Representation of a data class
 */
open class DataClass : ClassRepresentation {
    override lateinit var name: String
    override var packageName: String = ""
    override var sourceFolder: String = ""
    override val parameters: ArrayList<Parameter> = arrayListOf()
    override val comment: ArrayList<String> = arrayListOf()
    override var settersPostFix: String = ""
    override var serializable: Boolean = false
    override var serializationType: SerializationType = SerializationType.NotSerializable

}
/**
 * Representation of an object
 */
open class Object : ClassRepresentation {
    override lateinit var name: String
    override var packageName: String = ""
    override var sourceFolder: String = ""
    override val parameters: ArrayList<Parameter> = arrayListOf()
    override val comment: ArrayList<String> = arrayListOf()
    override var settersPostFix: String = ""
    override var serializable: Boolean = false
    override var serializationType: SerializationType = SerializationType.NotSerializable
}
/**
 * Representation of an interface
 */
open class Interface : ClassRepresentation {
    override lateinit var name: String
    override var packageName: String = ""
    override var sourceFolder: String = ""
    override val parameters: ArrayList<Parameter> = arrayListOf()
    override val comment: ArrayList<String> = arrayListOf()
    override var settersPostFix: String = ""
    override var serializable: Boolean = false
    override var serializationType: SerializationType = SerializationType.NotSerializable
}
/**
 * Representation of a class
 */
open class Class : ClassRepresentation {
    override lateinit var name: String
    override var packageName: String = ""
    override var sourceFolder: String = ""
    override val parameters: ArrayList<Parameter> = arrayListOf()
    override val comment: ArrayList<String> = arrayListOf()
    override var settersPostFix: String = ""
    override var serializable: Boolean = false
    override var serializationType: SerializationType = SerializationType.NotSerializable
}

/**
 * Representation of a sealed
 */
open class SealedClass : ClassRepresentation {
    override lateinit var name: String
    override var packageName: String = ""
    override var sourceFolder: String = ""
    override val parameters: ArrayList<Parameter> = arrayListOf()
    val representatives: ArrayList<SubClass> = arrayListOf()
    override val comment: ArrayList<String> = arrayListOf()
    override var settersPostFix: String = ""
    override var serializable: Boolean = false
    override var serializationType: SerializationType = SerializationType.NotSerializable
}

/**
 * Representation of a sub class
 */
open class SubClass(open val parent: ClassRepresentation) : ClassRepresentation {
    override lateinit var name: String
    override var packageName: String = ""
    override var sourceFolder: String = ""
    override val parameters: ArrayList<Parameter> = arrayListOf()
    override val comment: ArrayList<String> = arrayListOf()
    /**
     * Override parameters of the parent class
     */
    val overrideParameters: ArrayList<String> = arrayListOf()
    /**
     * Provide default values for parameters of the parent class
     */
    val defaultValuesSet: HashMap<String, String> = hashMapOf()
    override var settersPostFix: String = ""
    override var serializable: Boolean = false
    override var serializationType: SerializationType = SerializationType.NotSerializable
}

/**
 * Representation of a sub object
 */
open class SubObject(override val parent: ClassRepresentation) : SubClass(parent)
/**
 * Representation of a sub data class
 */
open class SubDataClass(override val parent: ClassRepresentation) : SubClass(parent)
/**
 * Representation of a sub sealed class
 */
open class SubSealedClass(override val parent: ClassRepresentation) : SubClass(parent) {
    val representatives: ArrayList<SubClass> = arrayListOf()
}

/**
 * Parameter representation
 */
open class Parameter {
    lateinit var name: String
    lateinit var type: ParameterType
    var defaultValue: String? = null
    val modifiers: ArrayList<String> = arrayListOf()
    val comment: ArrayList<String> = arrayListOf()
}

open class ParameterType {
    lateinit var name: String
    var packageName: String = ""
    var isGeneric: Boolean = false
    var genericIn: String = ""
    var serializable: Boolean = false
    var serializationType: SerializationType = SerializationType.NotSerializable
    val dependencies: ArrayList<ParameterTypeDependency> = arrayListOf()
}

open class ParameterTypeDependency(
        val name:String,
        val packageName : String,
        var serializable: Boolean = false,
        var serializationType: SerializationType = SerializationType.NotSerializable
)
sealed class SerializationType {
    object Serializable : SerializationType()
    object Transient : SerializationType()
    object Polymorphic : SerializationType()
    object NotSerializable: SerializationType()
}


/**
 * Extension functions
 * ====================================================================================================================
 */

fun AlgebraicTypesExtension.dataClasses(configuration: DataClasses.()->Unit) {

    val dataClasses = DataClasses()
    dataClasses.configuration()
    this.dataClasses = dataClasses
}

fun DataClasses.dataClass(configuration: DataClass.()->Unit) {
    val dataClass = DataClass()
    dataClass.configuration()
    dataClasses.add(dataClass)
}


fun DataClasses.sealedClass(configuration: SealedClass.()->Unit) {
    val sealedClass = SealedClass()
    sealedClass.configuration()
    sealedClasses.add(sealedClass)
}

fun DataClasses.clazz(configuration: Class.()->Unit) {
    val clazz = Class()
    clazz.configuration()
    classes.add(clazz)
}

fun DataClasses.objekt(configuration: Object.()->Unit) {
    val objekt = Object()
    objekt.configuration()
    objects.add(objekt)
}

fun DataClass.parameter(configuration: Parameter.() -> Unit) {
    val parameter = Parameter()
    parameter.configuration()
    parameters.add(parameter)
}

fun SealedClass.parameter(configuration: Parameter.() -> Unit) {
    val parameter = Parameter()
    parameter.configuration()
    parameters.add(parameter)
}

fun SubClass.parameter(configuration: Parameter.() -> Unit) {
    val parameter = Parameter()
    parameter.configuration()
    parameters.add(parameter)
}

fun SealedClass.representative(configuration: SubClass.() -> Unit) = subClass( configuration )

fun SealedClass.dataRepresentative(configuration: SubDataClass.() -> Unit) = representatives.add(subDataClass( configuration ))

fun ClassRepresentation.subDataClass(configuration: SubDataClass.()->Unit): SubDataClass {
    require(this !is DataClass)
    val subClass = SubDataClass(this)
    subClass.configuration()
    return subClass
}

fun ClassRepresentation.subClass(configuration: SubClass.()->Unit) {
    require(this !is DataClass)
    val subClass = SubClass(this)
    subClass.configuration()
}


fun Parameter.type(configuration: ParameterType.()->Unit) {
    val type = ParameterType()
    type.configuration()
    this.type = type
}

/**
 * Auxiliary functions
 * ====================================================================================================================
 */
/**
 *
 */
fun SubDataClass.toDataClass(): DataClass {
    val dataClass = DataClass()
    dataClass.name = name
    dataClass.settersPostFix = settersPostFix
    dataClass.packageName = packageName
    dataClass.comment.addAll(comment)
    dataClass.sourceFolder = sourceFolder
    dataClass.parameters.addAll(parameters)
    return dataClass
}

fun Set<ClassRepresentation>.findByFullName(fullName: String): ClassRepresentation? = find{
    it.packageName + "." + it.name == fullName
}

fun Set<ClassRepresentation>.findByNameIn(name: String, vararg locations: String): ClassRepresentation? {
    var result: ClassRepresentation? = null
    locations.forEach {
        result = find{
            classRepresentation ->
            classRepresentation.packageName + "." + classRepresentation.name == "$it.$name"
        }
        if(result != null) {
            return@forEach
        }
    }
    return result
}

fun DataClasses.toSet(): Set<ClassRepresentation> = with(hashSetOf<ClassRepresentation>()) {
    addAll(dataClasses)
    addAll(sealedClasses)
    addAll(classes)
    addAll(interfaces)
    addAll(objects)
    this
}