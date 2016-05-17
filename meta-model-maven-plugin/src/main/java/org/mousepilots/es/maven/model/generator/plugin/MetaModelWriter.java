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
import org.mousepilots.es.core.util.Function;
import org.mousepilots.es.core.util.StringUtils;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.HasValueDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.ManagedTypeDescriptor;
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

    public MetaModelWriter(File generatedSourceDir, Log log) {
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
        context.put("currentDate", MetaModelGeneratorMojo.CURRENT_DATE);
        return context;
    }

    /**
     * Writes all {@link AbstractType} implementations
     *
     * @throws MojoExecutionException if something goes wrong when writing the
     * generated meta models to disk.
     */
    public void writeAbstractTypeImpls() throws MojoExecutionException {
        final Template template = velocityEngine.getTemplate("templates/TypeESImpl.vsl");
        for (TypeDescriptor td : TypeDescriptor.getAll()) {
            final VelocityContext context = createContext();
            context.put("td", td);
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
    public void writeHasValueImpls() throws MojoExecutionException {
        Template template = velocityEngine.getTemplate("templates/HasValueImpl.vsl");
        for (TypeDescriptor td : TypeDescriptor.getAll()){
            if(td.requiredHasValueClassGeneration()){
                final VelocityContext context = createContext();
                final HasValueDescriptor hasValueDescriptor = td.getHasValueDescriptor();
                context.put("hvd", hasValueDescriptor);
                StringWriter writer = new StringWriter();
                template.merge(context, writer);
                classToDisk(writer, hasValueDescriptor.getHasValueImplClassCanonicalName());
            }
        }
    }
    
    public void writeProxyImpls() throws MojoExecutionException{
        Template template = velocityEngine.getTemplate("templates/proxy/ProxyImpl.vsl");
        for (TypeDescriptor td : TypeDescriptor.getAll()){
            if(td instanceof ManagedTypeDescriptor && td.isInstantiable()){
                ManagedTypeDescriptor mtd = (ManagedTypeDescriptor) td;
                final VelocityContext context = createContext();
                context.put("td", mtd);
                StringWriter writer = new StringWriter();
                template.merge(context, writer);
                classToDisk(writer, mtd.getProxyClassCanonicalName());
            }
        }
    }

    public void writeMetaModel() throws MojoExecutionException {
        Template template = velocityEngine.getTemplate("templates/AbstractMetaModelImpl.vsl");
        final VelocityContext context = createContext();
        context.put("package", "org.mousepilots.es.test.domain");
        Function<TypeDescriptor,String> transformer = td -> "(TypeESImpl) " + td.getDescriptorClassFullName() + "." + td.getDeclaredVariableName();
        context.put("types",StringUtils.join(TypeDescriptor.getAll(), transformer, ",\n\t\t\t\t"));
        context.put("attributeDescriptors",AttributeDescriptor.getAll());
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        classToDisk(writer, "org.mousepilots.es.test.domain.MetamodelImpl");
        
    }
}
