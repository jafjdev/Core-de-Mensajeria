package Classes.M06_DataOrigin;

import Classes.M02_Company.Company;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.util.ArrayList;

@WebListener
public class PathHandler implements ServletContextListener {

    private static String ORIGIN_HOME = System.getProperty("user.home") + "/Origin";
    private static String ORIGIN_HOME_INBOX = ORIGIN_HOME + "/Inbox";
    private static String ORIGIN_HOME_TRASH = ORIGIN_HOME + "/Trash";
    /*public void FileChooser(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        try {
            fileChooser.setCurrentDirectory(new File(/*System.getProperty("user.home")"C:/"));
            fileChooser.setDialogTitle("Seleccione un directorio.");
            int result = fileChooser.showOpenDialog(new JPanel());
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected path: " + selectedFile.getAbsolutePath());
            }
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.createDirectory(ORIGIN_HOME);
        this.createDirectory(ORIGIN_HOME_INBOX);
        this.createDirectory(ORIGIN_HOME_TRASH);
        System.out.println("--------------->PATHS CREATED");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void createDirectory(String path){
        File pathFile = new File(path);
        if (!pathFile.exists()){
            pathFile.mkdir();
        }
    }

    private void createOriginDirectories(String rootPath, ArrayList<Company> companies){
        for (Company company : companies) {
            //------------------->REPLECE THIS FOR COMPANY PATH
            this.createDirectory(rootPath + "/" + company.get_name());
        }
    }

    private String generatePath(Company company){
        return company.get_name().replaceAll("\\s","") + "_" + Encrypter.getCurrentTime();
    }

    public static String getInboxPath(){
        return ORIGIN_HOME_INBOX;
    }

    public static String getTrashPath(){
        return ORIGIN_HOME_TRASH;
    }
}