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
import org.mousepilots.es.maven.model.generator.model.type.EntityTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.MappedSuperClassDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Class that handles the actual writing of the meta model classes.
 * @author Nicky Ernste
 * @version 1.0, 8-12-2015
 */
public class MetaModelWriter {
    
    private final File generatedSourceDir;
    private final Log log;
    private final String packageName;
    
    /**
     * The velocity engine for generating source files
     */
    private final VelocityEngine velocityEngine;

    public MetaModelWriter(File generatedSourceDir, Log log, String packageName) {
        this.generatedSourceDir = generatedSourceDir;
        this.log = log;
        this.packageName = packageName;
        
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
    * @throws MojoExecutionException is writing fails
    */
   private void classToDisk(StringWriter writer, String className) throws MojoExecutionException
   {
      final String path = toPath(className);
      final File classFile = new File(path);
      if (!classFile.getParentFile().exists())
      {
         classFile.getParentFile().mkdirs();
      }
      if (classFile.exists())
      {
         classFile.delete();
      }
      //write file
      try (final PrintWriter printWriter = new PrintWriter(classFile))
      {
         printWriter.append(writer.toString());
         printWriter.flush();
         log.debug("written " + className);
      }
      catch (IOException ex)
      {
         throw new MojoExecutionException("error writing class " + className + " to " + path, ex);
      }
   }

   /**
    * @param className
    * @return the absolute path to the file on disk corresponding to {@code className}
    */
   private String toPath(String className)
   {
      return new StringBuilder(this.generatedSourceDir.getAbsolutePath()).append(File.separatorChar).append(className.replace('.', File.separatorChar)).append(".java").toString();
   }
   
   /**
    * Creates a velocity context initialised for the specified {@link SubPackage}
    *
    * @param subPackage
    * @return
    */
   private VelocityContext createContext(MetaModelGeneratorMojo.SubPackage subPackage)
   {
      final VelocityContext context = new VelocityContext();
      context.put("package", packageName);
      context.put("esNameAndVersion", MetaModelGeneratorMojo.ES_NAME_AND_VERSION);
      context.put("currentDate", MetaModelGeneratorMojo.currentDate);
      return context;
   }

   /**
    * Writes all {@link AbstractType} implementations
    * @throws MojoExecutionException If something goes wrong when writing the generated meta models to disk.
    */
   public void writeAbstractTypeImpls() throws MojoExecutionException
   {      
      for (TypeDescriptor td : TypeDescriptor.getAll())
      {
         final VelocityContext context = createContext(MetaModelGeneratorMojo.SubPackage.TYPE);
         Template template = null;
         switch (td.getPersistenceType()){             
             case MAPPED_SUPERCLASS:
                 context.put("td", (MappedSuperClassDescriptor)td);
                 template = velocityEngine.getTemplate("templates/MappedSuperClassImpl.vsl");
                 break;
             case ENTITY:
                 context.put("td", (EntityTypeDescriptor)td);
                 template = velocityEngine.getTemplate("templates/EntityImpl.vsl");
                 break;
         }
          if (template != null) {
              if (td.getSuper() != null) {
                  //Class has a parent.
                  context.put("extendsClass", td.getSuper());
              }
              StringWriter writer = new StringWriter();
              template.merge(context, writer);
              classToDisk(writer, td.getDescriptorClassFullName());
          } else {
              log.warn("Could not write the class because there was no template: " + td.getName());
          }
      }
   }
}