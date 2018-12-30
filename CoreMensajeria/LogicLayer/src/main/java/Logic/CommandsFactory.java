package Logic;

import Entities.Entity;
import Logic.M01_Login.GetUser;
import Logic.M09_Statistics.*;
import Logic.M02_Company.AddCompanyCommand;

import java.util.List;

public class CommandsFactory {

    public static Command instanciateGetUser ( Entity user) {
        return new GetUser(user);
    }

    public static getAllCompaniesByUserCommand getAllCompaniesByUserCommand(Integer userId) {return new getAllCompaniesByUserCommand(userId); }
    public static getCampaignsForCompanyCommand getCampaignsForCompanyCommand(List<Integer> companyIds){return new getCampaignsForCompanyCommand(companyIds);}
    public static getAllChannelsCommand getAllChannelsCommand() {return new getAllChannelsCommand();}

    public static AddCompanyCommand createAddCompanyCommand( Entity _co ){ return new AddCompanyCommand( _co ); }



}
