package ${package}.client.home;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.appview.Presenter;

/**
 * @author nikolasavic
 */
public interface HomeView extends IsElement {
    interface HomePresenter extends Presenter {
        void goToGreeting();
    }
}