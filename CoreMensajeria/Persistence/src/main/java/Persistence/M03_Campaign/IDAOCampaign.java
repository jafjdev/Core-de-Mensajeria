package Persistence.M03_Campaign;

import Entities.Entity;
import Entities.M03_Campaign.Campaign;
import Persistence.IDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDAOCampaign extends IDAO {

    Entity campaign(Entity e ) throws SQLException;

    void changeStatusCampaign( Entity e);

    Entity campaignById( Entity e);

    ArrayList<Entity> campaignListByUserCompany( Entity _u , Entity _comp );

    ArrayList<Entity> campaignListByUser( Entity e );

    ArrayList<Entity> campaignList();

    Campaign getCampaign(ResultSet _result) throws SQLException;

}