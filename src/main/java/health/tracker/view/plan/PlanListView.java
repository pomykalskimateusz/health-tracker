package health.tracker.view.plan;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import health.tracker.repository.product.Product;

import java.util.Collections;
import java.util.List;

class PlanListView extends VerticalLayout
{
    private Grid<Product> grid = new Grid<>(Product.class);
    private Button create = new Button("Add product");
    private Button delete = new Button("Delete product");
    private PlanDetailDialog planDetailDialog = new PlanDetailDialog();

    PlanListView(String day)
    {
        setupButtons();

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(fakeProducts());
        grid.setHeightByRows(true);
        grid.setMinHeight("120px");
        grid.setMaxHeight("300px");

        add(new H3(day.toUpperCase()), grid, buttonsLayout());
    }

    private HorizontalLayout buttonsLayout()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(create, delete);

        return horizontalLayout;
    }

    private void setupButtons()
    {
        delete.setEnabled(false);

        create.addClickListener(event -> planDetailDialog.open());
    }

    private List<Product> fakeProducts()
    {
        return Collections.singletonList(Product
                .builder()
                .id(1L)
                .name("Apple")
                .calorific(12.3)
                .build());
    }
}
