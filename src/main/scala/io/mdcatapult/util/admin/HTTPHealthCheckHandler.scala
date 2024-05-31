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

package io.mdcatapult.util.admin


import java.io.{ByteArrayOutputStream, OutputStreamWriter}
import java.net.HttpURLConnection

import com.sun.net.httpserver.{HttpExchange, HttpHandler}

class HTTPHealthCheckHandler(checkHealth: () => Boolean) extends HttpHandler {
  override def handle(exchange: HttpExchange): Unit = {
    val isHealthy = checkHealth()

    val response = new ByteArrayOutputStream()
    val writer = new OutputStreamWriter(response)
    if (isHealthy) writer.write("\"OK\"") else writer.write("\"unhealthy\"")

    writer.flush()
    writer.close()
    response.flush()
    response.close()

    exchange.getResponseHeaders.set("Content-Type", "application/json")
    exchange.getResponseHeaders.set("Content-Length", String.valueOf(response.size))

    if (isHealthy)
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.size)
    else
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, response.size)

    response.writeTo(exchange.getResponseBody)
    exchange.close()
  }
}