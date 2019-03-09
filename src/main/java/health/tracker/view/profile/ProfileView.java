package health.tracker.view.profile;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import health.tracker.model.ProfileModel;
import health.tracker.view.app.ApplicationLayout;

@Caption("Profile")
@Icon(VaadinIcon.HOME)
@Route(value = "", layout = ApplicationLayout.class)
public class ProfileView extends VerticalLayout
{
    private final String profileName = "Mateo";
    private final Double profileAge = 23d;
    private final Double profileHeight = 175d;
    private final Double profileWeight = 65d;

    private TextField name = new TextField("Name: ");
    private NumberField age = new NumberField("Age: ");
    private NumberField height = new NumberField("Height: ");
    private NumberField weight = new NumberField("Weight: ");

    private Button editButton = Components.editButton();
    private ProfileDetailDialog profileDetailDialog = new ProfileDetailDialog(new ProfileModel(profileName, profileAge, profileHeight,profileWeight));

    public ProfileView()
    {
        setupComponents();
        add(new H1("Profile settings"));
        add(name, age, height, weight, editButton);
    }

    private void setupComponents()
    {
        name.setWidth("20vw");
        name.setEnabled(false);
        name.setValue(profileName);

        age.setWidth("20vw");
        age.setEnabled(false);
        age.setValue(profileAge);

        height.setWidth("20vw");
        height.setEnabled(false);
        height.setValue(profileHeight);

        weight.setWidth("20vw");
        weight.setEnabled(false);
        weight.setValue(profileWeight);

        editButton.addClickListener(event -> profileDetailDialog.open());
    }
}
