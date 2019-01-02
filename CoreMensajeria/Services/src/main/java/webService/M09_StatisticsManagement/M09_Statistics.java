package webService.M09_StatisticsManagement;

import DTO.DTO;
import Entities.M02_Company.Company;
import Entities.M03_Campaign.Campaign;
import Entities.M04_Integrator.IntegratorFactory;
import Entities.M04_Integrator.Integrator;
import Entities.M09_Statistics.FilterType;
import Entities.M09_Statistics.SqlEstrella;
import Entities.M09_Statistics.Statistics;
import Entities.Sql;
import Exceptions.CampaignDoesntExistsException;
import Exceptions.ChannelNotFoundException;
import Exceptions.CompanyDoesntExistsException;
import Logic.Command;
import Logic.CommandsFactory;
import Logic.M09_Statistics.*;
import Mappers.CampaignMapper.MapperCampaignWithOut_Company;
import Mappers.CompanyMapper.MapperCompanyWithOut_Link;
import Mappers.GenericMapper;
import Factory.MapperFactory;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.*;
import java.util.List;


@Path( "/M09_Statistics" )
public class M09_Statistics extends Application {

    Gson gson = new Gson();
    private Connection connStar = SqlEstrella.getConInstance();
    private Connection conn = Sql.getConInstance();
    private GenericMapper mapper;
    private DTO dto;

    /* ====================
            Endpoints
    ======================= */

