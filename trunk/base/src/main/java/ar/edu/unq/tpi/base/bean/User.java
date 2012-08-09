package ar.edu.unq.tpi.base.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.edu.unq.tpi.base.generator.annotations.DataGenerator;
import ar.edu.unq.tpi.base.initialData.DataGeneratorUser;
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;
import ar.edu.unq.tpi.util.common.HashUtils;

@Entity
@Table(name = "usuario")
@DataGenerator(DataGeneratorUser.class)
public class User extends IdentificablePersistentObject implements Serializable {
    private static final long serialVersionUID = 5332067114052738292L;

    @Basic
    private String pass;
    
    @Basic
    private boolean conect;

    public User() {
    }

    public User(final String nombre, final String pass) {
        this.setKey(nombre);
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(final String pass) {
        this.pass = pass;
    }

    public void setConect(final boolean conectado) {
        this.conect = conectado;
    }

    public boolean isConect() {
        return conect;
    }

    public boolean checkPassword(final String pass) {
        return this.pass.equals(HashUtils.hash(pass));
    }

    @Override
    public String[] atributos() {
        return null;
    }

    @Override
    public String getIdentificableName() {
        return "Usuario";
    }

}
