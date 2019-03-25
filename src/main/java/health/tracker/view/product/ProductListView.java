package health.tracker.view.product;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import health.tracker.repository.product.Product;

import java.util.List;

class ProductListView extends VerticalLayout
{
    Grid<Product> grid = new Grid<>(Product.class);

    ProductListView(List<Product> products)
    {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        grid.setItems(products);

        add(grid);
    }
}
