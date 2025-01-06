package org.team1;

/*-
 * #%L
 * GameOfLife
 * %%
 * Copyright (C) 2024 - 2025 Zespol1
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public class GolNullPtrCellExp extends GolExp {

    private static final Logger logger = LogManager.getLogger(GolNullPtrCellExp.class);

    public GolNullPtrCellExp(String messKey) {
        super(messKey);
        logger.error(exceptionsMess.getString("exp.null.cell"));
    }

    public GolNullPtrCellExp(String messKey, Throwable cause) {
        super(messKey, cause);
    }

    public GolNullPtrCellExp(Locale expLang, String messKey, Throwable cause) {
        super(expLang, messKey, cause);
    }

    public GolNullPtrCellExp(Locale expLang, String messKey) {
        super(expLang, messKey);
    }
}
