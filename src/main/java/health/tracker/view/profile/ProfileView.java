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
import health.tracker.repository.profile.User;
import health.tracker.repository.profile.UserRepository;
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

    public ProfileView()
    {
        User user = userRepository.findById(1L);
        System.out.println("ZNALAZLO ? " + user.toString());
        profileDetailDialog = new ProfileDetailDialog(user,reloadData());
        setupComponents(user);

        add(new H1("User settings"));
        add(name, age, height, weight, editButton);
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
}
