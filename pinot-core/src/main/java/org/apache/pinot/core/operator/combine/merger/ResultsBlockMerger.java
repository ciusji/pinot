/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pinot.core.operator.combine.merger;

import org.apache.pinot.core.operator.blocks.results.BaseResultsBlock;


public interface ResultsBlockMerger<T extends BaseResultsBlock> {

  /**
   * Merges a results block into the main mergeable results block.
   *
   * <p>NOTE: {@code blockToMerge} should contain the result for a segment without any exception. The errored segment
   * results are handled by {@link org.apache.pinot.core.operator.combine.BaseCombineOperator}.
   *
   * @param mergedBlock The block that accumulates previous results. It should be modified to add the information of the
   *                    other block.
   * @param blockToMerge The new block that needs to be merged into the mergedBlock.
   */
  void mergeResultsBlocks(T mergedBlock, T blockToMerge);

  /**
   * Determine if a block satisfies the query result requirement. This is mostly used to determine early termination
   * conditions. Default to always false and terminate should be done normally by processing all blocks.
   *
   * <p>The input results block might not be mergeable.
   */
  default boolean isQuerySatisfied(T resultsBlock) {
    return false;
  }

  /**
   * Converts the given results block into a mergeable results block.
   *
   * <p>This conversion is necessary if a block is used as the first argument for:
   * {@link ResultsBlockMerger#mergeResultsBlocks(BaseResultsBlock, BaseResultsBlock)}.
   */
  default T convertToMergeableBlock(T resultsBlock) {
    return resultsBlock;
  }
}
