package $

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.History;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.appview.AcceptsOneElement;
import com.dncomponents.client.views.appview.PlaceManager;
import elemental2.dom.HTMLElement;

public class MainApp implements AcceptsOneElement, IsElement {

    @UiField
    HTMLElement root;
    PlaceManager placeManager = new PlaceManager(this);

    {
        HtmlBinder.create(MainApp.class, this).bind();

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
