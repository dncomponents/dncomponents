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

package com.dncomponents.client.main;


import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.AppTemplates;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.History;
import com.dncomponents.client.main.components.appviews.MainApp;
import com.dncomponents.client.views.Ui;
import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;

public class App implements EntryPoint {

    public void onModuleLoad() {
        AppTemplates.register();
        Ui.setDebug(true);
        HtmlBinder.cssDevMode();

        testComponents();

        History.fireCurrentHistoryState();
    }

    private void testComponents() {
        Ui.set(new BootstrapUi());
        MainApp main = new MainApp();
        DomGlobal.document.body.appendChild(main.asNode());
    }

    private void testReactive() {
        com.dncomponents.client.main.reactive.MainApp main2 = new com.dncomponents.client.main.reactive.MainApp();
        DomGlobal.document.body.appendChild(main2.asElement());
    }

}