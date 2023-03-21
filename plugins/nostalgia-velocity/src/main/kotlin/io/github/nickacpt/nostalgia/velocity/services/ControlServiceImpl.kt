package io.github.nickacpt.nostalgia.velocity.services

import com.google.inject.Inject
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.ServerInfo
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import io.github.nickacpt.nostalgia.core.services.control.ControlService
import io.github.nickacpt.nostalgia.core.services.control.ShardStatus
import java.net.InetSocketAddress

class ControlServiceImpl @Inject constructor(val proxy: ProxyServer) : ControlService {
    override fun notifyShardStatus(shard: NostalgiaShard, status: ShardStatus) {
        if (status == ShardStatus.READY) {
            // If a shard is ready, we need to add it to our server list of available servers
            // TODO: Pass the address of this server
            proxy.registerServer(ServerInfo(
                "shard-${shard.id}",
                InetSocketAddress(25565)
            ))
        }
    }
}