package health.tracker.view.plan;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import health.tracker.view.app.ApplicationLayout;

@Caption("Plan")
@Icon(VaadinIcon.HOME)
@Route(value = "plan", layout = ApplicationLayout.class)
public class PlanView extends VerticalLayout
{
    public PlanView()
    {
        add(new PlanListView("Monday"),
            new PlanListView("Tuesday"),
            new PlanListView("Wednesday"),
            new PlanListView("Thursday"),
            new PlanListView("Friday"),
            new PlanListView("Saturday"),
            new PlanListView("Sunday"));
    }
}
