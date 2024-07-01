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

package io.doclib.util.models.result

object DeletionResult {

  /** Result when a single item is successfully deleted. */
  val ok: DeletionResult = DeletionResult(acknowledged = true, deletedCount = 1)
}

/**
  * Result object from a delete request.
  * @param acknowledged true if database ack'ed the request
  * @param deletedCount number of database items that were deleted
  */
case class DeletionResult(acknowledged: Boolean, deletedCount: Long)
