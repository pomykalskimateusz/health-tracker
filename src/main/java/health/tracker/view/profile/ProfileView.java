package health.tracker.view.profile;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import health.tracker.view.app.ApplicationLayout;

@Route(value = "", layout = ApplicationLayout.class)
public class ProfileView extends VerticalLayout
{
    public ProfileView()
    {
        add(new H1("Health Tracker"));
    }
}
