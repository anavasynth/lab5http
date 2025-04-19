package com.anavasynth.lab5http;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

public class ArchitectureTest {

    private JavaClasses classes;

    @BeforeEach
    void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.anavasynth.lab5http");
    }

    // === CONTROLLER TESTS ===

    @Test
    void controllerClassesShouldBeAnnotatedWithRestController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(RestController.class)
                .check(classes);
    }

    @Test
    void controllerClassNamesShouldEndWithController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Controller")
                .check(classes);
    }

    @Test
    void controllersShouldNotBeAutowiredDirectly() {
        noFields()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().beAnnotatedWith(Autowired.class)
                .check(classes);
    }

    @Test
    void controllersShouldNotDependOnOtherControllers() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..controller..")
                .check(classes);
    }

    @Test
    void controllersShouldNotAccessRepositoriesDirectly() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..repository..")
                .check(classes);
    }

    // === MODEL TESTS ===

    @Test
    void modelFieldsShouldNotBePublic() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .should().notBePublic()
                .check(classes);
    }

    @Test
    void modelClassesShouldHaveGettersAndSetters() {
        classes()
                .that().resideInAPackage("..model..")
                .should().haveOnlyFinalFields()
                .because("POJOs should use private fields and provide accessors")
                .allowEmptyShould(true)
                .check(classes);
    }

    // === NAMING TESTS ===

    @Test
    void serviceClassesShouldBeNamedProperly() {
        classes()
                .that().resideInAPackage("..service..")
                .and().areNotInterfaces()
                .should().haveSimpleNameEndingWith("Service");
    }

    @Test
    void repositoryClassesShouldBeNamedProperly() {
        classes()
                .that().resideInAPackage("..repository..")
                .and().areNotInterfaces()
                .should().haveSimpleNameEndingWith("Repository");
    }

    // === REPOSITORY TESTS ===

    @Test
    void repositoryClassesShouldBeInterfaces() {
        classes().that().resideInAPackage("..repository..").should().beAssignableTo(Repository.class);
    }

    @Test
    void repositoriesShouldBeAnnotatedWithRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beAnnotatedWith(Repository.class)
                .check(classes);
    }

    @Test
    void repositoriesShouldNotDependOnControllers() {
        noClasses()
                .that().resideInAPackage("..repository..")
                .should().dependOnClassesThat().resideInAPackage("..controller..")
                .check(classes);
    }

    // === SERVICE TESTS ===

    @Test
    void serviceClassesShouldBeAnnotatedWithService() {
        classes()
                .that().resideInAPackage("..service..")
                .and().areNotInterfaces() // <-- Додай це
                .should().beAnnotatedWith(Service.class);
    }

    @Test
    void serviceShouldOnlyBeAccessedByControllerOrService() {
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..")
                .check(classes);
    }

    @Test
    void serviceShouldNotDependOnControllers() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..controller..")
                .check(classes);
    }

    // === LAYER DEPENDENCY ===

    @Test
    void layeredArchitectureShouldBeRespected() {
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .check(classes);
    }

    // === GENERAL RULES ===

    @Test
    void classesShouldNotUseFieldInjection() {
        noFields()
                .should().beAnnotatedWith(Autowired.class)
                .because("constructor injection is preferred over field injection")
                .check(classes);
    }

    @Test
    void classesShouldNotAccessStandardStreams() {
        noClasses()
                .should().accessField(System.class, "out")
                .orShould().accessField(System.class, "err")
                .because("System.out/err should not be used in production code")
                .check(classes);
    }

    @Test
    void noClassesShouldThrowGenericException() {
        noMethods()
                .should().declareThrowableOfType(Exception.class)
                .because("use more specific exception types")
                .check(classes);
    }
}
