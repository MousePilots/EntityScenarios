package org.mousepilots.es.maven.model.generator.plugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.mousepilots.es.maven.model.generator.model.type.BasicTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.EmbeddableTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.EntityTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.MappedSuperClassDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Class that handles the actual writing of the meta model classes.
 *
 * @author Nicky Ernste
 * @version 1.0, 8-12-2015
 */
public class MetaModelWriter {

    private final File generatedSourceDir;
    private final Log log;

    /**
     * The velocity engine for generating source files
     */
    private final VelocityEngine velocityEngine;

    public MetaModelWriter(File generatedSourceDir, Log log, String packageName) {
        this.generatedSourceDir = generatedSourceDir;
        this.log = log;

        //Initialise the velocity engine.
        velocityEngine = new VelocityEngine();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        props.put("directive.set.null.allowed", Boolean.TRUE.toString());
        velocityEngine.init(props);
    }

    /**
     * Writes a source file file for a class to disk
     *
     * @param writer contains the source code
     * @param className the FQN of the class contained in the {@code writer}
     * @throws MojoExecutionException if writing fails
     */
    private void classToDisk(StringWriter writer, String className) throws MojoExecutionException {
        final String path = toPath(className);
        final File classFile = new File(path);
        if (!classFile.getParentFile().exists()) {
            classFile.getParentFile().mkdirs();
        }
        if (classFile.exists()) {
            classFile.delete();
        }
        //write file
        try (final PrintWriter printWriter = new PrintWriter(classFile)) {
            printWriter.append(writer.toString());
            printWriter.flush();
            log.debug("written " + className);
        } catch (IOException ex) {
            throw new MojoExecutionException("error writing class " + className + " to " + path, ex);
        }
    }

    /**
     * Get the absolute path to the file with the given {@code className}.
     * @param className the name of the class.
     * @return the absolute path to the file on disk corresponding to
     * {@code className}
     */
    private String toPath(String className) {
        return new StringBuilder(this.generatedSourceDir.getAbsolutePath()).append(File.separatorChar).append(className.replace('.', File.separatorChar)).append(".java").toString();
    }

    /**
     * Creates a velocity context initialised with the name and version of this
     * generator and the current date and time.
     *
     * @return A {@link VelocityContext} initialised with some values.
     */
    private VelocityContext createContext() {
        final VelocityContext context = new VelocityContext();
        context.put("esNameAndVersion", MetaModelGeneratorMojo.ES_NAME_AND_VERSION);
        context.put("currentDate", MetaModelGeneratorMojo.currentDate);
        return context;
    }

    /**
     * Writes all {@link AbstractType} implementations
     *
     * @throws MojoExecutionException if something goes wrong when writing the
     * generated meta models to disk.
     */
    public void writeAbstractTypeImpls() throws MojoExecutionException {
        for (TypeDescriptor td : TypeDescriptor.getAll()) {
            final VelocityContext context = createContext();
            Template template;
            if (td.getSuper() != null) {
                context.put("superType", td.getSuper().getOrdinal());
            } else {
                context.put("superType", -1);
            }
            context.put("subTypes", DescriptorUtils.printSubTypes(td));
            switch (td.getPersistenceType()) {
                case BASIC:
                default:
                    context.put("td", (BasicTypeDescriptor) td);
                    template = velocityEngine.getTemplate("templates/BasicTypeImpl.vsl");
                    break;
                case EMBEDDABLE:
                    EmbeddableTypeDescriptor etd = (EmbeddableTypeDescriptor) td;
                    context.put("td", etd);
                    context.put("attributes", DescriptorUtils.printAttributesList(etd.getAttributes()));
                    template = velocityEngine.getTemplate("templates/EmbeddableImpl.vsl");
                    break;
                case MAPPED_SUPERCLASS:
                    MappedSuperClassDescriptor mscd = (MappedSuperClassDescriptor) td;
                    context.put("td", mscd);
                    context.put("attributes", DescriptorUtils.printAttributesList(mscd.getAttributes()));
                    context.put("idClassAttributes", DescriptorUtils.printIdClassAttributes(mscd.getIdClassAttribute()));
                    context.put("idType", DescriptorUtils.printType(mscd.getIdType()));
                    template = velocityEngine.getTemplate("templates/MappedSuperClassImpl.vsl");
                    break;
                case ENTITY:
                    EntityTypeDescriptor enTd = (EntityTypeDescriptor) td;
                    context.put("td", enTd);
                    context.put("attributes", DescriptorUtils.printAttributesList(enTd.getAttributes()));
                    context.put("idClassAttributes", DescriptorUtils.printIdClassAttributes(enTd.getIdClassAttribute()));
                    context.put("idType", DescriptorUtils.printType(enTd.getIdType()));
                    template = velocityEngine.getTemplate("templates/EntityImpl.vsl");
                    break;
            }
            if (td.getSuper() != null) {
                //Class has a parent.
                context.put("extendsClass", td.getSuper());
            }
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            classToDisk(writer, td.getDescriptorClassFullName());
        }
    }

    /**
     * Writes all value wrapper implementations for the basic types.
     * @throws MojoExecutionException if something goes wrong when writing the
     * generated meta models to disk.
     */
    public void writeHasValuesTypes() throws MojoExecutionException {
        for (TypeDescriptor td : TypeDescriptor.getAll()){
            final VelocityContext context = createContext();
            Template template = null;
            switch (td.getPersistenceType()){
                case BASIC:
                    context.put("td", (BasicTypeDescriptor)td);
                    template = velocityEngine.getTemplate("templates/ValueWrapperImpl.vsl");
                    break;
            }
            if (template != null) {
                StringWriter writer = new StringWriter();
                template.merge(context, writer);
                classToDisk(writer, "wrappers." + td.getJavaTypeSimpleName() + "Wrapper");
            }
        }
    }

    public void writeMetaModel() throws MojoExecutionException {
        //TODO write the meta model implementation class.
    }
}
