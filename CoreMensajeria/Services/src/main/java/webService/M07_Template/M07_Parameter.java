package webService.M07_Template;


import Classes.M07_Template.HandlerPackage.ParameterHandler;
import Classes.M07_Template.MessagePackage.Parameter;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/parameters")
@Produces(MediaType.APPLICATION_JSON)
public class M07_Parameter {

    public Gson gson = new Gson();

    @POST
    @Path("add")
    public void postParameter(@FormParam("name") String name,@FormParam("companyId") int companyId){
        ParameterHandler parameterHandler = new ParameterHandler();
        parameterHandler.postParameter(name,companyId);
    }
    @GET
    @Path("get")
    public Response getParameters(){
        ParameterHandler parameterHandler = new ParameterHandler();
        ArrayList<Parameter> parameterList = parameterHandler.getParameters();
        return Response.ok(gson.toJson(parameterList)).build();
    }

}