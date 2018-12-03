package Classes.M07_Template.HandlerPackage;

import Classes.M07_Template.MessagePackage.Message;
import Classes.M07_Template.MessagePackage.Parameter;
import Classes.M07_Template.Template;
import Classes.Sql;
import Exceptions.MessageDoesntExistsException;
import Exceptions.ParameterDoesntExistsException;
import com.google.gson.JsonArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * MessageHandler class used for the management of information
 * search in the database with respect to Message.
 */
public class MessageHandler {
    /**
     * connection to the database
     */
    public static Sql sql;

    /**
     * constructor without receiving parameters that instantiates
     * the sql attribute of the MessageHandler class.
     */
    public MessageHandler() {
        sql = new Sql();
    }

    /**
     * this method returns a list of templates where each contains
     * its message and associated parameters.
     * @param templateArrayList arrayList of template without message
     * @return arrayList of template with message
     */
    public ArrayList<Template> getMessages(ArrayList<Template> templateArrayList){
        try {
            Connection connection = Sql.getConInstance();
            PreparedStatement preparedStatement = connection.prepareCall("{call m07_select_messages(?)}");
            for(int x = 0; x < templateArrayList.size(); x++){
                preparedStatement.setInt(1, templateArrayList.get(x).getTemplateId());
                ResultSet resultSet = preparedStatement.executeQuery();
               /*ResultSet resultSet = sql.sqlConn("SELECT * FROM PUBLIC.MESSAGE WHERE MES_TEMPLATE = " +
                        templateArrayList.get(x).getTemplateId());*/
                resultSet.next();
                Message message = new Message();
                message.setMessageId(resultSet.getInt("mes_id"));
                message.setMessage(resultSet.getString("mes_text"));
                message.setParameters(ParameterHandler.getParametersByMessage(message.getMessageId()));
                templateArrayList.get(x).setMessage(message);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Sql.bdClose(sql.getConn());
            return templateArrayList;
        }
    }

    /**
     * this method returns the message that is associated
     * with the specified template.
     * @param templateId template id
     * @return message template message specified
     * @throws MessageDoesntExistsException the specified template has no associated message
     * @throws ParameterDoesntExistsException the specified message has no associated parameters
     */
    public static Message getMessage(int templateId)
            throws MessageDoesntExistsException, ParameterDoesntExistsException{
        //String query = "select mes_id,mes_text from message where mes_template =" + templateId;
        Message message = new Message();
        try {
            Connection connection = Sql.getConInstance();
            PreparedStatement preparedStatement = connection.prepareCall("{call m07_select_message(?)}");
            preparedStatement.setInt(1, templateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                message.setMessageId(resultSet.getInt("mes_id"));
                message.setMessage(resultSet.getString("mes_text"));
                message.setParameters(ParameterHandler.getParametersByMessage(message.getMessageId()));
            }
        }catch (ParameterDoesntExistsException e) {
            throw new ParameterDoesntExistsException
                    ("No hay parametros para template con id:"
                            + templateId,e, templateId);
        }catch (SQLException e) {
            throw new MessageDoesntExistsException
                ("Error: No existe mensaje para esta plantilla con id:"
                        + templateId, e, templateId);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (sql.getConn() != null) {
                Sql.bdClose(sql.getConn());
            }
            return message;
        }
    }

    /**
     * this method is responsible for storing the message of a template
     * and all its attributes.
     * @param message string message with the parameters included
     * @param templateId template id
     * @param parameters list of associated parameters
     * @param companyId company id
     */
    public static void postMessage(String message, int templateId, String[] parameters,int companyId) {
        /*String query = "INSERT INTO public.Message(mes_text,mes_template)" +
                "VALUES ('" + message + "'," + templateId + ") returning mes_id";*/
        int messageId=0;
        try{
            Connection connection = Sql.getConInstance();
            PreparedStatement preparedStatement = connection.prepareCall("{call m07_post_message(?,?)}");
            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, templateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                messageId = resultSet.getInt("mes_id");
                postParameterOfMessage(messageId,parameters,companyId);
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            Sql.bdClose(sql.getConn());
        }
    }

    /**
     * this method is responsible for storing the parameters associated
     * with a specific message.
     * @param messageId message id
     * @param parameters list of associated parameters
     * @param companyId company id
     */
    private static void postParameterOfMessage(int messageId, String[] parameters, int companyId) {
        //String query = "";
        try{
            Connection connection = Sql.getConInstance();
            PreparedStatement preparedStatement = connection.prepareCall("{call m07_post_parameter_of_message(?,?,?}");
            for (int i = 0; i < parameters.length; i++) {
               /* query = query + "insert into public.message_parameter(mp_message,mp_parameter)\n" +
                        "values (" + messageId + ",(select par_id \n" +
                        "from public.parameter\n" +
                        "where par_company_id = " + companyId + "  and par_name = '" + parameters[i] +"'));";
            sql.sqlNoReturn(query);*/
                preparedStatement.setInt(1, messageId);
                preparedStatement.setInt(2, companyId);
                preparedStatement.setString(3, parameters[i]);
                ResultSet resultSet = preparedStatement.executeQuery();
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            Sql.bdClose(sql.getConn());
        }
    }

    /**
     * this method is responsible for modify the message of a template
     * and all its attributes.
     * @param message string message with the parameters included
     * @param templateId template id
     * @param parameters list of associated parameters
     * @param companyId company id
     */
    public static void updateMessage(String message, int templateId, String[] parameters,int companyId) {
        /*String query = "UPDATE public.message\n" +
                        "SET mes_text = '" + message + "'\n" +
                        "WHERE mes_template = " + templateId + "\n" +
                        "returning mes_id";
        sql = new Sql();*/
        ResultSet resultSet;
        int messageId;
        try{
            Connection connection = Sql.getConInstance();
            PreparedStatement preparedStatement = connection.prepareCall("{call m07_update_message(?,?}");
            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, templateId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                messageId = resultSet.getInt("mes_id");
                updateParameterOfMessage(messageId,parameters,companyId);
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            Sql.bdClose(sql.getConn());
        }
    }

    /**
     * his method is responsible for modifying the parameters associated
     * with a specific message.
     * @param messageId message id
     * @param parameters list of associated parameters
     * @param companyId company id
     */
    private static void updateParameterOfMessage(int messageId, String[] parameters, int companyId) {
        /*String query = "delete from public.message_parameter\n" +
                "WHERE mp_message = " + messageId;
        Sql sql = new Sql();*/
        try{
            /*sql.sqlNoReturn(query);*/
            Connection connection = Sql.getConInstance();
            PreparedStatement preparedStatement = connection.prepareCall("{call m07_delete_parameter_of_message(?}");
            preparedStatement.setInt(1, messageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            postParameterOfMessage(messageId,parameters,companyId);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Sql.bdClose(sql.getConn());
        }
    }
}
