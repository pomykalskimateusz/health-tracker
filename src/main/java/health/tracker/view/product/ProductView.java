package health.tracker.view.product;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import health.tracker.repository.product.Product;
import health.tracker.repository.product.ProductRepository;
import health.tracker.view.app.ApplicationLayout;

import java.util.List;
import java.util.stream.Collectors;

@Caption("Product")
@Icon(VaadinIcon.HOME)
@Route(value = "product", layout = ApplicationLayout.class)
public class ProductView extends VerticalLayout
{
    private TextField filterEdit = new TextField();
    private Select<String> fieldSelect = new Select<>("Calorific", "Name");
    private ProductDetailDialog productDetailDialog = new ProductDetailDialog(null);


    private ProductListView productListView;
    private List<Product> products;

    public ProductView()
    {
        ProductRepository productRepository = new ProductRepository();
        products = productRepository.findAll();

        productListView = new ProductListView(products);

        setupFilter();

        Button createButton = new Button("Create");
        createButton.addClickListener(event -> productDetailDialog.open());

        add(filterLayout(), productListView, createButton);
    }

    private HorizontalLayout filterLayout()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.add(filterEdit);
        horizontalLayout.add(fieldSelect);

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
                productListView.grid.setItems(products
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
