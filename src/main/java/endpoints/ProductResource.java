package endpoints;

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
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
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
    public Response addProduct(AddProduct data) {
        Set<ConstraintViolation<AddProduct>> violations = validator.validate(data);
        if(!violations.isEmpty()) {
            for(var v : violations) {
                logger.error("Class: " + this.getClass() + " Method: addProduct" + " Error: " + v.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(v.getMessage()).build();
            }
        }

        Warehouse warehouse = GetWarehouse.getWarehouse();
        String productId = warehouse.addProduct(data.name, Category.valueOf(data.category));
        if(Objects.equals(productId, "")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.CREATED).entity(productId).build();
    }
}
