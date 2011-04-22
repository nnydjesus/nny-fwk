package ar.com.nny.base.initialData;

import ar.com.nny.base.bean.Usuario;
import ar.com.nny.base.dao.GenericDao;
import ar.com.nny.base.generator.InitialDataGenerator;
import ar.com.nny.base.generator.annotations.DataGeneratorMethod;
import ar.com.nny.base.utils.HashUtils;

public class DataGeneratorUser   extends InitialDataGenerator<Usuario> {
	private GenericDao<Usuario> dao = new GenericDao<Usuario>(Usuario.class, "Usuario");
	
	@DataGeneratorMethod
	public void generateEmpleados(){
		dao.save(new Usuario("a", HashUtils.hash("a")));
		
	}

}
