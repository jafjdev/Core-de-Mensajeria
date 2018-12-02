package Classes.M04_Integrator;


/**
 * Clase abstracta Integrador que implementa la interfaz IIntegrador
 * que nos proporciona los metodos para trabajar con los integradores en concreto.
 *
 * @Author Josè Salas
 * @Author Manuel Espinoza
 * @Author Josè Cedeño
 * @see IIntegrator
 */

public abstract class Integrator implements IIntegrator {
    private int idIntegrator;
    private int threadCapacity;
    private float messageCost;
    private String nameIntegrator;
    private String apiIntegrator;
    private boolean enabled;


    public Integrator(int idIntegrator, int threadCapacity, float messageCost, String nameIntegrator, String apiIntegrator, boolean enabled) {
        this.idIntegrator = idIntegrator;
        this.threadCapacity = threadCapacity;
        this.messageCost = messageCost;
        this.nameIntegrator = nameIntegrator;
        this.apiIntegrator = apiIntegrator;
        this.enabled = enabled;
    }

    public int getIdIntegrator() {
        return idIntegrator;
    }

    public void setIdIntegrator(int idIntegrator) {
        this.idIntegrator = idIntegrator;
    }

    public int getThreadCapacity() {
        return threadCapacity;
    }

    public void setThreadCapacity(int threadCapacity) {
        this.threadCapacity = threadCapacity;
    }

    public float getMessageCost() {
        return messageCost;
    }

    public void setMessageCost(float messageCost) {
        this.messageCost = messageCost;
    }

    public String getNameIntegrator() {
        return nameIntegrator;
    }

    public void setNameIntegrator(String nameIntegrator) {
        this.nameIntegrator = nameIntegrator;
    }

    public String getApiIntegrator() {
        return apiIntegrator;
    }

    public void setApiIntegrator(String apiIntegrator) {
        this.apiIntegrator = apiIntegrator;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Integrator{" +
                "idIntegrator=" + idIntegrator +
                ", threadCapacity=" + threadCapacity +
                ", messageCost=" + messageCost +
                ", nameIntegrator='" + nameIntegrator + '\'' +
                ", apiIntegrator='" + apiIntegrator + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}