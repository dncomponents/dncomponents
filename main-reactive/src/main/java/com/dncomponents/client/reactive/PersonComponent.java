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
import com.dncomponents.client.components.core.Props;
import com.dncomponents.client.testing.Person;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


//language=html
@Component(template = "        <div>\n" +
                      "          <h1>Person component</h1>\n" +
                      "          <p>{{props.getPerson().getName()}}</p>\n" +
                      "          <p>{{props.getPerson().getAge()}}</p>\n" +
                      "          <p>{{props.getPerson().getGender()}}</p>\n" +
                      "          <p>Some numb: {{props.getSomeNumb()}}</p>\n" +
                      "          <p style='background: {{props.getPerson().getCurrentColor()}}'>Color: {{props.getColor()}}</p>\n" +
                      "        </div>\n")
public class PersonComponent implements IsElement {
    HtmlBinder<PersonComponent> binder = HtmlBinder.create(PersonComponent.class, this);

    class PersonComponentProps extends Props {

        public Person getPerson() {
            return getStateValue("person");
        }
        public LoopComponent getLoopComponent() {
            return getStateValue("parent");
        }

        public int getSomeNumb() {
            return getStateValue("someNumb");
        }

        public String getColor() {
            return getAttribute("color");
        }
    }

    PersonComponentProps props;

    public PersonComponent(Props props) {
        this.props = new PersonComponentProps().wrap(props, binder);
        //testing...
        LoopComponent loopComponent = this.props.getLoopComponent();
        if(loopComponent!=null) {
            Person person = loopComponent.persons.get(2);
        }
        //        State<Person> person = props.getState("person");
//        State<Integer> someNumb = props.getState("someNumb");
//        String color = props.getAttribute("color");
        binder.bindAndUpdateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }
}