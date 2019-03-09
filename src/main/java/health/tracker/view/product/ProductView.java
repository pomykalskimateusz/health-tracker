package health.tracker.view.product;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import health.tracker.view.app.ApplicationLayout;

@Caption("Product")
@Icon(VaadinIcon.HOME)
@Route(value = "product", layout = ApplicationLayout.class)
public class ProductView extends VerticalLayout
{

}
