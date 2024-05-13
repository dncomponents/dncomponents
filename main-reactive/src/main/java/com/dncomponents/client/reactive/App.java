/*
 * Copyright 2024 dncomponents
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

package com.dncomponents.client.reactive;


import com.dncomponents.client.components.core.AppTemplates;
import com.dncomponents.client.components.core.HtmlBinder;
import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;

public class App implements EntryPoint {

    public void onModuleLoad() {
        AppTemplates.register();
        MainApp mainApp = new MainApp();
        DomGlobal.document.body.appendChild(mainApp.asElement());
    }


}