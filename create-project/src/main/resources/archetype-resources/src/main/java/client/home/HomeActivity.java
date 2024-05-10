package $

import com.dncomponents.client.views.appview.AbstractActivity;{package}.client.greeting.GreetingPlace;


public class HomeActivity extends AbstractActivity<HomeView, HomePlace> implements HomeView.HomePresenter {

    public HomeActivity(HomeView view, HomePlace place) {
        super(view, place);
    }

    @Override
    public void goToGreeting() {
        placeManager.goTo(new GreetingPlace());
    }
}