package com.anavasynth.lab5http;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

public class ArchitectureTest {

    private JavaClasses applicationClasses;

    @BeforeEach
    void setUp() {
        applicationClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.anavasynth.lab5http");
    }

    @Test
    void controllerClassesShouldBeAnnotatedWithRestController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(RestController.class)
                .because("controllers should use @RestController annotation")
                .check(applicationClasses);
    }

    @Test
    void modelFieldsShouldNotBePublic() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .should().notBePublic()
                .because("model fields should be encapsulated")
                .check(applicationClasses);
    }

    @Test
    void controllersShouldNotBeAutowiredDirectly() {
        noFields()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().beAnnotatedWith(Autowired.class)
                .because("fields in controller should not be field-injected")
                .check(applicationClasses);
    }

    // Можна додати ще інші архітектурні правила
}
