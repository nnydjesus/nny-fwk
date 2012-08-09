package ar.edu.unq.tpi.base.persistence.transaction;
import ar.edu.unq.tpi.base.persistence.PersistenceManager;
import ar.edu.unq.tpi.base.persistence.query.Reporter;
import ar.edu.unq.tpi.base.persistence.query.TransactionManager;
import ar.edu.unq.tpi.base.unitofwork.UnitOfWork;
import ar.edu.unq.tpi.util.common.ReflectionUtils;
import ar.edu.unq.tpi.util.commons.exeption.UserException;

public class UseCaseManager {
        
        public static void execute(Object object, String metodo, Object...objects ){
                execute(TransactionManager.STANDARD, object, metodo, objects);          
        }
        
        public static void executeTest(Object object, String metodo, Object...objects ){
                execute(TransactionManager.ROLLBACK, object, metodo, objects);          
                
        }
        public static void executeDummy(Object object, String metodo, Object...objects ){
                execute(TransactionManager.DUMMY, object, metodo, objects);             
                
        }
        public static void execute(UseCase useCase ){
                execute(TransactionManager.STANDARD, useCase);          
        }
        
        public static void executeTest(UseCase useCase){
                execute(TransactionManager.ROLLBACK, useCase);          
                
        }
        public static void executeDummy(UseCase useCase){
                execute(TransactionManager.DUMMY, useCase);             
                
        }
        
        
        public static void execute(TransactionManager transactionManager, final Object object, final String metodo, final Object...objects){
                execute(transactionManager, new UseCase() {
                        @Override
                        public void run() {
                                ReflectionUtils.invokeMethod(object, metodo,objects);
                        }

                        @Override
                        public String getName() {
                                return metodo;
                        }
                });
        }
        public static void execute(final TransactionManager transactionManager, final UseCase useCase){
        	new Thread(new Runnable(){
        		@Override
        		public void run() {
        			Reporter.startTime();
        			UnitOfWork unitOfWork = PersistenceManager.getInstance().initUnitOfWork(transactionManager);
        			try {
        					unitOfWork.begin();
							useCase.run();
							unitOfWork.commit();
                	}catch (Exception e) {
                		unitOfWork.rollback();
                		throw new UserException(e);
                	}finally{
                		unitOfWork.close();
                		Reporter.finishTime(useCase.getName());
                		Reporter.show();
                	}
				}
                        	
            }).run();
        }
        

}
