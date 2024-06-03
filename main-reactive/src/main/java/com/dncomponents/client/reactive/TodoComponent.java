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

import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.IsElement;
import elemental2.core.JsArray;
import elemental2.dom.*;
import elemental2.webstorage.Storage;
import elemental2.webstorage.WebStorageWindow;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static elemental2.core.Global.JSON;

@Component(template = "<section class=\"todoapp\">\n" +
                      "    <header class=\"header\">\n" +
                      "        <h1>Todos</h1>\n" +
                      "        <input class=\"new-todo\" autofocus placeholder=\"What needs to be done?\"\n" +
                      "               dn-on-keyup='addTodo(e)'>\n" +
                      "    </header>\n" +
                      "    <section class=\"main\" dn-if='!todos.isEmpty()'>\n" +
                      "        <input id=\"toggle-all\" class=\"toggle-all\" type=\"checkbox\"\n" +
                      "               checked='{{getRemaining() == 0}}'\n" +
                      "               dn-on-change='toggleAll(e)'>\n" +
                      "        <label for=\"toggle-all\">Mark all as complete</label>\n" +
                      "        <ul class=\"todo-list\" dn-loop='todo in getFilteredTodos()'>\n" +
                      "            <li class='todo {{getTodoClass(todo)}}'>\n" +
                      "                <div class=\"view\">\n" +
                      "                    <input class=\"toggle\" type=\"checkbox\" dn-model='todo.completed'>\n" +
                      "                    <label dn-on-dblclick='editTodo(todo)'>{{todo.title}}</label>\n" +
                      "                    <button class=\"destroy\" dn-on-click='removeTodo(todo)'></button>\n" +
                      "                </div>\n" +
                      "                <input dn-if='todo.equals(editedTodo)'\n" +
                      "                       autofocus\n" +
                      "                       class=\"edit\"\n" +
                      "                       type=\"text\"\n" +
                      "                       dn-on-dnshow='onElementShown(e)'\n" +
                      "                       dn-model='todo.title'\n" +
                      "                       dn-on-blur='doneEdit(todo)'\n" +
                      "                       dn-on-keyup='onEdit(e,todo)'\n" +
                      "                >\n" +
                      "            </li>\n" +
                      "        </ul>\n" +
                      "    </section>\n" +
                      "    <footer class=\"footer\" dn-if='!todos.isEmpty()'>\n" +
                      "      <span class=\"todo-count\">\n" +
                      "        <strong>{{getRemaining()}}</strong>\n" +
                      "        <span>{{getRemaining() == 1 ? \"item\" : \"items\"}} left</span>\n" +
                      "      </span>\n" +
                      "        <ul class=\"filters\">\n" +
                      "            <li>\n" +
                      "                <a href=\"#/All\" class='{{getSelectedClass(\"All\")}}'>All</a>\n" +
                      "            </li>\n" +
                      "            <li>\n" +
                      "                <a href=\"#/Active\" class='{{getSelectedClass(\"Active\")}}'>Active</a>\n" +
                      "            </li>\n" +
                      "            <li>\n" +
                      "                <a href=\"#/Completed\" class='{{getSelectedClass(\"Completed\")}}'>Completed</a>\n" +
                      "            </li>\n" +
                      "        </ul>\n" +
                      "        <button class=\"clear-completed\" dn-on-click='removeCompleted()' dn-if='todos.size() > getRemaining()'>\n" +
                      "            Clear completed\n" +
                      "        </button>\n" +
                      "    </footer>\n" +
                      "</section>\n"
)
public class TodoComponent implements IsElement {
    HtmlBinder<TodoComponent> binder = HtmlBinder.create(TodoComponent.class, this);
    List<Todo> todos = new ArrayList<>();
    Filter currentFilter = Filter.All;
    String beforeEditCache = "";
    Todo editedTodo;
    private static final String STORAGE_KEY = "dn-todomvc";


