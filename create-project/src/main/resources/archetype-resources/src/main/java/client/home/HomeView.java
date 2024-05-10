package $

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.appview.Presenter;

public interface HomeView extends IsElement {
    interface HomePresenter extends Presenter {
        void goToGreeting();
    }
}