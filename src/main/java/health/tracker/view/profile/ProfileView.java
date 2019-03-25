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

        verticalLayout.add(new H1("Some theory"));
        verticalLayout.add(new Label(THEORY_PLAIN_TEXT));

        return verticalLayout;
    }

    private final String THEORY_PLAIN_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ullamcorper commodo feugiat. Proin in elit suscipit, faucibus mi eget, vestibulum felis. Donec placerat faucibus nunc id interdum. Quisque vel fringilla turpis. Etiam a odio a libero lobortis dignissim a sit amet enim. Sed vel lorem ante. Donec tincidunt, massa ut convallis congue, ante arcu tincidunt risus, in fringilla ex augue tempor urna. Morbi sodales eros quis augue sollicitudin, in ultricies nulla rhoncus. Nulla in tincidunt mi, id tempus eros. Sed at lorem urna. In a aliquet ante, sed scelerisque arcu. Nulla tempor vehicula nibh eu placerat. Phasellus feugiat congue est, quis suscipit odio finibus sit amet. Duis ac neque eget est pulvinar viverra. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.";
}
