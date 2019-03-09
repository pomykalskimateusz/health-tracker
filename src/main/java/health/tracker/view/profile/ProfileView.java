package health.tracker.view.profile;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import health.tracker.view.app.ApplicationLayout;

@Caption("Profile")
@Icon(VaadinIcon.HOME)
@Route(value = "", layout = ApplicationLayout.class)
public class ProfileView extends VerticalLayout
{
    public ProfileView()
    {
        add(new H1("Health Tracker"));
    }
}
