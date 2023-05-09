package ${package}.client.home;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.appview.AbstractView;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

public class HomeViewImpl extends AbstractView<HomeView.HomePresenter> implements HomeView {
    private static HomeViewImpl instance;

    @UiField
    public HTMLDivElement root;

    {
        HtmlBinder.create(HomeViewImpl.class, this).bind();
    }

    public HomeViewImpl() {
        greetingBtn.addClickHandler(e -> presenter.goToGreeting());
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @UiField
    public Button greetingBtn;

    public static HomeViewImpl getInstance() {
        if (instance == null) instance = new HomeViewImpl();
        return instance;
    }
}
