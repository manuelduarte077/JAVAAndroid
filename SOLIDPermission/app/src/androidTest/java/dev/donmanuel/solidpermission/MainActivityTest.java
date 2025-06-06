package dev.donmanuel.solidpermission;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Pruebas instrumentadas para la actividad principal y la integración del sistema de permisos.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule cameraPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.CAMERA);

    @Test
    public void testCameraPermissionButton_clickShowsPermissionGranted() {
        // Hacer clic en el botón de permiso de cámara
        onView(withId(R.id.cameraPermissionButton)).perform(click());

        // Verificar que el texto de estado muestra que el permiso está concedido
        onView(withId(R.id.statusTextView)).check(matches(withText(containsString("concedido"))));
    }

    @Test
    public void testInitialState_showsWaitingForPermission() {
        // Verificar el estado inicial de la aplicación
        onView(withId(R.id.statusTextView)).check(matches(withText("Estado: Esperando solicitud de permiso")));
    }

    @Test
    public void testAllButtonsAreClickable() {
        // Verificar que todos los botones son clicables
        onView(withId(R.id.storagePermissionButton)).perform(click());
        onView(withId(R.id.locationPermissionButton)).perform(click());
        onView(withId(R.id.cameraPermissionButton)).perform(click());
        onView(withId(R.id.microphonePermissionButton)).perform(click());
    }
}
