package health.tracker.view.plan;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import health.tracker.repository.plan.PlanRepository;
import health.tracker.repository.product.Product;

class PlanListView extends VerticalLayout
{
    private Grid<Product> grid = new Grid<>(Product.class);
    private Button create = new Button("Add product");
    private Button delete = new Button("Delete product");
    private PlanDetailDialog planDetailDialog;

    private PlanRepository planRepository = new PlanRepository();

    private String day;

    PlanListView(String day)
    {
        this.day = day;
        planDetailDialog = new PlanDetailDialog(day, reloadData());
        setupButtons();

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setHeightByRows(true);
        grid.setMinHeight("120px");
        grid.setMaxHeight("300px");

        reloadData().run();
        planRepository.findProductsFor(day).forEach(plan -> System.out.println(plan.toString()));
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

    private Runnable reloadData()
    {
        return new Runnable() {
            @Override
            public void run() {
                grid.setItems(planRepository.findProductsFor(day));
            }
        };
    }
}
