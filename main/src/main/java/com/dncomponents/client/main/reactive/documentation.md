## Table of Contents

* [HtmlBinder](#htmlbinder)
* [Components](#components)
* [States](#states)
* [Props of Components](#props-of-components)
* [Conditional displaying](#conditional-displaying---if-else)
* [Loops](#loops)
* [Forms and two-way binding](#forms-and-two-way-binding)

# HtmlBinder

HtmlBinder is a part of the dncomponents project, designed for constructing user interfaces. It
uses regular HTML templates, allowing you to easily bind elements with Java code.

# Components

Component is reusable building block for your web application. <br>
It's a self-contained piece of code that encapsulates HTML, CSS, and
Java functionality. <br>
Components can also communicate with each other, passing data and triggering actions.

Basic example:

 ```java
//language=html
@Component(template = """
        <div class='helloCss'>
          <h1>Hello, {{name}}!</h1>         
        </div>
          """,
//language=css
        css = """
                .helloCss{
                           background: lightgreen;
                           padding: 20px;
                           border: 1px solid gray;
                         }
                  """,
        tag = "HelloComponent"
)

public class HelloComponent implements IsElement {
    HtmlBinder<HelloComponent> binder = HtmlBinder.create(HelloComponent.class, this);

    String name = "All";

    public HelloComponent() {
        binder.bindAndUpdateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }
}
 ```

_@Component_ annotation contains three attributes: <br><br>
**template**  specifies the HTML template associated with the component. <br>
**css** defines the CSS style for the component <br>
**tag** specifies how the component should be rendered in the HTML markup. <br>
(if tag is not provided it will use name of the java class - HelloComponent in this case)

```html

<div>
    <HelloComponent></HelloComponent>
    <HelloComponent></HelloComponent>
    <HelloComponent></HelloComponent>
</div>
```

This will output

```
Hello, All!
Hello, All!
Hello, All!
```

Note: Self-closing tags not possible at this moment.
So this is not valid:

```html

<HelloComponent/>
```

Valid:

```html

<HelloComponent></HelloComponent
```

## States

States are special objects that holds value obtain from **variable** or **method** of current Component.<br>
They know how to display and update its value in UI.
<br>To use state values in **html** code we use double curly braces **{{variable}}** or **{{method()}}** .

```html
<p>Name: {{name}}</p>
<p>age: {{age}}</p>
<p>Address: {{getAddress()}}</p>
```

 ```java

String name;
int age;

String getAddress() {
    return "Some address";
}
 ```

#### How it works:

It is enough just to reference method or variable of component within curly braces in HTML markup, state objects are
automatically
created in the background and displayed.<br>
To update states you need to call:
**binder.updateUi()** <br>
This is not an expensive call; it determines in a clever way what states are changed and updates the UI accordingly.
So, you can freely call it often.

### Note:

**Access modifiers** for variables or methods of states must be **_protected_**, **_package protected_** or *
*_public_**.
**_private_** access modifiers are not allowed because they are accessed from generated java files.

### Updating UI

After changes of any state variables you need to call `binder.updateUi()`.
As mentioned this method determins which state has changed and updates UI accordingly.

(In most cases, using this method is perfectly safe.
However, in rare instances, if needed, you can update only specific fields or methods.)

### Monitoring field changes

Occasionally, you may need to perform certain actions based on specific state changes.<br>
This can be achieved by either adding a handler directly to the state object or by <br>
utilizing the watch method of the HTML binder.

```java
binder.watch("stateName",event ->{
        event.

getValue();//do something with changed value
});
        binder.

getState("stateName").

addValueChangeHandler(event ->{
        event.

getValue();//do something with changed value
});
```

## State bindings

You can bind states into html element's **content** or **attributes**.

### Text content of element

```html

<div>{{value}}</div>
```

#### Note:

Only text can be inserted, not html (from security reasons)

### Different attributes of element

```html

<div title="{{value}}"></div>
<img src="{{person.image}}" alt="">
```

### Style attribute

To bind style properties set its name followed by state like this :

```html

<div style="color:{{color}};padding:{{getPadding()}}px;margin: 5px;">
    Style
</div>
```

```java
String color = "red";

int getPadding() {
    return 3 * 5 + 4; //some calculation
}
```

### Classes attribute

Example shows how to bind states to class attribute.<br>
In this case method adds class based on the condition.

```html

<div class="main {{getMainBackground()}}">
    Classes
</div>

<style>
    .mainClass {
        padding: 10px;
        font-weight: bold;
        color: white;
    }

    .redColorClass {
        background: red;
    }

    .blueColorClass {
        background: blue;
    }
</style>
```

```java
String main = "mainClass";
boolean isRed = false;

String getMainBackground() {
    return isRed ? "redColorClass" : "blueColorClass";
}
```

## Props of components

To access attributes passed to components, add a constructor with a Props argument.<br>
You can use two types of attributes: state and string.

Usage example:

```html

<PersonComponent person='{{pers}}' someNumb='{{number}}' color='red'></PersonComponent>

```

The **person** and **someNumb** attributes are states, while **color** is a regular string attribute. <br>
This is how you access them from the Props:

 ```java

public class PersonComponent implements IsElement {
    HtmlBinder<PersonComponent> binder = HtmlBinder.create(PersonComponent.class, this);

    public PersonComponent(Props props) {
        State<Person> person = props.getState("person");
        State<Integer> someNumb = props.getState("someNumb");
        String color = props.getAttribute("color");
        binder.bindAndUpdateUi();
    }
}
 
}
 ```

### Conditional displaying - if else

**dn-if** **dn-else-if** and **dn-else** are directives which we can be used on elements to conditionally display blocks
based on boolean value.

```java
String type = "B";
```

```html

<div dn-if='type=="A"'>
    A block
</div>
<div dn-else-if='type=="B"'>
    B block
</div>
<div dn-else-if='type=="C"'>
    C block
</div>
<div dn-else>
    Not A/B/C
</div>
```

This will output

```html
  B block
```

### Loops

**dn-loop** directive is used to render items of java collections.
You can use it on some element which will be a parent for rendered child elements e.g :

```html

<div dn-loop="item in items">
    <h3>{{item}}</h3>
</div>
```

Or as a **template** which will add fragment of rendered list at the place of
template element.

```html

<template dn-loop="item in items">
    <h3>{{item}}</h3>
</template>
```

### Note:

When using loops in **table** always use it with **template** !
(required by HTML parser)

```java
List<Person> persons;
```

```html

<div>
    <h2>Loop in table</h2>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Age</th>
        </tr>
        </thead>
        <tbody>
        <template dn-loop='person in persons'>
            <tr>
                <td>
                    {{person.getName()}}
                </td>
                <td>
                    {{person.getAge()}}
                </td>
            </tr>
        </template>
        </tbody>
    </table>
    <h2>Loop in list</h2>
    <ul dn-loop='person in persons'>
        <li>{{person.getName()}}</li>
    </ul>
</div>
```

### Events

To add events to element use following syntax:

```<div dn-on-(nameOfEvent)="eventHandler(e)" </div>```

**nameOfEvent** is any event that can be handled including custom events.
**eventHandler(e)** is method that you call defined in java file with "**e**" as a Event parameter.

```html

<div dn-on-click="handleClickMethod(e)"></div>
```

```java
void handleClickMethod(Event e) {
    e.target.style.background = "red";
}
```

### Forms and two-way binding

To synchronize form element values with states we use **dn-model** directive.
This works for all types of inputs and for select elements too.

```html
<input dn-model="text">
```

```java
String text;
```

### reference elements or components

```html

<div ui-field="divElement"></div>
<HelloComponent ui-field="helloComponent"></HelloComponent>
```

To reference element or component :

```java

@UiField
HTMLElement divElement;
@UiField
HelloComponent helloComponent;
```

