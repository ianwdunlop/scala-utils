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

package io.mdcatapult.util.models.result

object InsertionResult {

  /** Placeholder indicates that nothing was inserted, but request was accepted. */
  val nothing: InsertionResult = InsertionResult(acknowledged = true, 0)

  /** Result when a single item is successfully inserted. */
  val ok: InsertionResult = InsertionResult(acknowledged = true, 1)
}

/**
  * Result object from an insert request.
  * @param acknowledged true if database ack'ed the request
  * @param insertedCount number of database items that were inserted
  */
case class InsertionResult(acknowledged: Boolean, insertedCount: Long)
