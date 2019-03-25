package health.tracker.view.profile;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import health.tracker.repository.profile.User;
import health.tracker.repository.profile.UserRepository;
import health.tracker.view.Components;

class ProfileDetailDialog extends Dialog
{
    private TextField name = new TextField("Name: ");
    private NumberField age = new NumberField("Age: ");
    private NumberField height = new NumberField("Height: ");
    private NumberField weight = new NumberField("Weight: ");

    private Button cancelButton = Components.cancelButton();
    private Button saveButton = Components.saveButton();

    private HorizontalLayout buttons = prepareButtonsLayout();
    private VerticalLayout layout = new VerticalLayout();

    private Binder<User> binder = new Binder<>(User.class);

    private User user;
    private UserRepository userRepository;

    Runnable runnable;

    ProfileDetailDialog(User user, Runnable runnable)
    {
        this.runnable = runnable;
        this.user = user;
        userRepository = new UserRepository();

        setupBindings();
        setupComponents();

        if(user == null)
            this.user = User.empty();

        binder.readBean(this.user);

        add(layout);
    }

    private void setupComponents()
    {
        this.setWidth("50vw");

        name.setPlaceholder("Enter name");
        name.setWidthFull();

        age.setPlaceholder("Enter age");
        age.setWidthFull();

        height.setPlaceholder("Enter height");
        height.setWidthFull();

        weight.setPlaceholder("Enter weight");
        weight.setWidthFull();

        layout.add(name, age, height, weight, buttons);
    }

    private void setupBindings()
    {
        binder.forField(name).bind(User::getName, User::setName);
        binder.forField(age).bind(User::getAge, User::setAge);
        binder.forField(height).bind(User::getHeight, User::setHeight);
        binder.forField(weight).bind(User::getWeight, User::setWeight);
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
            binder.writeBean(user);
            userRepository.updateById(1L, user);
            close();
            runnable.run();
        }
        catch (ValidationException exception)
        {
            System.out.println("Error something wrong during catching model from binder.");
        }
    }
}
