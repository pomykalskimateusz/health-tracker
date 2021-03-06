package health.tracker.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class Components
{
    private static final String EDIT_LABEL = "Edit";
    private static final String SAVE_LABEL = "Save";
    private static final String CANCEL_LABEL = "Cancel";

    public static Button editButton()
    {
        return createButton(EDIT_LABEL, VaadinIcon.EDIT);
    }

    public static Button saveButton()
    {
        return createButton(SAVE_LABEL, VaadinIcon.SAFE);
    }

    public static Button cancelButton()
    {
        return createButton(CANCEL_LABEL, VaadinIcon.CROP);
    }

    private static Button createButton(String label, VaadinIcon icon)
    {
        Button button = new Button(label);
        button.setIcon(new Icon(icon));

        return button;
    }
}
