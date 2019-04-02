package health.tracker.view.plan;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import health.tracker.repository.plan.PlanRepository;
import health.tracker.repository.product.Product;
import health.tracker.repository.profile.User;
import health.tracker.repository.profile.UserRepository;
import health.tracker.utils.BMIUtil;

import java.util.List;

class PlanListView extends VerticalLayout
{
    private Grid<Product> grid = new Grid<>(Product.class);
    private Button create = new Button("Add product");
    private Button delete = new Button("Delete product");
    private Label calorificLabel = new Label();

    private PlanDetailDialog planDetailDialog;

    private PlanRepository planRepository = new PlanRepository();

    private User user;
    private String day;
    private Double bmr;
    private Double currentCalorific;

    PlanListView(String day)
    {
        UserRepository userRepository = new UserRepository();
        user = userRepository.findById(1L);

        bmr = BMIUtil.calculateCalorieRequirement(user.getHeight(), user.getWeight(), user.getAge(), user.isFemale());

        this.day = day;
        planDetailDialog = new PlanDetailDialog(day, reloadData());
        setupButtons();

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setHeightByRows(true);
        grid.setMinHeight("120px");
        grid.setMaxHeight("300px");

        List<Product> products = planRepository.findProductsFor(day);

        grid.setItems(products);

        currentCalorific = currentCalorific(products);
        setCalorificLabel();

        add(new H3(day.toUpperCase()), grid, buttonsLayout());
    }
    /**
     * @return HorizontalLayout which contain buttons
     */
    private HorizontalLayout buttonsLayout()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(create, delete, calorificLabel);

        return horizontalLayout;
    }

    /**
     * Method which setup buttons configuration such as click listeners
     */
    private void setupButtons()
    {
        create.addClickListener(event -> planDetailDialog.open());

        delete.addClickListener(event -> {
            grid.getSelectedItems().forEach(product -> planRepository.delete(product.getId(), day));
            reloadData().run();
        });
    }

    /**
     * Method which reload our data in grid layout.
     */
    private Runnable reloadData()
    {
        return new Runnable() {
            @Override
            public void run() {
                grid.setItems(planRepository.findProductsFor(day));
            }
        };
    }

    /**
     * @param products - list of products
     * @return Double - summed up calorific of all products
     */
    private Double currentCalorific(List<Product> products)
    {
        return products
                .stream()
                .map(Product::getCalorific)
                .reduce((p_1, p_2) -> p_1 + p_2)
                .orElse( 0d);
    }

    /**
     * Method which map calorific to user friendly information
     */
    private void setCalorificLabel()
    {
        if(user.getHeight() != 0 && user.getWeight() != 0 && user.getAge() != 0)
        {
            if (currentCalorific > bmr)
                calorificLabel.setText("To much calories for today. Reduce your plan by removing " + (currentCalorific - bmr) + " calories.");
            else if (currentCalorific == bmr)
                calorificLabel.setText("Perfect! Your calories for today are suitable.");
            else
                calorificLabel.setText("Not enough calories for today. Reduce your plan by adding " + (bmr - currentCalorific) + " calories.");
        }
    }
}
