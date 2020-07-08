package org.tempestade.nucleo;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.tempestade.nucleo");

        noClasses()
            .that()
                .resideInAnyPackage("org.tempestade.nucleo.service..")
            .or()
                .resideInAnyPackage("org.tempestade.nucleo.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..org.tempestade.nucleo.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
