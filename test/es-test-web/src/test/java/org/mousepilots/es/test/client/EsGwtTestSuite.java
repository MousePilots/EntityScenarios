/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import org.mousepilots.es.test.shared.TestMetaModel;
import org.mousepilots.es.test.shared.CommandTest;
import org.mousepilots.es.test.shared.TypeTest;

/**
 *
 * @author AP34WV
 */
public class EsGwtTestSuite extends TestCase{

    public EsGwtTestSuite(){
        super();
    }

    public EsGwtTestSuite(String name) {
        super(name);
    }
    
    public static Test suite()
      {
          GWTTestSuite suite = new GWTTestSuite("GWT test suite");
          suite.addTestSuite(TestMetaModel.class);
          suite.addTestSuite(CommandTest.class);
          suite.addTestSuite(TypeTest.class);
          return suite;
      }
}
