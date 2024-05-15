# dncomponents


&nbsp;&nbsp;Client side java UI framework for building rich web applications written purely in Java language using GWT
compiler and elemental2 browser API without any external js libraries.
http://dncomponents.com

Note: There is the same project using [TeaVM](https://teavm.org/) compiler [here](https://github.com/dncomponents/dncomponents-tea) 

| Module                                |                                                                      Info |
|---------------------------------------|--------------------------------------------------------------------------:|
| <b>core</b>                           |  Basic classes to build user interface with elemental2 and dn HTML binder |
| <b> template_annotation_processor</b> |                Annotation processor to generate code from java/html pairs |
| <b> mvp</b>                           |                                                   Lightweight MVP pattern |
| <b>core-uui</b>                       |                                                Components of dncomponents |
| <b>bootstrap-ui</b>                   | Views implementation of dncomponents in bootstrap front-end framework</b> |
| <b>material-ui</b>                    |                   Views implementation of dncomponents in material design |
| <b>main</b>                           |                                                          tests components |
| <b>main-reactive</b>                  |                                                   tests reactive features |

## Getting started

See [project web site](https://dncomponents.com/index.html).

### To run components examples

Get a stable set of components, same as you can see at  [dncompoentns bootstrap example](https://dncomponents.com/demo/index.html).

clone the project `git clone https://github.com/dncomponents/dncomponents.git`

`cd main` and `run mvn gwt:devmode`


Download starter project [here](https://github.com/dncomponents/dncomponents-java-starter-bs) .

### To test new reactivity features

This attempts to bring some features of popular frameworks - Vue, React, and Angular, such as:

* **Reactivity** Data-binding system that synchronize the state of the application with DOM.<br>
* **Component based** Organizing code into reusable UI components encapsulating their own logic, styling, and structure. <br>
* **Two-way data binding** Changes in the model affect the view and vice versa. <br>
* **Loops** Iterating over arrays and objects using the **dn-loop** directive, handy for rendering lists and tables dynamically in the DOM. <br>
* **Conditional Rendering** With **dn-if, dn-if-else dn-else** directives, you can conditionally render elements based on the value of expressions or data properties.


Read more [here](https://github.com/dncomponents/dncomponents/blob/master/main-reactive/README.md)

Note that this is still work in progress and we need feedback and more testing.

cd `main-reactivity` and `run mvn gwt:devmode`

Download starter project [here](https://github.com/dncomponents/dncomponents-java-elemental-starter)

## Contact
Join [gitter room](https://app.gitter.im/#/room/#dncomponents.com:gitter.im)<br>
support@dncomponents.com

## License

This project is licensed under

* [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)
