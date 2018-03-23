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

public class Pow2Size {
    final int pow2Size;

    Pow2Size(int pow2Size) {
        if (pow2Size > Integer.SIZE) {
            throw new IllegalArgumentException("Size to large");
        }
        this.pow2Size = pow2Size;
    }

    int value() {
        return pow2Size;
    }

    public int toInt() {
        return 1 << pow2Size;
    }

    @Override
    public String toString() {
        return Integer.toString(toInt());
    }
}
