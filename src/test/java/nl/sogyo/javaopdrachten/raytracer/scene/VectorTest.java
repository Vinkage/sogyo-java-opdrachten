package javalanguage.nl.sogyo.javaopdrachten.raytracer.scene;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@IndicativeSentencesGeneration(separator = "-> ", generator = DisplayNameGenerator.ReplaceUnderscores.class)
class VectorTest {

    private static final double EPSILON = 1e-7;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Nested
    class vector_rotation_in_place {
        @Test
        void rotation_z_theta_0_phi_pi() {
            Vector z = new Vector(0,0,-1);
            float PI = (float) Math.PI;
            z.rotationInPlace(0f, PI / 2, new Vector(1,0,0));
            assertTrue(z.crossProduct(new Vector(0,1,0)).getModulus() < EPSILON);
        }
    }


    @Test
    void rotationAndReturn() {
    }
}