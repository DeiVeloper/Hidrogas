package mx.com.desoft.hidrogas.to;

/**
 * Created by erick.martinez on 22/12/2016.
 */

public class PersonalTO {

    private String nomina;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String password;
    private Integer noPipa;
    private Integer fechaRegistro;
    private String nominaRegistro;
    private Integer tipoEmpleado;

    public PersonalTO() {

    }

    public PersonalTO(String nomina, String nombre, String apellidoPaterno, String apellidoMaterno, String password, Integer noPipa, Integer fechaRegistro,
                      String nominaRegistro, Integer tipoEmpleado) {
        this.nombre = nombre;
        this.nomina = nomina;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.password = password;
        this.noPipa = noPipa;
        this.fechaRegistro = fechaRegistro;
        this.nominaRegistro = nominaRegistro;
        this.tipoEmpleado = tipoEmpleado;

    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNomina(){
        return nomina;
    }

    public void setNomina(String nomina){
        this.nomina = nomina;
    }

    public String getApellidoPaterno(){
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno){
        this.apellidoPaterno= apellidoPaterno;
    }

    public String getApellidoMaterno(){
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno){
        this.apellidoMaterno= apellidoMaterno;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Integer getNoPipa(){
        return noPipa;
    }

    public void setNoPipa(Integer noPipa){
        this.noPipa = noPipa;
    }

    public Integer getFechaRegistro(){
        return fechaRegistro;
    }

    public void setFechaRegistro(Integer fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    public String getNominaRegistro(){
        return nominaRegistro;
    }

    public void setNominaRegistro(String nominaRegistro){
        this.nominaRegistro = nominaRegistro;
    }

    public Integer getTipoEmpleado(){
        return tipoEmpleado;
    }

    public void setTipoEmpleado(Integer tipoEmpleado){
        this.tipoEmpleado = tipoEmpleado;
    }
}
