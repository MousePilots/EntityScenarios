package org.mousepilots.es.maven.model.generator.plugin;

import java.io.File;
import java.sql.Blob;
import java.util.List;
import java.util.Properties;
import javax.persistence.metamodel.Type;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.app.VelocityEngine;
import org.mousepilots.es.maven.model.generator.model.Descriptor;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.TypeES;
import org.reflections.Reflections;


@Mojo(name = "generate", requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, defaultPhase = LifecyclePhase.GENERATE_SOURCES, executionStrategy = "once-per-session"

)
public class MetaModelGeneratorMojo extends AbstractMojo
{
  /**
   * used to prevent multiple executions within the same maven session
   */
  private static boolean executed = false;

   /**
    * Enumerates the sub-packages of {@link #packageName}
    */
   private enum SubPackage
   {

      /**
       * The subpackage in which the {@link MetaModel} implementation is written
       */
      META_MODEL(""),
      /**
       * The subpackage in which {@link TypeES} implementations are written
       */
      TYPE("type"),

      /**
       * The subpackage in which {link DTO_Facade} implementations are written
       */
      DTO_FACADE("dto.facade"),
      /**
       * The subpackage in which {@link AttributeES} implementations are written
       */
      ATTRIBUTE("attribute");

      private SubPackage(String subPackage)
      {
         this.name = subPackage;
      }

      private final String name;

      /**
       * @return the name of the subpackage, relative to {@link MetaModelGeneratorMojo#packageName}
       */
      public String getSubPackageName()
      {
         return name;
      }
   }

   /**
    * @param subPackage
    * @return the fully qualified package name of the {@code subPackage}, resolved against {@link #packageName}
    */
   private String getPackageName(SubPackage subPackage)
   {
      return subPackage.getSubPackageName().isEmpty() ? this.packageName : this.packageName + "." + subPackage.getSubPackageName();
   }

   /**
    * The fully qualified name of the package in which sources are generated by the plugin. E.g. {@code org.my.package}. The plugin must have this and all of its subpackes to itself
    */
   @Parameter(required = true)
   private String packageName;

   @Parameter(defaultValue = "${project}", readonly = true)
   private MavenProject project;

   /**
    * The fully qualified names of the managed classes you whish to include. Put {@code null} for all.
    */
   @Parameter
   private List<String> includedTypeJavaClasses;

   /**
    * The fully qualified names of attribute classes for which you do not want to generate an attribute. These could e.g. be {@link Blob}s.
    */
   @Parameter
   private List<String> excludedAttributeJavaClasses;

   /**
    * The project folder in which generated sources are put. The plugin should have this and all subfolders to itself.
    */
   @Parameter(defaultValue = "${project.build.directory}/generated-sources/mmx")
   private File generatedSourceDir;

   /**
    * Contains an in-memory representation of the reflections xml report of the domain project
    */
   private Reflections reflections;

   /**
    * The velocity engine for generating source files
    */
   private final VelocityEngine velocityEngine;

   public MetaModelGeneratorMojo()
   {
      super();
      velocityEngine = new VelocityEngine();
      Properties props = new Properties();
      props.put("resource.loader", "class");
      props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
      props.put("directive.set.null.allowed", Boolean.TRUE.toString());
      velocityEngine.init(props);
   }

   @Override
   public void execute() throws MojoExecutionException, MojoFailureException
   {
       System.out.println("Hello World from Plugin!");
       Descriptor des = new TypeDescriptor(Type.PersistenceType.ENTITY, "Test", null, 0);
       System.out.println(des.getEsNameAndVersion());
   }
}