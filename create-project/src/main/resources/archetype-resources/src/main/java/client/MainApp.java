package ${package}.client;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.appview.AcceptsOneElement;
import com.dncomponents.client.views.appview.PlaceManager;
import com.google.gwt.user.client.History;
import com.mycompany.client.greeting.GreetingPlace;
import com.mycompany.client.home.HomePlace;
import elemental2.dom.HTMLElement;


/**
 * @author nikolasavic
 */
public class MainApp implements AcceptsOneElement, IsElement {

    @UiField
    HTMLElement root;
    PlaceManager placeManager = new PlaceManager(this);

    {
        HtmlBinder.get(MainApp.class, this).bind();

    }

    public MainApp() {
        init();
    }

    public void init() {
        placeManager.register(HomePlace.HomePlaceRegister.instance);
        placeManager.register(GreetingPlace.GreetingPlaceRegister.instance);
        placeManager.setHomePlace(HomePlace.class);
        History.fireCurrentHistoryState();
    }

    @Override
    public void setElement(IsElement element) {
        root.innerHTML = "";
        root.appendChild(element.asElement());
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}