    @GET
    @Path("/companies")
    @Produces("application/json")
    public Response getAllCompanies(@QueryParam("userId") Integer userId) {
        /*
        String query = "SELECT com_id, com_name from m02_getcompaniesbyresponsible(" + userId + ") ORDER BY com_id;";
        try {
            return getCompanies(query);
        } catch(CompanyDoesntExistsException e) {
            return Response.serverError().build();
        }
        */
        GetAllCompaniesByUserCommand command = CommandsFactory.getAllCompaniesByUserCommand(userId);
        try {
            command.execute();
            mapper = new MapperCompanyWithOut_Link();
            return Response.ok(gson.toJson(
                    mapper.CreateDtoList(command.returnList())
            )).build();
        } catch(CompanyDoesntExistsException e) {
            return Response.serverError().build();
        }catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/campaigns")
    @Produces("application/json")
    public Response getCampaignsForCompany(@QueryParam("companyId") List<Integer> companyIds) {
/*
        String query = "SELECT DISTINCT cam_id, cam_name FROM m09_getAllCampaigns(";
        for (int i = 0; i < companyIds.size() - 1;  i++) {
            query += companyIds.get(i) + ", ";
        }
        query += companyIds.get(companyIds.size() - 1) + ") ORDER BY cam_id;";
        try {
            return getCampaigns(query);
        } catch(CampaignDoesntExistsException e) {
            return Response.serverError().build();
        }
        */

        GetCampaignsForCompanyCommand command = CommandsFactory.getCampaignsForCompanyCommand(companyIds);
        try {
            command.execute();
            mapper = new MapperCampaignWithOut_Company();
            return Response.ok(gson.toJson(
                    mapper.CreateDtoList(command.returnList())
            )).build();
        } catch(CampaignDoesntExistsException e) {
            return Response.serverError().build();
        }catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/channels")
    @Produces("application/json")
    public Response getAllChannels() {
        /*
        String query = "SELECT DISTINCT cha_id, cha_name FROM dim_channel ORDER BY cha_id;";
        ArrayList<Channel> channels = new ArrayList<>();
        try {
            Statement statement = connStar.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                ChannelFactory channelFactory = new ChannelFactory();
                Channel channel = channelFactory.getChannel(result.getInt("cha_id"), result.getString("cha_name"), null, null);
                channels.add(channel);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            SqlEstrella.bdClose(connStar);
        }
        return Response.ok(gson.toJson(channels)).build();
        */
        GetAllChannelsCommand command = CommandsFactory.getAllChannelsCommand();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.returnList())).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/integrators")
    @Produces("application/json")
    public Response getIntegratorsForChannel(@QueryParam("channelId") List<Integer> channelIds) {
        /*String query = "select int_id, int_name from m09_getIntegratorsByChannels(";
        for (int i = 0; i < channelIds.size() - 1;  i++) {
            query += channelIds.get(i) + ", ";
        }
        query += channelIds.get(channelIds.size() - 1) + ") ORDER BY int_id;";
        try {
            return getIntegrators(query);
        } catch(ChannelNotFoundException e) {
            return Response.serverError().build();
        }
        */
        GetIntegratorsForChannelCommand command = CommandsFactory.getIntegratorsForChannelCommand(channelIds);
        try {
            command.execute();
            return Response.ok(gson.toJson(
                    command.returnList()
            )).build();
        } catch (ChannelNotFoundException e) {
            return Response.serverError().build();
        }
    }
/*
    @GET
    @Path("/count")
    @Produces("application/json")
    public Response getCompaniesCount(@QueryParam("filter") String filter) {
        String filterAux = filter.toLowerCase();
        switch (filterAux) {
            case "companies": return getOverallCountFor(FilterType.company);
            case "campaigns": return getOverallCountFor(FilterType.campaign);
            case "channels": return getOverallCountFor(FilterType.channel);
            case "integrators": return getOverallCountFor(FilterType.integrator);
            default: return Response.status(400).entity("{ \"Mensaje\": \"No se envió ningun parametro o el parametro es incorrecto\" }").build();
        }
    }
*/
    @GET
    @Path("/companiesCount")
    @Produces("application/json")
    public Response getCompaniesCount() {
        //return getOverallCountFor(FilterType.company);
        Command command = CommandsFactory.getCompanyStatisticCommand();
        return getStadistic(command);
    }

    @GET
    @Path("/campaignsCount")
    @Produces("application/json")
    public Response getCampaignsCount() {
        //return getOverallCountFor(FilterType.campaign);
        Command command = CommandsFactory.getCampaignStatisticCommand();
        return getStadistic(command);
    }

    @GET
    @Path("/channelsCount")
    @Produces("application/json")
    public Response getChannelsCount() {
        //return getOverallCountFor(FilterType.channel);
        Command command = CommandsFactory.getChannelStatisticCommand();
        return getStadistic(command);
    }

    @GET
    @Path("/integratorsCount")
    @Produces("application/json")
    public Response getIntegratosCount() {
        //return getOverallCountFor(FilterType.integrator);
        Command command = CommandsFactory.getIntegratorStatisticCommand();
        return getStadistic(command);
    }

    private Response getStadistic(Command command) {
        try {
            command.execute();
            mapper = MapperFactory.createStatisticMapper();
            return Response.ok(gson.toJson(
                    mapper.CreateDto(command.Return())
            )).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/update")
    @Produces("application/json")
    public Response updateStarSchema() {
        Command command = CommandsFactory.updateStarSchema();
        try {
            command.execute();
            return Response.ok().build();
        } catch(SQLException e) {
            return Response.serverError().build();
        } catch(Exception e) {
            return Response.serverError().build();
        }
    }
    public Response getOverallCountFor(FilterType filterType) {
        /*
        String query = queryForOverallCount(filterType);
        Statistics Statistic = new Statistics();
        try {
            Statement statement = connStar.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Statistic.addX(result.getString(filterType.value()));
                Statistic.addY(result.getInt("messages"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            SqlEstrella.bdClose(connStar);
        }
        return Response.ok(gson.toJson(Statistic)).build();
        */
        return null;
    }

    public String queryForOverallCount(FilterType filterType) {
        switch (filterType) {
            case company:
                return "SELECT DISTINCT c.com_id, c.com_name, messages from dim_company_campaign c, " +
                        "(select sen_com_id, count(*) as messages from fact_sent_message " +
                        "group by sen_com_id) as m where c.com_id = m.sen_com_id ORDER BY c.com_id ASC;";
            case campaign:
                return "SELECT DISTINCT c.cam_id, c.cam_name, messages from dim_company_campaign c, " +
                        "(select sen_cam_id, count(*) as messages from fact_sent_message " +
                        "group by sen_cam_id) as m where c.cam_id = m.sen_cam_id ORDER BY c.cam_id ASC;";
            case channel:
                return "SELECT DISTINCT c.cha_id, c.cha_name, messages from dim_channel c, " +
                        "(select sen_cha_id, count(*) as messages from fact_sent_message " +
                        "group by sen_cha_id) as m where c.cha_id = m.sen_cha_id ORDER BY c.cha_id ASC;";
            case integrator:
                return "SELECT DISTINCT i.int_id, i.int_name, messages from dim_integrator i, " +
                    "(select sen_int_id, count(*) as messages from fact_sent_message " +
                    "group by sen_int_id) as m where i.int_id = m.sen_int_id ORDER BY i.int_id ASC;";
            default: return "";
        }
    }

    private Response getCompanies(String query) throws CompanyDoesntExistsException {

        ArrayList<Company> companies = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Company company = new Company(result.getInt("com_id"), result.getString("com_name"), "", true);
                companies.add(company);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new CompanyDoesntExistsException(e);
        } finally {
            Sql.bdClose(conn);
        }
        return Response.ok(gson.toJson(companies)).build();
    }

    public Response getCampaigns(String query) throws CampaignDoesntExistsException {
        ArrayList<Campaign> campaigns = new ArrayList<>();
        try {
            Statement statement = connStar.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Campaign campaign = new Campaign();
                campaign.set_idCampaign(result.getInt("cam_id"));
                campaign.set_nameCampaign(result.getString("cam_name"));
                campaigns.add(campaign);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new CampaignDoesntExistsException(e);
        } finally {
            SqlEstrella.bdClose(connStar);
        }
        return Response.ok(gson.toJson(campaigns)).build();
    }

    public Response getIntegrators(String query) throws ChannelNotFoundException {
        ArrayList<Integrator> integrators = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Integrator integrator = IntegratorFactory.getIntegrator(result.getString("int_name"), result.getInt("int_id"),
                        result.getString("int_name"), 0, 0, "", true);
                integrators.add(integrator);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new ChannelNotFoundException(e);
        } finally {
            Sql.bdClose(conn);
        }
        return Response.ok(gson.toJson(integrators)).build();
    }

    //Endpoint que Devuelve Todos los Años donde ha habido envío de mensajes
    @GET
    @Path("/yearsCount")
    @Produces("application/json")
    public Response getYears(){
        GetYearsCommand command = CommandsFactory.getYears();
        try{
            command.execute();
            return Response.ok(gson.toJson(command.returnList())).build();
        } catch(Exception e) {
            return Response.serverError().build();
        }
    }

    //Endpoint que Devuelve Todos los Meses donde ha habido envío de mensajes
    @GET
    @Path("/monthsCount")
    @Produces("application/json")
    public Response getMonths(){
        GetMonthsCommand command = CommandsFactory.getMonths();
        try{
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        } catch(Exception e) {
            return Response.serverError().build();
        }
    }

    //Endpoint que Devuelve Todos los días de la semana donde ha habido envío de mensajes
    @GET
    @Path("/daysofweekCount")
    @Produces("application/json")
    public Response getDaysofWeek(){
        GetDaysofWeekCommand command = CommandsFactory.getDaysofWeek();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        }  catch (Exception e){
            return Response.serverError().build();
        }
    }

