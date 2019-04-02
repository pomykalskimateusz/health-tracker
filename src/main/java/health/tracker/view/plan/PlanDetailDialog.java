package health.tracker.view.plan;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import health.tracker.repository.plan.Plan;
import health.tracker.repository.plan.PlanRepository;
import health.tracker.repository.product.Product;
import health.tracker.repository.product.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

class PlanDetailDialog extends Dialog
{
    private TextField filterEdit = new TextField();
    private Select<String> fieldSelect = new Select<>("Name", "Calorific");
    private List<Product> products;
    private Grid<Product> productGrid = new Grid<>(Product.class);

    private Button save = new Button("Add this products");

    private String day;
    private Runnable runnable;

    private PlanRepository planRepository = new PlanRepository();

    PlanDetailDialog(String day, Runnable runnable)
    {
        this.runnable = runnable;
        this.day = day;
        this.setWidth("50vw");
        productGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productGrid.setHeightByRows(false);
        productGrid.setMaxHeight("250px");

        ProductRepository productRepository = new ProductRepository();
        products = productRepository.findAll();
        productGrid.setItems(products);

        setupFilter();

        save.addClickListener(event -> {
            productGrid.getSelectedItems().forEach(product -> {
                planRepository.save(preparePlanFrom(product, this.day));
            });
            close();
            this.runnable.run();
        });

        add(filterLayout(), productGrid, save);
    }

    private Plan preparePlanFrom(Product product, String day)
    {
        return Plan
                .builder()
                .day(day)
                .productId(product.getId())
                .build();
    }

    /**
     * @return HorizontalLayout which contain filter fields
     */
    private HorizontalLayout filterLayout()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.add(filterEdit, fieldSelect);

        return horizontalLayout;
    }

    /**
     * Method which setup filter properties and configure value change listener in filterEdit filed
     */
    private void setupFilter()
    {
        filterEdit.setLabel("Filter :");
        filterEdit.setPlaceholder("Filter");
        filterEdit.setValueChangeMode(ValueChangeMode.EAGER);

        filterEdit.addValueChangeListener(new HasValue.ValueChangeListener()
        {
            @Override
            public void valueChanged(HasValue.ValueChangeEvent valueChangeEvent)
            {
                productGrid.setItems(products
                        .stream()
                        .filter(product -> assertFilter(product, valueChangeEvent.getValue().toString()))
                        .collect(Collectors.toList()));
            }
        });

        fieldSelect.setValue("Name");
        fieldSelect.setLabel("Filter by :");
        fieldSelect.setPlaceholder("Select field");
    }

    /**
     * @param product - actually checked product from list
     * @param filterValue - value which we are looking for
     * @return boolean - depends if product contain specific filterValue
     */
    private boolean assertFilter(Product product, String filterValue)
    {
        switch (fieldSelect.getValue())
        {
            case "Name":
                return product.getName().contains(filterValue);
            case "Calorific":
                return product.getCalorific().toString().contains(filterValue);
            default:
                return false;
        }
    }
}
