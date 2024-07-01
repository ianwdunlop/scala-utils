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

object UpdatedResult {

  /** Placeholder indicates that update succeeded, but nothing was updated. */
  val nothing: UpdatedResult = UpdatedResult(acknowledged = true, modifiedCount = 0, matchedCount = 0)

  /** Generic successful update.
    * Do not rely on equality to determine if update was successful as counts may vary with
    * an UpdateResult that is converted from a real Mongo response.
    */
  val ok: UpdatedResult = UpdatedResult(acknowledged = true, modifiedCount = 1, matchedCount = 1)

  /** Placeholder indicates that update failed. */
  val fail: UpdatedResult = UpdatedResult(acknowledged = false, modifiedCount = 0, matchedCount = 0)
}

/**
  * Result object from an update request or an upsert request.
  * @param acknowledged true if database ack'ed the request
  * @param modifiedCount number of database items that were changed
  * @param matchedCount number of database items that matched the request criteria
  */
case class UpdatedResult(acknowledged: Boolean, modifiedCount: Long, matchedCount: Long) {

  /**
    * Will be true if the update caused any changes.
    */
  val changesMade: Boolean = modifiedCount > 0

}
