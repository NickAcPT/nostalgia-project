package io.github.nickacpt.nostalgia.rpc.utils

import java.lang.reflect.Method

@PublishedApi
internal object RpcUtils {
    fun getClazzNameForService(clazz: Class<*>) = clazz.simpleName

    fun getMethodNameForService(method: Method) =
        buildString {
            append(method.name)
            if (method.parameterTypes.isNotEmpty()) {
                append("_")
                append(method.parameterTypes.joinToString("_") { getClazzNameForService(it) })
            }
        }
}