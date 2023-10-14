package endpoints;

import entities.Category;
import entities.ProductCopy;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.GetWarehouse;
import service.Warehouse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/products")
public class ProductResource {

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductCopy> getAllProducts() {
        Warehouse warehouse = GetWarehouse.getWarehouse();
        return warehouse.getAllProducts();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") String id) {
        System.out.println(id);
        Warehouse warehouse = GetWarehouse.getWarehouse();
        ProductCopy product = warehouse.getProduct(id);
        if(!product.found()) {
            return Response.status(404).entity(product).build();
        }
        return Response.status(200).entity(product).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addProduct(@QueryParam("name") String name, @QueryParam("category") String category) {
        Warehouse warehouse = GetWarehouse.getWarehouse();
        return warehouse.addProduct(name, Category.valueOf(category));
    }
}
