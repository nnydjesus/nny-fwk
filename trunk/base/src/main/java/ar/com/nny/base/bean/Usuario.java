package ar.com.nny.base.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.com.nny.base.generator.annotations.DataGenerator;
import ar.com.nny.base.initialData.DataGeneratorUser;
import ar.com.nny.base.utils.HashUtils;
import ar.com.nny.base.utils.IdentificablePersistentObject;

@Entity
@Table(name = "usuario")
@DataGenerator(DataGeneratorUser.class)
public class Usuario extends IdentificablePersistentObject implements Serializable {
    private static final long serialVersionUID = 5332067114052738292L;

    @Basic
    private String pass;

    @Basic
    private boolean conectado;

    public Usuario() {
    }

    public Usuario(final String nombre, final String pass) {
        this.setId(nombre);
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(final String pass) {
        this.pass = pass;
    }

    public void setConectado(final boolean conectado) {
        this.conectado = conectado;
    }

    public boolean isConectado() {
        return conectado;
    }

    public boolean checkPassword(final String pass) {
        return this.pass.equals(HashUtils.hash(pass));
    }

    @Override
    public String[] atributos() {
        return null;
    }

    @Override
    public String getName() {
        return "Usuario";
    }
}
