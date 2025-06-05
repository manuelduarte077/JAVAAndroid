package dev.donmanuel.solidandroid.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase EmailValidator
 */
public class EmailValidatorTest {

    private EmailValidator emailValidator;

    @Before
    public void setup() {
        emailValidator = new EmailValidator();
    }

    @Test
    public void isValid_validEmail_returnsTrue() {
        // Arrange
        String validEmail = "test@example.com";

        // Act
        boolean result = emailValidator.isValid(validEmail);

        // Assert
        assertTrue("Un email válido debería devolver true", result);
    }

    @Test
    public void isValid_invalidEmailNoAt_returnsFalse() {
        // Arrange
        String invalidEmail = "testexample.com";

        // Act
        boolean result = emailValidator.isValid(invalidEmail);

        // Assert
        assertFalse("Un email sin @ debería devolver false", result);
    }

    @Test
    public void isValid_invalidEmailNoDomain_returnsFalse() {
        // Arrange
        String invalidEmail = "test@";

        // Act
        boolean result = emailValidator.isValid(invalidEmail);

        // Assert
        assertFalse("Un email sin dominio debería devolver false", result);
    }

    @Test
    public void isValid_emptyEmail_returnsFalse() {
        // Arrange
        String emptyEmail = "";

        // Act
        boolean result = emailValidator.isValid(emptyEmail);

        // Assert
        assertFalse("Un email vacío debería devolver false", result);
    }

    @Test
    public void isValid_nullEmail_returnsFalse() {
        // Act
        boolean result = emailValidator.isValid(null);

        // Assert
        assertFalse("Un email null debería devolver false", result);
    }
}
