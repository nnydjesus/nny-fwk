package ar.com.nny.base.configuration.jfig;


public class SecurityBlock extends AbstractBlockDecorator {

    public SecurityBlock(final CodeBlock block) {
        super(block);
    }

    public Object execute() throws Exception {
        final RestrictedAccess access = this.getMetadata(RestrictedAccess.class);

        if ( access != null ) {
//            ContextSessionFacade.getLoggedUserRoles().ensurePermissionsToService(access.roles());
        }
        
        return block.execute();
    }
}
