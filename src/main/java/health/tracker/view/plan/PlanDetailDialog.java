package health.tracker.view.plan;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
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

    PlanDetailDialog()
    {
        this.setWidth("50vw");
        productGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productGrid.setHeightByRows(false);
        productGrid.setMaxHeight("250px");

        ProductRepository productRepository = new ProductRepository();
        products = productRepository.findAll();
        productGrid.setItems(products);
        setupFilter();

        add(filterLayout(), productGrid, save);
    }

    private HorizontalLayout filterLayout()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.add(filterEdit, fieldSelect);

        return horizontalLayout;
    }

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
