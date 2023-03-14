package io.github.nickacpt.nostalgia.rpc.server

import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.model.MethodCallReplyRpcMessage
import io.github.nickacpt.nostalgia.rpc.model.MethodCallRequestRpcMessage
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import io.github.nickacpt.nostalgia.rpc.utils.RpcUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutionException
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspend
import kotlin.reflect.jvm.kotlinFunction

class NostalgiaServiceHandler {

    sealed interface ServiceMethod {
        data class NormalServiceMethod(
            val handle: MethodHandle,
        ) : ServiceMethod

        data class SuspendServiceMethod(
            val service: Any,
            val function: KFunction<*>
        ) : ServiceMethod
    }

    data class ServiceData(
        private val methods: ConcurrentHashMap<String, ServiceMethod>
    ) {
        private val scope = CoroutineScope(Dispatchers.Unconfined)

        fun invoke(name: String, arguments: List<Any?>): Any? {
            val method = methods[name] ?: throw Exception("Method $name not found!")

            if (method is ServiceMethod.SuspendServiceMethod) {
                return scope.future {
                    method.function.callSuspend(method.service, *arguments.toTypedArray())
                }.get()
            }

            check(method is ServiceMethod.NormalServiceMethod) { "Unexpected service method type" }

            return method.handle.invokeWithArguments(arguments)
        }

        companion object {
            fun <T : Any> from(clazz: Class<T>, service: T): ServiceData {
                val lookup = MethodHandles.lookup()

                val methodMap = clazz.methods.associateBy(RpcUtils::getMethodNameForService).mapValues {
                    val method = it.value
                    val kotlinFunction = method.kotlinFunction

                    if (kotlinFunction?.isSuspend == true) {
                        ServiceMethod.SuspendServiceMethod(service, kotlinFunction)
                    } else {
                        ServiceMethod.NormalServiceMethod(lookup.unreflect(method).bindTo(service))
                    }
                }

                return ServiceData(
                    ConcurrentHashMap(methodMap)
                )
            }
        }
    }

    private val exposedServices = ConcurrentHashMap<String, ServiceData>()

    fun <T : Any> exposeService(serviceClazz: Class<T>, service: T) {
        exposedServices[RpcUtils.getClazzNameForService(serviceClazz)] = ServiceData.from(serviceClazz, service)
    }

    fun handleMessage(message: RpcMessage, transport: RpcTransport) {
        if (message is MethodCallRequestRpcMessage) {
            var result = runCatching {
                handleInvocation(message)
            }

            val exception = result.exceptionOrNull()
            if (exception is ExecutionException) {
                result = Result.failure(exception.cause ?: exception)
            }

            val resultMessage = MethodCallReplyRpcMessage(
                message.requestId,
                result
            )

            transport.sendMessage(resultMessage)
        }
    }

    private fun handleInvocation(call: MethodCallRequestRpcMessage): Any? {
        val service = exposedServices[call.clazz] ?: throw Exception("Service not found!")

        return service.invoke(call.method, call.args)
    }


}
