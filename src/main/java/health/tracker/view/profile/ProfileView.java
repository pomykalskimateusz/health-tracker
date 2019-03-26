package health.tracker.view.profile;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import health.tracker.repository.profile.User;
import health.tracker.repository.profile.UserRepository;
import health.tracker.utils.BMIUtil;
import health.tracker.view.Components;
import health.tracker.view.app.ApplicationLayout;

@Caption("User")
@Icon(VaadinIcon.HOME)
@Route(value = "", layout = ApplicationLayout.class)
public class ProfileView extends VerticalLayout
{
    private TextField name = new TextField("Name: ");
    private NumberField age = new NumberField("Age: ");
    private NumberField height = new NumberField("Height: ");
    private NumberField weight = new NumberField("Weight: ");

    private Button editButton = Components.editButton();

    private UserRepository userRepository = new UserRepository();

    private ProfileDetailDialog profileDetailDialog;
    private User user;
    private Double bmi;

    public ProfileView()
    {
        user = userRepository.findById(1L);
        bmi = BMIUtil.calculateBMI(user.getHeight(), user.getWeight());
        profileDetailDialog = new ProfileDetailDialog(user, reloadData());

        setupComponents(user);

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.add(formLayout(), plainTextLayout());

        add(mainLayout);
    }

    private void setupComponents(User user)
    {
        name.setWidth("20vw");
        name.setEnabled(false);
        name.setValue(user.getName());

        age.setWidth("20vw");
        age.setEnabled(false);
        age.setValue(user.getAge());

        height.setWidth("20vw");
        height.setEnabled(false);
        height.setValue(user.getHeight());

        weight.setWidth("20vw");
        weight.setEnabled(false);
        weight.setValue(user.getWeight());

        editButton.addClickListener(event -> profileDetailDialog.open());
    }

    private Runnable reloadData()
    {
        return new Runnable() {
            @Override
            public void run() {
                setupComponents(userRepository.findById(1L));
            }
        };
    }

    private VerticalLayout formLayout()
    {
        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.add(new H1("User settings"), name, age, height, weight, editButton);

        return verticalLayout;
    }

    private VerticalLayout plainTextLayout()
    {
        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.add(new H1("Basal Metabolic Rate and Body Mass Index"));

        if(user.getWeight() != 0 && user.getHeight() != 0) {
            verticalLayout.add(new Label("Your BMI rate : " + bmi));
            verticalLayout.add(new Label("Your BMI classification : " + BMIUtil.interpretationBMI(bmi)));
        }

        verticalLayout.add(new Label("Body mass index (BMI) is a value derived from the mass (weight) and height of an individual. \n" +
        "It's used to categorize that person as underweight, normal weight, overweight, or obese based on that value."));

        verticalLayout.add(new Label("Basal metabolic rate (BMR) is the rate of energy expenditure per unit time. \n" +
        "There are several ways to calculate BMR rate. In this application BMR is separated gender. Below there are used patterns : \n"));
        verticalLayout.add(new Label("- female pattern : 655 + [9,6 x masa ciała (kg)] + [1,8 x wzrost (cm)] - [4,7 x wiek (lata)] \n"));
        verticalLayout.add(new Label("- male pattern : 66 + [13,7 x masa ciała (kg)] + [5 x wzrost (cm)] - [6,76 x wiek (lata)]"));

        return verticalLayout;
    }
}
