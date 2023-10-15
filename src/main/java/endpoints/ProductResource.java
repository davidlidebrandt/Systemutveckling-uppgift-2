package endpoints;

import constraints.CategoryCheck;
import entities.AddProduct;
import entities.Category;
import entities.ProductCopy;
import interceptors.Logging;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GetWarehouse;
import service.Warehouse;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Path("/products")
@Logging
public class ProductResource {
    private final static Logger logger = LoggerFactory.getLogger(ProductResource.class);
    Warehouse warehouse = GetWarehouse.getWarehouse();
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductCopy> getAllProducts() {
        return warehouse.getAllProducts();
    }

    @Path("/category/{category}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductCopy> getAllProducts(@PathParam("category") String category) {
        return warehouse.getProductsByCategory(Category.valueOf(category.toUpperCase()));
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") String id) {
        Warehouse warehouse = GetWarehouse.getWarehouse();
        ProductCopy product = warehouse.getProduct(id);
        if(!product.found()) {
            return Response.status(404).entity("No product with that ID found").build();
        }
        return Response.status(200).entity(product).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(AddProduct data) {
        Set<ConstraintViolation<AddProduct>> violations = validator.validate(data);
        if(!violations.isEmpty()) {
            for(var v : violations) {
                logger.error("Class: " + this.getClass() + " Method: addProduct" + " Error: " + v.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(v.getMessage()).build();
            }
        }

        String productId = warehouse.addProduct(data.name, Category.valueOf(data.category.toUpperCase()));
        if(Objects.equals(productId, "")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.CREATED).entity(productId).build();
    }
}
