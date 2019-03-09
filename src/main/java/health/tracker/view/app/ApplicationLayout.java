package health.tracker.view.app;

import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.builder.interfaces.NavigationElementContainer;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.left.LeftNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.page.Push;
import health.tracker.view.plan.PlanView;
import health.tracker.view.product.ProductView;
import health.tracker.view.profile.ProfileView;

@Push
public class ApplicationLayout extends AppLayoutRouterLayout
{
    public ApplicationLayout()
    {
        init(prepareAppLayout());
    }

    private AppLayout prepareAppLayout()
    {
        return AppLayoutBuilder
                .get(Behaviour.LEFT_RESPONSIVE)
                .withTitle("Health Tracker")
                .withAppBar(AppBarBuilder.get().build())
                .withAppMenu(prepareMenu())
                .build();
    }

    private NavigationElementContainer prepareMenu()
    {
        return LeftAppMenuBuilder
                .get()
                .add(new LeftNavigationComponent(ProfileView.class))
                .add(new LeftNavigationComponent(PlanView.class))
                .add(new LeftNavigationComponent(ProductView.class))
                .build();
    }
}
