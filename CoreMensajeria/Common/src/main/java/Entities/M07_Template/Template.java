package Entities.M07_Template;

import Entities.M01_Login.User;
import Entities.M03_Campaign.Campaign;
import Entities.M06_DataOrigin.Application;
import Entities.M05_Channel.Channel;
import Entities.M07_Template.MessagePackage.*;
import Entities.M07_Template.PlanningPackage.Planning;
import Entities.M07_Template.StatusPackage.*;

import java.util.*;

/**
 * Template class uses for storing template information
 */
public class Template {
    /**
     * Message of the template (with the parameters)
     */
    private Message message;
    /**
     * date of creation of the template
     */
    private String creationDate;
    /**
     * id of the template
     */
    private int templateId;
    /**
     * status of the template (approved or not approved)
     */
    private Status status;
    /**
     * channels (sms and/or email) of the template (with the integratos)
     */
    private ArrayList<Channel> channels;
    /**
     * campaing of the template
     */
    private Campaign campaign;
    /**
     * origin of application of the template
     */
    private Application application;
    /**
     * user creator of the template
     */
    private User user;
    /**
     * planning of the template
     */
    private Planning planning;

    /**
     * empty builder
     */
    public Template() {}

    /**
     * builder with only the template id.
     * @param templateId template id
     */
    public Template(int templateId) {
        this.templateId = templateId;
    }

    /**
     * builder with message, status, day of creation and id of the template.
     * @param message message of the template
     * @param status status of the template
     * @param creationDate date of creation of the template
     * @param templateId template id
     */
    public Template(Message message, Status status, String creationDate, int templateId) {
        this.message = message;
        this.status = status;
        this.creationDate = creationDate;
        this.templateId = templateId;
    }

    /**
     * show id template.
     * @return id template
     */
    public int getTemplateId(){
        return templateId;
    }

    /**
     * add id template.
     * @param templateId new id template
     */
    public void setTemplateId(int templateId){
        this.templateId = templateId;
    }

    /**
     * show date of creation of the template.
     * @return date of creation
     */
    public String getCreationDate(){
        return creationDate;
    }

    /**
     * add date of creation of the template.
     * @param creationDate new date of creation
     */
    public void setCreationDate(String creationDate){
        this.creationDate = creationDate;
    }

    /**
     * show current status of the template.
     * @return status (approved or not approved)
     */
    public Status getStatus() {
        return status;
    }

    /**
     * add/modify status of the template
     * @param status new status
     */
    public void setStatus(Status status) { this.status = status; }

    /**
     * show the message of the template with the parameters
     * @return message and parameters of the template
     */
    public Message getMessage(){ return message; }

    /**
     * add/modify message and parameters of the template
     * @param message new message and parameters
     */
    public void setMessage(Message message) { this.message = message; }

    /**
     * show the channels (sms or/and email) with the integrators
     * @return channels and integrators of the template
     */
    public ArrayList<Channel> getChannels() {
        return channels;
    }

    /**
     * add/modify the channels (sms or/and email) and the integrators
     * @param channels new channels and integrators
     */
    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public void addChannel(Channel channel){
        channels.add(channel);
    }

    /**
     * show the campaign of the template
     * @return campaign of the template
     */
    public Campaign getCampaign() {
        return campaign;
    }

    /**
     * add/modify the campaign of the template
     * @param campaign new campaign
     */
    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    /**
     * show the origin of application of the template
     * @return origin of application of the template
     */
    public Application getApplication() {
        return application;
    }

    /**
     * add/modify the origin of application of the template
     * @param application new origin of application
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * show user creator of the template
     * @return  user creator of the template
     */
    public User getUser() {
        return user;
    }

    /**
     * add user creator of the template
     * @param user  new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * show planning of the template
     * @return planning of the template
     */
    public Planning getPlanning() {
        return planning;
    }

    /**
     * add/modify planning of the template
     * @param planning new planning
     */
    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Template)) return false;
        Template template = (Template) o;
        return getTemplateId() == template.getTemplateId() &&
                Objects.equals(message, template.message) &&
                Objects.equals(getCreationDate(), template.getCreationDate()) &&
                Objects.equals(status, template.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, getCreationDate(), getTemplateId(), status);
    }

}