    public TodoComponent() {
        WebStorageWindow webStorageWindow = WebStorageWindow.of(DomGlobal.window);
        Storage localStorage = webStorageWindow.localStorage;
        binder.bindAndUpdateUi();
        DomGlobal.window.addEventListener("hashchange", e -> onHashChange());
        onHashChange();
        //todos variable is not directly referenced from html markup so no state created.
        // That's wy we need this createOrGetState call.
        binder.createOrGetState("todos", () -> todos)
                .addValueChangeHandler(event -> {
                    JsArray<Todo> jsArray = JsArray.asJsArray((Todo[]) todos.toArray());
                    localStorage.setItem(STORAGE_KEY, JSON.stringify(jsArray));
                });
        JsArray<Todo> parse = (JsArray<Todo>) JSON.parse(localStorage.getItem(STORAGE_KEY));
        if (parse != null) {
            todos.clear();
            todos=new ArrayList<>(parse.asList());
            binder.updateUi();
        }
    }

    void onElementShown(Event e) {
        DomGlobal.setTimeout((evt) -> ((HTMLInputElement) e.target).focus(), 111);
    }

    void onHashChange() {
        String route = DomGlobal.window.location.hash.replaceAll("^#/?", "");
        try {
            Filter filter = Filter.valueOf(route);
            currentFilter = filter;
        } catch (IllegalArgumentException ex) {
            DomGlobal.window.location.hash = ("");
            currentFilter = Filter.All;
        }
        binder.updateUi();
    }

    void addTodo(Event e) {
        HTMLInputElement target = (HTMLInputElement) e.target;
        String value = target.value.trim();
        if (((KeyboardEvent) e).key.equals("Enter")) {
            if (!value.isEmpty()) {
                todos.add(new Todo().setTitle(value));
                binder.updateUi();
                target.value=("");
                target.focus();
            }
        }
    }

    String getSelectedClass(String cls) {
        return Filter.valueOf(cls).equals(currentFilter) ? "selected" : "";
    }

    void removeTodo(Todo item) {
        todos.remove(item);
        binder.updateUi();
    }

    void editTodo(Todo item) {
        beforeEditCache = item.title;
        editedTodo = item;
        //we need to force update because there is no changes in data of list getFilteredTodos()
        // but there is a change in todo.equals(editedTodo) after editing starts and we need to refresh list items.
        binder.getState("getFilteredTodos()").forceUpdateUI();
    }

    void cancelEdit(Todo item) {
        editedTodo = null;
        item.title = beforeEditCache;
        binder.updateUi();
    }

    void doneEdit(Todo todo) {
        if (editedTodo != null) {
            editedTodo = null;
            todo.title = todo.title.trim();
            if (todo.title.isEmpty())
                removeTodo(todo);
        }
        binder.updateUi();
        binder.getState("getFilteredTodos()").forceUpdateUI();
    }

    void onEdit(Event e, Todo todo) {
        if (((KeyboardEvent) e).key.equals("Enter")) {
            doneEdit(todo);
        } else if (((KeyboardEvent) e).key.equals("Escape")) {
            cancelEdit(todo);
        }
    }

    void removeCompleted() {
        todos = getFilteredTodos(Filter.Active);
        binder.updateUi();
    }

    String getTodoClass(Todo todo) {
        String result = "";
        if (todo.completed) {
            result += "completed";
        }
        if (todo.equals(editedTodo)) {
            result += " editing";
        }
        return result;
    }

    List<Todo> getFilteredTodos(Filter f) {
        return todos.stream().filter(f.filter).collect(Collectors.toList());
    }

    List<Todo> getFilteredTodos() {
        return getFilteredTodos(currentFilter);
    }

    void toggleAll(Event e) {
        todos.forEach(todo -> todo.completed = (((HTMLInputElement) e.target).checked));
    }

    int getRemaining() {
        return getFilteredTodos(Filter.Active).size();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }

    enum Filter {
        All(e -> true),
        Active(e -> !e.completed),
        Completed(e -> e.completed);
        private Predicate<Todo> filter;

        Filter(Predicate<Todo> filter) {
            this.filter = filter;
        }
    }
}

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
class Todo {
    double id;
    String title;
    boolean completed;

    @JsOverlay
    public final Todo setTitle(String title) {
        this.title = title;
        this.id=new Date().getTime();
        return this;
    }
}

