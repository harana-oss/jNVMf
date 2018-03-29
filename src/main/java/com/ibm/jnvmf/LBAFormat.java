/*
 * Copyright (C) 2018, IBM Corporation
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
 *
 */

package com.ibm.jnvmf;

public class LBAFormat {
    public final static int SIZE = 4;

    /*
     * NVM Spec 1.3a - Figure 115
     */
    private final static int METADATA_SIZE_OFFSET = 0;
    private final static int LBA_DATA_SIZE_OFFSET = 2;
    private final static int RELATIVE_PERFORMANCE_OFFSET = 3;
    private final static int RELATIVE_PERFORMANCE_BITOFFSET_START = 0;
    private final static int RELATIVE_PERFORMANCE_BITOFFSET_END = 1;

    private final short metaDataSize;
    private final LBADataSize lbaDataSize;
    private final RelativePerformance.Value relativePerformance;

    LBAFormat(short metaDataSize, byte lbaDataSize, RelativePerformance.Value relativePerformance) {
        this.metaDataSize = metaDataSize;
        this.lbaDataSize = new LBADataSize(lbaDataSize);
        this.relativePerformance = relativePerformance;
    }

    public short getMetadataSize() {
        return metaDataSize;
    }

    public LBADataSize getLBADataSize() {
        return lbaDataSize;
    }

    public static class RelativePerformance extends EEnum<RelativePerformance.Value> {
        public class Value extends EEnum.Value {
            Value(int value) {
                super(value);
            }
        }

        public final Value BEST_PERFORMANCE = new Value(0x0);
        public final Value BETTER_PERFORMANCE = new Value(0x1);
        public final Value GOOD_PERFORMANCE = new Value(0x2);
        public final Value DEGRADED_PERFORMANCE = new Value(0x3);

        RelativePerformance() {
            super(3);
        }

        private final static RelativePerformance instance = new RelativePerformance();

        public static RelativePerformance getInstance() {
            return instance;
        }
    }

    public RelativePerformance.Value getRelativePerformance() {
        return relativePerformance;
    }

    static LBAFormat fromBuffer(NativeBuffer buffer) {
        short metaDataSize = buffer.getShort(METADATA_SIZE_OFFSET);
        byte lbaDataSize = buffer.get(LBA_DATA_SIZE_OFFSET);
        int b = buffer.get(RELATIVE_PERFORMANCE_OFFSET);
        int val = BitUtil.getBits(b, RELATIVE_PERFORMANCE_BITOFFSET_START, RELATIVE_PERFORMANCE_BITOFFSET_END);
        RelativePerformance.Value relativePerformance = RelativePerformance.getInstance().valueOf(val);
        return new LBAFormat(metaDataSize, lbaDataSize, relativePerformance);
    }
}