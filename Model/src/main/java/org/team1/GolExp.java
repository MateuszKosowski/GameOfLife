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
import java.util.Locale;
import java.util.ResourceBundle;

public class GolExp extends RuntimeException {

    protected static final ResourceBundle exceptionsMess = ResourceBundle.getBundle("exceptions");
    protected Locale locale = new Locale("pl", "PL");

    public GolExp(Locale expLang, String messKey) {
        super(exceptionsMess.getString(messKey));
        locale = expLang;
    }

    public GolExp(Locale expLang, String messKey, Throwable cause) {
        super(exceptionsMess.getString(messKey), cause);
        locale = expLang;
    }

    public GolExp(String messKey) {
        super(exceptionsMess.getString(messKey));
    }

    public GolExp(String messKey, Throwable cause) {
        super(exceptionsMess.getString(messKey), cause);
    }

    public Locale getLocale() {
        return locale;
    }

}
