package ar.com.nny.base.configuration.jfig;

import java.lang.annotation.Annotation;

public abstract class AbstractBlockDecorator implements CodeBlock {

    protected CodeBlock block;


    public AbstractBlockDecorator(final CodeBlock block) {
        this.block = block;
    }


    public <T extends Annotation> T getMetadata(final Class<T> annotationClass) {
        return this.block.getMetadata(annotationClass);
    }

    public <T extends Annotation> T getClassMetadata(final Class<T> annotationClass) {
        return this.block.getClassMetadata(annotationClass);
    }
    

    @Override
	public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName());
        builder.append("[");
        builder.append(block.toString());
        builder.append("]");
        return builder.toString();
    }

}
