package ar.edu.unq.tpi.base.initialData;

import ar.edu.unq.tpi.base.bean.User;
import ar.edu.unq.tpi.base.generator.InitialDataGenerator;
import ar.edu.unq.tpi.base.generator.annotations.DataGeneratorMethod;
import ar.edu.unq.tpi.base.repository.GenericRepository;
import ar.edu.unq.tpi.util.common.HashUtils;

public class DataGeneratorUser   extends InitialDataGenerator<User> {
	private GenericRepository<User> repository = new GenericRepository<User>(User.class);
	
	@DataGeneratorMethod
	public void generateEmpleados(){
//		repository.save(new User("a", HashUtils.hash("a")));
//		repository.save(new User("ranelagh", HashUtils.hash("ranelagh")));
		
	}

}
