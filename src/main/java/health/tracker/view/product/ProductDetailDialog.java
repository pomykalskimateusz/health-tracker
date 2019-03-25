package health.tracker.view.product;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import health.tracker.repository.product.Product;
import health.tracker.repository.product.ProductRepository;
import health.tracker.view.Components;

class ProductDetailDialog extends Dialog
{
    private TextField name = new TextField("Name: ");
    private NumberField calorific = new NumberField("Calorific: ");

    private Button cancelButton = Components.cancelButton();
    private Button saveButton = Components.saveButton();

    private HorizontalLayout buttons = prepareButtonsLayout();
    private VerticalLayout layout = new VerticalLayout();

    private Binder<Product> binder = new Binder<>(Product.class);

    private Product product;
    private ProductRepository productRepository;

    ProductDetailDialog(Product product)
    {
        this.product = product;
        productRepository = new ProductRepository();

        setupBindings();
        setupComponents();

        if(product != null)
            binder.readBean(product);
        else
            binder.readBean(Product.empty());

        add(layout);
    }

    private void setupComponents()
    {
        this.setWidth("50vw");

        name.setPlaceholder("Enter name");
        name.setWidthFull();

        calorific.setPlaceholder("Enter calorific");
        calorific.setWidthFull();

        layout.add(name, calorific, buttons);
    }

    private void setupBindings()
    {
        binder.forField(name).bind("name");
        binder.forField(calorific).bind("calorific");
    }

    private HorizontalLayout prepareButtonsLayout()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        cancelButton.addClickListener(event -> close());

        saveButton.addClickListener(event -> saveButtonListener());

        horizontalLayout.add(saveButton);
        horizontalLayout.add(cancelButton);

        return horizontalLayout;
    }

    private void saveButtonListener()
    {
        try
        {
            binder.writeBean(product);
            productRepository.save(product);
            close();
        }
        catch (ValidationException exception)
        {
            System.out.println("Error something wrong during catching model from binder.");
        }
    }
}
