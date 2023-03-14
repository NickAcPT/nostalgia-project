package io.github.nickacpt.nostalgia.rpc.server

import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.model.MethodCallReplyRpcMessage
import io.github.nickacpt.nostalgia.rpc.model.MethodCallRequestRpcMessage
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import io.github.nickacpt.nostalgia.rpc.utils.RpcUtils
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.util.concurrent.ConcurrentHashMap

class NostalgiaServiceHandler {
    data class ServiceData(
        private val methods: ConcurrentHashMap<String, MethodHandle>
    ) {
        fun invoke(name: String, arguments: List<Any?>): Any? {
            val method = methods[name] ?: throw Exception("Method $name not found!")

            return method.invokeWithArguments(arguments)
        }

        companion object {
            fun <T : Any> from(clazz: Class<T>, service: T): ServiceData {
                val lookup = MethodHandles.lookup()

                return ServiceData(
                    ConcurrentHashMap(clazz.methods.associateBy(RpcUtils::getMethodNameForService)
                        .mapValues { lookup.unreflect(it.value).bindTo(service) })
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
            val result = runCatching {
                handleInvocation(message)
            }

            val resultMessage = MethodCallReplyRpcMessage(
                message.requestId,
                result
            )

            transport.sendMessage(resultMessage)
        }
    }

    fun handleInvocation(call: MethodCallRequestRpcMessage): Any? {
        val service = exposedServices[call.clazz] ?: throw Exception("Service not found!")

        return service.invoke(call.method, call.args)
    }


}
