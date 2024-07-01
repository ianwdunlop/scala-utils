/*
 * Copyright 2024 Medicines Discovery Catapult
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

package io.doclib.util.admin

import java.net.InetSocketAddress

import com.sun.net.httpserver.HttpServer
import com.typesafe.config.Config
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.HTTPServer
import io.prometheus.client.hotspot.DefaultExports
import java.net._

object Server {
  def apply(config: Config, checkHealth: () => Boolean) = new Server(config, checkHealth)
  def apply(config: Config) = new Server(config, () => true)
}

class Server(config: Config, checkHealth: () => Boolean) {
  private var server: HTTPServer = _
  var address: String = InetAddress.getLocalHost.getHostAddress

  def start(): Unit = {
    DefaultExports.initialize()
    val port = config.getInt("admin.port")
    address = s"http://${address}:9090"
    val addr = new InetSocketAddress(port)
    val srv = HttpServer.create(addr, 3)
    val healthCheckHandler = new HTTPHealthCheckHandler(checkHealth)
    srv.createContext("/health", healthCheckHandler)
    val reg = CollectorRegistry.defaultRegistry
    server = new HTTPServer(srv, reg, false)
  }

  def stop(): Unit = {
    server.stop()
  }
}