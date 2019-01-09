package webService.M10_Profile;

import Entities.M01_Login.Privilege;
import Logic.Command;
import Logic.CommandsFactory;
import Persistence.Factory.DAOAbstractFactory;
import Persistence.IDAOProfile;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/profiles")
public class M10_Profile {

    @GET
    @Path("/privileges")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivileges(@QueryParam("userId") int userId,
                                  @QueryParam("companyId") int companyId){
        Response response = null;
        try {
            // 1. Map DTO to Entity ...
            Command command = CommandsFactory.createGetPrivilegesByUserCompanyCommand(userId, companyId);
            command.execute();
            // 4. Map Entity to DTO
            // 5. Return DTO
            return Response.ok(new Gson().toJson(command.Return())).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        // 5. Return DTO
        return response;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(){

        return null;
    }
}