    //Endpoint que Devuelve Todos los días del mes donde ha habido envío de mensajes
    @GET
    @Path("/daysofmonthCount")
    @Produces("application/json")
    public Response getDaysofMonth(){
        GetDaysofMonthCommand command = CommandsFactory.getDaysofMonth();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        }  catch (Exception e){
            return Response.serverError().build();
        }
    }

    //Endpoint que Devuelve Todos los días del año donde ha habido envío de mensajes
    @GET
    @Path("/daysofyearCount")
    @Produces("application/json")
    public Response getDaysofYear(){
        GetDaysofYearCommand command = CommandsFactory.getDaysofYear();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        }  catch (Exception e){
            return Response.serverError().build();
        }
    }

    //Endpoint que Devuelve Todas las semanas del año donde ha habido envío de mensajes
    @GET
    @Path("/weeksofyearCount")
    @Produces("application/json")
    public Response getWeeksofYear(){
        GetWeeksofYearCommand command = CommandsFactory.getWeeksofYear();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        }  catch (Exception e){
            return Response.serverError().build();
        }
    }

    //Endpoint que devuelve los cuartos del año
    @GET
    @Path("/quartersofyearCount")
    @Produces("application/json")
    public Response getQuartersofYear(){
        GetQuartersofYearCommand command = CommandsFactory.getQuartersofYear();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        }  catch (Exception e){
            return Response.serverError().build();
        }
    }

    //Endpoint que devuelve las horas del día donde se han enviado mensaje
    @GET
    @Path("/hoursCount")
    @Produces("application/json")
    public Response getHours(){
        GetHoursCommand command = CommandsFactory.getHours();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        }  catch (Exception e){
            return Response.serverError().build();
        }
    }

    //Endpoint que devuelve los minutos donde se han enviado mensaje
    @GET
    @Path("/minutesCount")
    @Produces("application/json")
    public Response getMinutes(){
        GetMinutesCommand command = CommandsFactory.getMinutes();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        }  catch (Exception e){
            return Response.serverError().build();
        }
    }

    //Endpoint que devuelve los segundos donde se han enviado mensaje
    @GET
    @Path("/secondsCount")
    @Produces("application/json")
    public Response getSeconds(){
        GetSecondsCommand command = CommandsFactory.getSeconds();
        try {
            command.execute();
            return Response.ok(gson.toJson(command.ReturnList())).build();
        }  catch (Exception e){
            return Response.serverError().build();
        }
    }

    //Endpoint que devuelve la cantidad de Mensajes según los filtros enviados(compañia, campaña, canal, integrador, tiempo)
    @GET
    @Path("/filters")
    @Produces("application/json")
    public Response getStatistics(@QueryParam("companyId") List<Integer> companyIds,
                                  @QueryParam("campaignId") List<Integer> campaignIds,
                                  @QueryParam("channelId") List<Integer> channelIds,
                                  @QueryParam("integratorId") List<Integer> integratorIds,
                                  @QueryParam("yearId") List<Integer> yearIds,
                                  @QueryParam("monthId") List<Integer> monthIds,
                                  @QueryParam("dayofweekId") List<Integer> dayofweekIds,
                                  @QueryParam("weekofyearId") List<Integer> weekofyearIds,
                                  @QueryParam("dayofmonthId") List<Integer> dayofmonthIds,
                                  @QueryParam("dayofyearId") List<Integer> dayofyearIds,
                                  @QueryParam("hourId") List<Integer> hourofdayIds,
                                  @QueryParam("minuteId") List<Integer> minuteofhourIds,
                                  @QueryParam("secondId") List<Integer> secondofminuteIds,
                                  @QueryParam("quarterId") List<Integer> quarterIds)
    {
        String companyin = setParametersforQuery(companyIds,"and me.sen_com_id in ");
        String campaignin = setParametersforQuery(campaignIds,"and me.sen_cam_id in ");
        String channelin = setParametersforQuery(channelIds,"and me.sen_cha_id in ");
        String integratorin = setParametersforQuery(integratorIds, "and me.sen_int_id in");
        String yearin = setParametersforQuery(yearIds, "and da.dat_year in");
        String monthin = setParametersforQuery(monthIds, "and da.dat_month in");
        String dayofweekin = setParametersforQuery(dayofweekIds,"and da.dat_dayofweek in");
        String weekofyearin = setParametersforQuery(weekofyearIds, "and da.dat_weekofyear in");
        String dayofmonthin = setParametersforQuery(dayofmonthIds, "and da.dat_dayofmonth in");
        String dayofyearin = setParametersforQuery(dayofyearIds, "and da.dat_dayofyear in");
        String hourin = setParametersforQuery(hourofdayIds, "and da.dat_hourofday in");
        String minutein = setParametersforQuery(minuteofhourIds, "and da.dat_minuteofhour in");
        String secondin = setParametersforQuery(secondofminuteIds, "and da.dat_secondofminute in");
        String quarterin = setParametersforQuery(quarterIds, "and da.dat_quarterofyear in");
        Map<String, Statistics> stats = new HashMap<String, Statistics>();
        try {
            Statement st = connStar.createStatement();
            if (!companyIds.isEmpty()) {
                stats.put("companies", getMessagesParam(companyin, campaignin, channelin, integratorin, yearin, monthin, dayofweekin,
                        weekofyearin, dayofmonthin, dayofyearin, hourin, minutein, secondin, quarterin,"me.sen_com_id", "co.com_name",
                        ", public.dim_company_campaign co", "co.com_id",", public.dim_date da",
                        " and da.dat_id = me.sen_dat_id ",st));
                //stats.add();
            }
            if (!campaignIds.isEmpty()) {
                stats.put("campaigns", getMessagesParam(companyin, campaignin, channelin, integratorin, yearin, monthin, dayofweekin,
                        weekofyearin, dayofmonthin, dayofyearin, hourin, minutein, secondin, quarterin, "me.sen_cam_id",
                        "ca.cam_name", ", public.dim_company_campaign ca", "ca.cam_id",", public.dim_date da",
                        " and da.dat_id = me.sen_dat_id ", st));
                //stats.add();
            }
            if (!channelIds.isEmpty()) {
                stats.put("channels", getMessagesParam(companyin, campaignin, channelin, integratorin, yearin, monthin, dayofweekin,
                        weekofyearin, dayofmonthin, dayofyearin, hourin, minutein, secondin, quarterin, "me.sen_cha_id",
                        "ch.cha_name", ", public.dim_channel ch", "ch.cha_id", ", public.dim_date da",
                        " and da.dat_id = me.sen_dat_id ", st));
                //stats.add();
            }
            if (!integratorIds.isEmpty()) {
                stats.put("integrators", getMessagesParam(companyin, campaignin, channelin, integratorin, yearin, monthin, dayofweekin,
                        weekofyearin, dayofmonthin, dayofyearin, hourin, minutein, secondin, quarterin,"me.sen_int_id",
                        "int.int_name", ", public.dim_integrator int", "int.int_id",", public.dim_date da",
                        " and da.dat_id = me.sen_dat_id ",st));
                //stats.add();
            }
            if (channelIds.isEmpty() && campaignIds.isEmpty() && companyIds.isEmpty() && integratorIds.isEmpty()){
                return Response.status(400).entity("{ \"Mensaje\": \"Debe enviar al menos un parametro\" }").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Sql.bdClose(connStar);
        }
        return Response.ok(gson.toJson(stats)).build();
    }


    //Método que devuelve la consulta de mensajes enviados, agrupada por los filtros enviados
    public Statistics getMessagesParam(String companyIds, String campaignIds, String channelIds, String integratorIds, String yearIds,
                                       String monthIds, String dayofweekIds, String weekofyearIds, String dayofmonthIds, String dayofyearIds,
                                       String hourIds, String minuteIds, String secondIds, String quarterIds ,String param1, String param2,
                                       String param3, String param4, String param5, String param6, Statement st){
        int num;
        String name;
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<Integer> listNum = new ArrayList<>();
        Statistics gr = new Statistics();
        try {
            String select = "SELECT icount, paramName FROM m09_get_MessageParameter('"+ companyIds + "','" + campaignIds + "','" +
                    channelIds + "','" + integratorIds + "','" + yearIds + "','" + monthIds + "','" + dayofweekIds + "','" +
                    weekofyearIds + "','" + dayofmonthIds + "','" + dayofyearIds + "','" + hourIds + "','" + minuteIds + "','" +
                    secondIds + "','" + quarterIds + "','" + param1 + "','" + param2 + "','" + param3 + "','" + param4 + "','" +
                    param5 + "','" + param6 + "')";
            System.out.println(select);
            ResultSet result = st.executeQuery( select );
            while ( result.next() ) {
                num = result.getInt("icount");
                name = result.getString("paramName");
                listNum.add( num );
                listName.add( name );
                gr.x = listName;
                gr.y = listNum;
            }
        }
        catch ( SQLException e ) {
            e.printStackTrace();
            // throw new SQLException();
        }
        return gr;
    }

    //Método que arma las condiciones para la consulta de mensajes enviados
    public String setParametersforQuery(List<Integer> ids, String params){
        if (ids.isEmpty()) {
            return "";
        }
        params = params.concat("(");
        for(int i=0;i<ids.size();i++){
            params = params.concat(ids.get(i).toString());
            if (i == ids.size()-1){
                params = params.concat(")");
            }
            else{
                params = params.concat(",");
            }
        }
        return params;
    }

}

