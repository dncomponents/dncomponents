package ${package}.client;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.AppTemplates;
import com.dncomponents.client.views.Ui;
import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;

public class ${mainapp} implements EntryPoint {
@Override
public void onModuleLoad() {
        Ui.set(new BootstrapUi());
        AppTemplates.register();
        //
        MainApp mainApp = new MainApp();
        DomGlobal.document.body.appendChild(mainApp.asElement());
        }
}
