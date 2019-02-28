package controller;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MapControllerTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generateMap() throws Exception {
    }

    @Test
    public void createAndSaveUserMap() throws Exception {
    }

    @Test
    public void checkMapFileExists() throws Exception {
    }

    @Test
    public void editMap() throws Exception {
    }

    @Test
    public void getContinentList() throws Exception {
    }

    @Test
    public void listofMapsinDirectory() throws Exception {
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(MapController.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
