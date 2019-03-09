package health.tracker.view.profile;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import health.tracker.repository.profile.ProfileModel;
import health.tracker.repository.profile.ProfileRepository;

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

    private Binder<ProfileModel> binder = new Binder<>(ProfileModel.class);

    private ProfileModel profileModel;
    private ProfileRepository profileRepository;

    ProfileDetailDialog(ProfileModel profileModel)
    {
        this.profileModel = profileModel;
        profileRepository = new ProfileRepository();

        setupComponents();
        setupBindings();

        binder.readBean(profileModel);

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
        binder.forField(name).bind("name");
        binder.forField(age).bind("age");
        binder.forField(height).bind("height");
        binder.forField(weight).bind("weight");
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
            binder.writeBean(profileModel);
            profileRepository.save(profileModel);
            close();
        }
        catch (ValidationException exception)
        {
            System.out.println("Error something wrong during catching model from binder.");
        }
    }
}
